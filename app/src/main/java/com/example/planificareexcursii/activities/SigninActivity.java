package com.example.planificareexcursii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.planificareexcursii.R;
import com.example.planificareexcursii.database.DatabaseManager;
import com.example.planificareexcursii.database.UserDAO;
import com.example.planificareexcursii.database.UserService;
import com.example.planificareexcursii.utils.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

public class SigninActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText repassword;
    private EditText age;
    private EditText emai;
    private Button btnSave;
    private List<User> listUsers = new ArrayList<>();
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_signin);
        initComponents();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    final User user =  createUser();
                    DatabaseManager databaseManager = DatabaseManager.getInstance(getApplicationContext());
                    final UserDAO userDAO = databaseManager.getUserDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDAO.insert(user);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                    Log.v("user", createUser().toString());
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initComponents(){
        firstName = findViewById(R.id.et_firstname);
        lastName = findViewById(R.id.et_lastname);
        password = findViewById(R.id.et_password);
        repassword = findViewById(R.id.et_repassword);
        emai = findViewById(R.id.et_email);
        age = findViewById(R.id.et_age);

        btnSave = findViewById(R.id.btn_save);
        userService = new UserService(getApplicationContext());
        //userService.getAll(getAllFromDbCallback());
    }

    private boolean validate(){
        if (firstName.getText().toString()==null || firstName.getText().toString().trim().length()<3){
            Toast.makeText(getApplicationContext(), "Invalid first name!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.getText().toString()==null || password.getText().toString().trim().length()<3){
            Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (lastName.getText().toString()==null || lastName.getText().toString().trim().length()<2){
            Toast.makeText(getApplicationContext(), "Invalid last name!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (repassword.getText().toString().equals(password.getText().toString())==false) {
            Toast.makeText(getApplicationContext(), "reenter password!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (age.getText().toString()==null || age.getText().toString().trim().length()!=2){
            Toast.makeText(getApplicationContext(), "Invalid age!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (emai.getText().toString()==null || validateEmail(emai.getText().toString().trim())==false){
            Toast.makeText(getApplicationContext(), "Invalid email!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private User createUser(){
        Random random = new Random();
        String firstname = firstName.getText().toString();
        String lastname = lastName.getText().toString();
        String email = emai.getText().toString();
        String pass = password.getText().toString();
        int a = Integer.parseInt(age.getText().toString().trim());

        return new User(random.nextInt(99)+1,firstname,lastname,pass,email,a);
    }

    public boolean validateEmail(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        if (!email.equals("") && matcher.matches()) {
            return true;
        } else {

            return false;
        }
    }
}