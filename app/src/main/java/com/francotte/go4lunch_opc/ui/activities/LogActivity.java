package com.francotte.go4lunch_opc.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.francotte.go4lunch_opc.DI.DI;
import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.repositories.user_repository.FirestoreCall;
import com.francotte.go4lunch_opc.service.authentication.AuthenticationService;
import com.francotte.go4lunch_opc.repositories.user_repository.UserHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class LogActivity extends AppCompatActivity implements FirestoreCall.CallbackGetTokenAtCurrentUser {

    /**
     * --- UI ---
     */
    private ConstraintLayout mConstraintLayout;
    /**
     * --- Authentication ---
     */
    private AuthenticationService authenticationService;
    private static final int RC_SIGN_IN = 123;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        checkPermissionApp();
        init();
        setUI();
    }

    // init authentication service
    private void init() {
        authenticationService = DI.getServiceAuthentication();
    }

    // Configure UI
    private void setUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.dismiss();
        Button mButtonLogWithFacebook = (Button) findViewById(R.id.log_with_facebook);
        Button mButtonLogWithGoogle = (Button) findViewById(R.id.log_with_google);
        Button mButtonLogWithTwitter = (Button) findViewById(R.id.log_with_twitter);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.activity_log_constraint_layout);

        mButtonLogWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(authenticationService.getAuthUIOfSignWithFacebook(), RC_SIGN_IN);
            }
        });
        mButtonLogWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(authenticationService.getAuthUIOfSignWithGoogle(), RC_SIGN_IN);
            }
        });
        mButtonLogWithTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(authenticationService.getAuthUIOfSignWithTwitter(), RC_SIGN_IN);
            }
        });
    }

    // Get current user firesbaseAuth
    @Nullable
    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // Get current token
    private void createUserInFirestore() {
        FirestoreCall.getTokenAtCurrentUser(this);
    }

    // Response get current token and create user in firestore
    @Override
    public void onSuccessGetCurrentToken(String token) {
        Log.d("LogActivity", "token: " + token);
      if (this.getCurrentUser() != null) {
            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
            String username = this.getCurrentUser().getDisplayName();
            String uid = this.getCurrentUser().getUid();
            final Intent intent = new Intent(this, MainActivity.class);
            UserHelper.createUser(uid, username, urlPicture, token).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.connection_error_unknown), Toast.LENGTH_LONG).show();
                }
            });
       }
    }

    @Override
    public void onFailureGetCurrentToken(Exception e) {
        Log.e("LogActivity", e.getMessage());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        progressDialog.show();
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                this.createUserInFirestore();
                Snackbar.make(mConstraintLayout, getString(R.string.connection_success), Snackbar.LENGTH_LONG).show();
            } else {
                if (response == null) {
                    Snackbar.make(mConstraintLayout, getString(R.string.connection_error_canceled), Snackbar.LENGTH_LONG).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Snackbar.make(mConstraintLayout, getString(R.string.connection_error_no_internet), Snackbar.LENGTH_LONG).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Snackbar.make(mConstraintLayout, getString(R.string.connection_error_unknown), Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }

    //Check permissions for application
    private void checkPermissionApp() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                /** --- Permissions --- */
                int REQUEST_CODE_ASK_PERMISSIONS_LOCATION = 123;
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS_LOCATION);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                int REQUEST_CODE_ASK_PERMISSIONS_CALL = 124;
                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS_CALL);
            }
        }
    }
}




