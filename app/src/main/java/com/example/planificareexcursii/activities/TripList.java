package com.example.planificareexcursii.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.planificareexcursii.CustomAdapter.CustomAdapter;
import com.example.planificareexcursii.R;
import com.example.planificareexcursii.asyncTask.Callback;
import com.example.planificareexcursii.database.DatabaseManager;
import com.example.planificareexcursii.database.TripDAO;
import com.example.planificareexcursii.database.TripService;
import com.example.planificareexcursii.database.UserDAO;
import com.example.planificareexcursii.utils.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TripList extends AppCompatActivity {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    public static final int REQUEST_CODE = 200;
    private static final int UPDATE_REQUEST_CODE = 202;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListNombres;
    public final static String MESSAGE = "msg";
    private HashMap<String, Trip> tripList;
    private List<Trip> trips = new ArrayList<>();
    private int lastExpandedPosition = -1;
    private CustomAdapter customAdapter;
    private TripService tripService;
     long nr;
    Button btn;
    Intent intent;
    String i;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_trip_list);
        btn = findViewById(R.id.btn_addtrip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTrip.class);
                startActivityForResult(intent, 200);
            }
        });

        init();
        img = findViewById(R.id.img_back);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(i2);
            }
        });

        tripService = new TripService(getApplicationContext());
        tripService.getAll(getAllFromDbCallback());
        Date c = Calendar.getInstance().getTime();
        //tripList.put("Date", new Trip("Bucuresti","Cluj","Timi",c,c));
        this.expandableListView = findViewById(R.id.expandableListView);
        expandableListView.setAdapter(expandableListAdapter); //pt incercare

        customAdapter = new CustomAdapter((ArrayList<Trip>) trips, getApplicationContext());
        expandableListView.setAdapter(customAdapter);


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tripService.delete(deleteToDbCallback(position), trips.get(position));

                return true;
            }
        });


//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                tripService.delete(deleteToDbCallback(groupPosition),trips.get(groupPosition));
//                return false;
//            }
//        });


        intent = getIntent();
        nr = intent.getLongExtra(MESSAGE, 0);


    }

    private Callback<Integer> deleteToDbCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    trips.remove(position);
                    customAdapter.update();
                    //notifyAdapter();
                }
            }
        };
    }

    private void init() {
        this.expandableListView = findViewById(R.id.expandableListView);
        this.tripList = getContactos();
        this.expandableListNombres = new ArrayList<>(tripList.keySet());


    }

    private HashMap<String, Trip> getContactos() {
        HashMap<String, Trip> listaC = new HashMap<>();

        Date c = Calendar.getInstance().getTime();
        return listaC;
    }

    private Callback<List<Trip>> getAllFromDbCallback() {
        return new Callback<List<Trip>>() {
            @Override
            public void runResultOnUiThread(List<Trip> result) {
                if (result != null) {
                    trips.clear();
                    trips.addAll(result);
                }
            }
        };
    }

    private Callback<Trip> insertIntoDbCallback() {
        return new Callback<Trip>() {
            @Override
            public void runResultOnUiThread(Trip result) {
                if (result != null) {
                    // trips.add(result);
                    customAdapter.addElement(result);

                }
            }
        };
    }

    private void notifyAdapter() {
        CustomAdapter adapter = (CustomAdapter) expandableListView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private Callback<Trip> updateToDbCallback() {
        return new Callback<Trip>() {
            @Override
            public void runResultOnUiThread(Trip result) {
                if (result != null) {
                    for (Trip trip : trips) {
                        if (trip.getUserId() == result.getUserId()) {
                            trip.setData(result.getData());
                            trip.setDataFinal(result.getDataFinal());
                            trip.setDestinatie1(result.getDestinatie1());
                            trip.setDestinatie2(result.getDestinatie2());
                            trip.setDestinatie3(result.getDestinatie3());
                            break;
                        }
                    }

                    customAdapter.update();
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // if(data.hasExtra(AddTrip.ADD_TRIP)) {
            final Trip trip = (Trip) data.getSerializableExtra(AddTrip.ADD_TRIP);

            //Log.v("info",trip.toString());
            if (trip != null) {

                trip.setId(nr);
                tripService.insert(insertIntoDbCallback(), trip);
            }

            // customAdapter.addElement(trip);
            // notifyAdapter();
            // }

//            else
//                if(data.hasExtra(AddTrip.TRIP_KEY)){
//
//                    final Trip trip = (Trip) data.getSerializableExtra(AddTrip.TRIP_KEY);
//
//                    //Log.v("info",trip.toString());
//                    if (trip != null) {
//
//                        trip.setId(nr);
//                        tripService.insert(insertIntoDbCallback(), trip);
//                    }
//                }
//


        }
    }
}