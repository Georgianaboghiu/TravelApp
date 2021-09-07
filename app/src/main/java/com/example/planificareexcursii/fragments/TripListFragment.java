package com.example.planificareexcursii.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.planificareexcursii.CustomAdapter.CustomAdapter;
import com.example.planificareexcursii.R;
import com.example.planificareexcursii.activities.AddTrip;
import com.example.planificareexcursii.asyncTask.Callback;
import com.example.planificareexcursii.database.TripService;
import com.example.planificareexcursii.utils.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class TripListFragment extends Fragment {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    public static final int REQUEST_CODE = 200;
    private static final int UPDATE_REQUEST_CODE = 202;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListNombres;
    public final static String MESSAGE="msg";
    private HashMap<String, Trip> tripList;
    private List<Trip> trips = new ArrayList<>();
    private int lastExpandedPosition = -1;
    private CustomAdapter customAdapter;
    private TripService tripService;
    long nr;
    Button btn;
    Intent intent;
    String i;
    // TODO: Rename and change types of parameters


    public TripListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);


        btn = view.findViewById(R.id.btn_addtrip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), AddTrip.class);
                startActivityForResult(intent, 200);
            }
        });

        init(view);
        tripService = new TripService(getContext().getApplicationContext());
        tripService.getAll(getAllFromDbCallback());
        Date c = Calendar.getInstance().getTime();
        //tripList.put("Date", new Trip("Bucuresti","Cluj","Timi",c,c));
        this.expandableListView = view.findViewById(R.id.expandableListView);
        expandableListView.setAdapter(expandableListAdapter); //pt incercare


        customAdapter = new CustomAdapter((ArrayList<Trip>) trips, getContext().getApplicationContext());
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
                tripService.delete(deleteToDbCallback(position),trips.get(position));

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
        intent= getActivity().getIntent();
        nr=intent.getLongExtra(MESSAGE,0);


        return view;
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

    private void init(View view) {
        this.expandableListView = view.findViewById(R.id.expandableListView);
        this.tripList = getContactos();
        this.expandableListNombres = new ArrayList<>(tripList.keySet());


    }

    private HashMap<String, Trip> getContactos() {
        HashMap<String, Trip> listaC = new HashMap<>();

        Date c = Calendar.getInstance().getTime();
        listaC.put("Date", new Trip("Bucuresti", "Cluj", "Timi", c, c));
        listaC.put("Date2", new Trip("Bucuresti", "Cluj", "Timi", c, c));
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
                    //notifyAdapter();
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
                        if (trip.getUserId() == result.getUserId())
                        {
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE  &&resultCode == Activity.RESULT_OK && data != null) {
            final Trip trip = (Trip) data.getSerializableExtra(AddTrip.ADD_TRIP);
            //Log.v("info",trip.toString());
            if (trip != null) {

                trip.setId(nr);
                tripService.insert(insertIntoDbCallback(), trip);

                //tripService.insert(insertIntoDbCallback(), trip);
                // customAdapter.addElement(trip);
                // notifyAdapter();
            }

            else {
                Trip trip2 = (Trip) data.getSerializableExtra(AddTrip.TRIP_KEY);
                Log.v("info", trip2.toString());
                if (trip2 != null) {

                    //trip.setId(52);

                    tripService.update(updateToDbCallback(), trip2);
                    // customAdapter.addElement(trip);
                    // notifyAdapter();
                }

            }
        }



    }
}