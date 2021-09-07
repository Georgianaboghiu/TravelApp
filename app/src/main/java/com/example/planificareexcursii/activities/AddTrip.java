package com.example.planificareexcursii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planificareexcursii.CustomAdapter.DateConverter;
import com.example.planificareexcursii.R;
import com.example.planificareexcursii.utils.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AddTrip extends AppCompatActivity {
    public static final String TRIP_KEY = "tripKey";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String ADD_TRIP = "adaugaReteta";
    ImageView img;
    TextView tvDataInceput, tvDataFinal;
    DatePickerDialog.OnDateSetListener listener, listener1;
    Button btnSalveaza;
    CheckBox cb;
    AutoCompleteTextView city1, city2, city3;
    Intent intent;
    AutoCompleteTextView mAutoCompleteTextViewTimezone;

    int i=0;
    private Trip trip = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        String[] cities=getResources().getStringArray(R.array.cities);
        // mAutoCompleteTextViewTimezone=findViewById(R.id.actvTimezone);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                R.layout.custom_listcity,R.id.tv_listcitiyitem,cities);
        //mAutoCompleteTextViewTimezone.setAdapter(adapter);

        // String input=mAutoCompleteTextViewTimezone.getText().toString();


        city1=findViewById(R.id.actvTimezone);
        city1.setAdapter(adapter);
        city2=findViewById(R.id.actvTimezone2);
        city2.setAdapter(adapter);
        city3=findViewById(R.id.actvTimezone3);
        city3.setAdapter(adapter);


        init();
    
        intent = getIntent();
        if (intent.hasExtra(TRIP_KEY)) {
            i=1;
            trip = (Trip) intent.getSerializableExtra(TRIP_KEY);
            buildViewsFromExpense(trip);
        }


    }


    private void buildViewsFromExpense(Trip trip) {
        if (trip == null) {
            return;
        }
        if (trip.getData() != null) {
            tvDataInceput.setText(DateConverter.fromDate(trip.getData()));
        }
        if (trip.getDataFinal() != null) {
            tvDataFinal.setText(DateConverter.fromDate(trip.getDataFinal()));
        }
        city1.setText(trip.getDestinatie1());
        if(trip.getDestinatie2().length()>2)
        {
            city2.setText(trip.getDestinatie2());
            city2.setVisibility(View.VISIBLE);
        }
        if(trip.getDestinatie3().length()>2)
        {
            city2.setText(trip.getDestinatie3());
            city2.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        img=findViewById(R.id.multiple_dest);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(city2.getVisibility() == View.VISIBLE)
                {
                    city3.setVisibility(View.VISIBLE);
                }
                else {
                    city2.setVisibility(View.VISIBLE);

                }

            }
        });


        tvDataInceput=findViewById(R.id.tvdatainceput);
        tvDataInceput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTrip.this,
                        listener, year, month, day);
                dialog.show();
            }
        });
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String data = dayOfMonth + "-" + month + "-" + year;
                tvDataInceput.setText(data);
            }
        };

        tvDataFinal=findViewById(R.id.tvdatafinal);

        tvDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTrip.this,
                        listener1, year, month, day);
                dialog.show();
            }
        });
        listener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String data = dayOfMonth + "-" + month + "-" + year;
                tvDataFinal.setText(data);
            }
        };
        btnSalveaza=findViewById(R.id.btnsaveTrip);
        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Trip trip = null;
                    try {
                        trip = createTripFromView();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                   if(i==1) {
                        intent.putExtra(TRIP_KEY, trip);

                    }
                    else if (i==0){
                        intent.putExtra(ADD_TRIP, trip);

                   }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }


    private Trip createTripFromView() throws ParseException {
        String destination1 = city1.getText().toString();
        String destination2 = city2.getText().toString();
        String destination3 = city3.getText().toString();

        Date dataInc=new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(tvDataInceput.getText().toString());
//        try {
//             dataInc = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(tvDataInceput.getText().toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Random random = new Random();

        Date dataSf = null;
        try {
            dataSf = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(tvDataFinal.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Trip(destination1,destination2,destination3,dataInc,dataSf);
    }
    private boolean validate() {
        if(tvDataInceput.getText() == null || tvDataInceput.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please set start date", Toast.LENGTH_LONG).show();
            return false;
        } else if(tvDataFinal.getText() == null || tvDataFinal.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please set end date", Toast.LENGTH_LONG).show();
            return false;
        } else if(city1.getText() == null || city1.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please set first destiantion", Toast.LENGTH_LONG).show();
            return false;
        }
//        }else if(city2.getText() == null ) {
//            Toast.makeText(getApplicationContext(), "Please set second destiantion", Toast.LENGTH_LONG).show();
//            return false;
//        }else if(city3.getText() == null) {
//            Toast.makeText(getApplicationContext(), "Please set third destiantion", Toast.LENGTH_LONG).show();
//            return false;
//        }


        return true;
    }
}