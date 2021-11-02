package com.francotte.go4lunch_opc.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.francotte.go4lunch_opc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    NavController navController;
    FirebaseAuth firebaseAuth;
    TextView emailUser, nameUser;
    ImageView iconUser;
    NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        emailUser = (TextView) headerView.findViewById(R.id.nav_email_user);
        nameUser = (TextView) headerView.findViewById(R.id.nav_name_user);
        iconUser = (ImageView) headerView.findViewById(R.id.nav_icon_user);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        // --- Drawer Layout --- //
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.bn_map_view, R.id.bn_list_view, R.id.bn_workmates)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check condition
        if (firebaseUser != null) {
            // When user already sign in
            // Redirect to profile activity
        firebaseUser.getPhotoUrl();
        firebaseUser.getEmail();
        firebaseUser.getDisplayName();

        emailUser.setText(firebaseUser.getEmail());
        nameUser.setText(firebaseUser.getDisplayName());

            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(iconUser);

            displayToast("Firebase authentication successful");
        }


    }
    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.main_drawer_your_lunch:

               break;
            case R.id.main_drawer_settings:

               // break;
            case R.id.main_drawer_logout :
                // this.signOutUserFromFirebase();
              //  break;
            default:
              //  break;
        }
        return false;
    }

}