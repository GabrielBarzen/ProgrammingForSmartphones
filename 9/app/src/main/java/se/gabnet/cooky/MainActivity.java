package se.gabnet.cooky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import se.gabnet.cooky.Database.DatabaseController;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new DatabaseController(this);


        setContentView(R.layout.activity_main);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        setupNavigationView();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }


    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    boolean first = true;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navigationController = Navigation.findNavController(this,R.id.nav_host_fragment_view);
        System.out.println("ITEM WAS SELECTED");
        System.out.println("ITEM IS: " + item);
        if(!first) {
            System.out.println("CHECK IF BACK BUTTON IN VIEWER");
            System.out.println("CHECK IF IN VIEWER");
            if (navigationController.getCurrentDestination().equals(navigationController.findDestination(R.id.fragmentRecipeViewer))){
                System.out.println("IS IN VIEWER");

                navigationController.navigate(R.id.action_fragmentRecipeViewer_to_fragmentRecipeList);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
                drawerLayout.setVisibility(View.VISIBLE);
                drawerLayout.open();
                return true;
            }
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    NavController navigationController;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        first = false;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_recipes){
            if (navigationController.getCurrentDestination().equals(navigationController.findDestination(R.id.settingsFragment))) {
                navigationController.navigate(R.id.action_settingsFragment_to_fragmentRecipeList);
            }
            else if (navigationController.getCurrentDestination().equals(navigationController.findDestination(R.id.fragmentRecipeList))) {
            } else {
                navigationController.navigate(R.id.action_fragmentMain_to_fragmentRecipeList);

            }
        }
        if (id == R.id.nav_settings){
            if (navigationController.getCurrentDestination().equals(navigationController.findDestination(R.id.settingsFragment))) {
            }
            else if (navigationController.getCurrentDestination().equals(navigationController.findDestination(R.id.fragmentRecipeList))) {
                navigationController.navigate(R.id.action_fragmentRecipeList_to_settingsFragment);
            } else {
                navigationController.navigate(R.id.action_fragmentMain_to_settingsFragment);
            }
        }



        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}