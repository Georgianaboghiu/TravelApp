package com.example.planificareexcursii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.planificareexcursii.R;

public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_destination);

        ImageView imageView=(ImageView) findViewById(R.id.img_cityDetail);
        TextView textView=(TextView)findViewById(R.id.tv_cityDetail);
        EditText et_description=(EditText)findViewById(R.id.et_multiline);
        String txt=getIntent().getStringExtra("Description");
        textView.setText(getIntent().getStringExtra("City"));
        et_description.setText(txt);
        imageView.setImageResource(getIntent().getIntExtra("Picture",R.drawable.berlin));
    }
}