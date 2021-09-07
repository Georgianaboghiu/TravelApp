package com.example.planificareexcursii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.planificareexcursii.R;
import com.example.planificareexcursii.asyncTask.Callback;
import com.example.planificareexcursii.database.DatabaseManager;
import com.example.planificareexcursii.database.UserDAO;
import com.example.planificareexcursii.database.UserService;
import com.example.planificareexcursii.utils.User;

import java.util.ArrayList;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    public final static String MESSAGE_KEY="msg_key";
    private EditText etUser;
    private EditText etPass;
    private Button btnLogin;
    private Button btnSignin;
    private CheckBox showpassword;
    private CheckBox keeplogged;
    private SharedPreferences sharedPreferences;
    private List<User> listUsers = new ArrayList<>();
    private UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_log_in);
        initComponents();
        showpassword=findViewById(R.id.showpassword);
        keeplogged=findViewById(R.id.keep_logged);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }
        });

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences("Credentials", 0);
        String username=sharedPreferences.getString("username",null);
        String password=sharedPreferences.getString("password",null);
        if(username != null && password !=null ){
           Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            intent.putExtra(MESSAGE_KEY,  sharedPreferences.getString("username", "defaultValue"));
           startActivity(intent);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUserFromSQL();
            }
        });
    }

    private void readUserFromSQL(){
        if (validate()){
            final String userName = etUser.getText().toString();
            final String userPass = etPass.getText().toString();
            DatabaseManager databaseManager = DatabaseManager.getInstance(getApplicationContext());
            final UserDAO userDAO = databaseManager.getUserDAO();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    User user = userDAO.login(userName, userPass);
                    if (user == null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(keeplogged.isChecked()){
                        SharedPreferences sharedPreferences = getSharedPreferences("Credentials", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", etUser.getText().toString());
                        editor.putString("password", etPass.getText().toString());
                        editor.apply();
//                          String name = user.getUsername();
                        String name = user.getFirstname();
                        //Long nr=user.getUserId();
                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        //intent.putExtra(MESSAGE_KEY,  sharedPreferences.getString("username", "defaultValue"));
                        intent.putExtra(MESSAGE_KEY, name);
                        startActivity(intent);



                    }else {
                        String name = user.getFirstname();
                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        intent.putExtra(MESSAGE_KEY, name);
                         startActivity(intent);
                    }

                }
            }).start();

        }
    }

    private void initComponents(){

        btnSignin=findViewById(R.id.btn_signin);
        etUser=findViewById(R.id.et_user);
        etPass=findViewById(R.id.et_pass);
        btnLogin=findViewById(R.id.btn_login);

        userService = new UserService(getApplicationContext());
        userService.getAll(getAllFromDbCallback());
    }


    private boolean validate(){
        if (etUser.getText().toString()==null || etUser.getText().toString().trim().length()<3){
            Toast.makeText(getApplicationContext(), "Invalid username!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etPass.getText().toString()==null || etPass.getText().toString().trim().length()<3){
            Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private Callback<List<User>> getAllFromDbCallback() {
        return new Callback<List<User>>() {
            @Override
            public void runResultOnUiThread(List<User> result) {
                if (result != null) {
                    listUsers.clear();
                    listUsers.addAll(result);
                    for (User user : listUsers)
                        Log.v("lista", user.toString());
                }
            }
        };
    }
}