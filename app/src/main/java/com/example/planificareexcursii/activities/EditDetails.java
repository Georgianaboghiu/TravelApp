package com.example.planificareexcursii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.planificareexcursii.R;
import com.example.planificareexcursii.database.DatabaseManager;
import com.example.planificareexcursii.database.UserDAO;
import com.example.planificareexcursii.utils.User;

public class EditDetails extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6, et7;
    User user;
    Button btnUpdate;
    Button btnDelete;
    DatabaseManager databaseManager;
    UserDAO userDAO;
    Intent intent;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        intent=getIntent();
        final String name=intent.getStringExtra("DETAILS");
        Log.v("detail ", name);

        et1=findViewById(R.id.idP);
        et2=findViewById(R.id.firstname);
        et3=findViewById(R.id.passwordP);
        et4=findViewById(R.id.repasswordP);
        et5=findViewById(R.id.email);
        et6=findViewById(R.id.ageP);
        et7=findViewById(R.id.lastname);
        btnDelete=findViewById(R.id.deleteUser);
        btnUpdate=findViewById(R.id.updateUser);


        new Thread(new Runnable() {
            @Override
            public void run() {
                databaseManager = DatabaseManager.getInstance(getApplicationContext());
                userDAO = databaseManager.getUserDAO();
                user = userDAO.retrieveInfo(name);
                if (user != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et1.setText(user.getUserId() + "");
                            et2.setText(user.getFirstname());
                            et3.setText(user.getPassword());
                            et4.setText(user.getPassword());
                            et6.setText(String.valueOf(user.getAge()));

                            et5.setText(user.getEmail());

                            et7.setText(user.getLastname());

                            et1.setEnabled(false);

                            et5.setEnabled(false);

                        }
                    });
                }
            }
        }).start();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfile();
            }
        });
    }

    public void updateProfile( ) {
        databaseManager = DatabaseManager.getInstance(getApplicationContext());
        userDAO = databaseManager.getUserDAO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                user.setFirstname(et2.getText().toString());
                user.setPassword(et3.getText().toString());
                user.setEmail( et5.getText().toString());
                user.setLastname( et7.getText().toString());
                user.setAge(Integer.parseInt( et6.getText().toString()));


                userDAO.update(user);
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).start();
    }

    public void deleteProfile() {
        databaseManager = DatabaseManager.getInstance(getApplicationContext());
        userDAO = databaseManager.getUserDAO();
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDAO.delete(user);
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("Credentials", 0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Profile deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }).start();


    }

}