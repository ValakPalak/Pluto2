package com.example.plutoacademy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // controls the view
        BottomNavigationView navView = findViewById(R.id.navigation);
        // Passing each menu ID as a set of Ids because each



        // builds navGraph
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_books, R.id.navigation_experts, R.id.navigation_profile)
                .build();
        // controls the fragment-to-fragment navigation in activity
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // set the UI
        NavigationUI.setupWithNavController(navView, navController);
        if(ExpertsDescription.Books)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.homefragment,new BooksFragment()).commit();
            navView.getMenu().findItem(R.id.navigation_books).setChecked(true);
            ExpertsDescription.Books=false;
        }
        if(ExpertsDescription.Expert)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.homefragment,new ExpertsFragment()).commit();
            navView.getMenu().findItem(R.id.navigation_experts).setChecked(true);
            ExpertsDescription.Expert=false;
        }
    }

    public void changeinterests(View view) {
        Intent intent = new Intent(this, ChangeInterests.class);
        startActivity(intent);
    }
    public void Saved(View view) {
        Intent intent = new Intent(this, Saved.class);
        startActivity(intent);
    }
    public void feedback(View view) {
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }

}