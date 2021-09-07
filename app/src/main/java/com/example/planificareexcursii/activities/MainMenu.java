package com.example.planificareexcursii.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planificareexcursii.R;
import com.example.planificareexcursii.database.DatabaseManager;
import com.example.planificareexcursii.database.UserDAO;
import com.example.planificareexcursii.fragments.FeedbackFragment;
import com.example.planificareexcursii.utils.Feedback;
import com.example.planificareexcursii.utils.User;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainMenu extends AppCompatActivity {
    public final static String MESSAGE_KEY="msg_key";
    public final static String MESSAGE="msg";
    Button btn;
    Intent intent;
    String userFromLogin;
    private TextView tvInfo;
    private TextView tvInfo2;
    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;
    ImageView img_upd;
    long i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main_menu);


         intent = getIntent();
         userFromLogin = intent.getStringExtra(MESSAGE_KEY);
         DatabaseManager databaseManager = DatabaseManager.getInstance(getApplicationContext());
         final UserDAO userDAO = databaseManager.getUserDAO();
        configNavigation();
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(addNavigationMenuItemSelectedEvent());
        tvInfo = findViewById(R.id.tv_wha);

                new Thread(new Runnable() {
            @Override
            public void run() {
                final User user = userDAO.retrieveInfo(userFromLogin);
                if (user != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvInfo.setText(user.getAge() + " years | " + user.getFirstname());
                            i=user.getUserId();
                            //tvInfo.setText((int) user.getUserId());
                           // tvInfo2.setText((int) user.getUserId());
                            //i=user.getUserId();
                        }
                    });
                }
            }
        }).start();
        img_upd=findViewById(R.id.update_user);
        img_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent_2=new Intent(getApplicationContext(),EditDetails.class);
            intent_2.putExtra("DETAILS",String.valueOf(userFromLogin));
            startActivity(intent_2);
            }
        });

    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    private NavigationView.OnNavigationItemSelectedListener addNavigationMenuItemSelectedEvent() {

        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_mytrip) {
                    Intent intent = new Intent(getApplicationContext(), TripList.class);
                   // String i=tvInfo.getText().toString();
                    intent.putExtra(MESSAGE, i);
                    startActivity(intent);



                }else
                if (item.getItemId() == R.id.nav_surround) {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    // String i=tvInfo.getText().toString();
                    //intent.putExtra(MESSAGE, i);
                    startActivity(intent);


                }else
                if (item.getItemId() == R.id.nav_checklist) {
                    Intent intent = new Intent(getApplicationContext(), CheckListActivity.class);
                    // String i=tvInfo.getText().toString();
                    //intent.putExtra(MESSAGE, i);
                    startActivity(intent);

                }else
                if (item.getItemId() == R.id.nav_currency) {
                    Intent intent = new Intent(getApplicationContext(), CurrencyActivity.class);
                     String i=tvInfo.getText().toString();

                    startActivity(intent);

                }else
                if (item.getItemId() == R.id.main_nav_feedback) {
                    Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                    startActivity(intent);
                    //currentFragment = new FeedbackFragment();
                 //   findViewById(R.id.imageView12).setSources(Color.parseColor("#FFFFFF"));
              //findViewById(R.id.imageView12).setSources(Color.parseColor("#FFFFFF"));
                    //getSupportFragmentManager().beginTransaction().replace(R.id.main_fl_container, currentFragment).commit();


                }else
                if (item.getItemId() == R.id.mav_weather) {
                    Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
                    startActivity(intent);

                }else
                if (item.getItemId() == R.id.main_nav_logout) {
                    Logout();

                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //atasare meniu clasic de activitatea principala
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.logout) {
            Toast.makeText(getApplicationContext(),
                    "log ",
                    Toast.LENGTH_LONG)
                    .show();
        }
        return true;
    }



    public void Logout(){
        SharedPreferences sharedPreferences=getSharedPreferences("Credentials", 0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        finish();
    }
}






