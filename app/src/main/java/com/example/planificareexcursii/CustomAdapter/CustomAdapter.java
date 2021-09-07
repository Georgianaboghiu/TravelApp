package com.example.planificareexcursii.CustomAdapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planificareexcursii.R;
import com.example.planificareexcursii.activities.AddTrip;
import com.example.planificareexcursii.activities.DestinationsList;
import com.example.planificareexcursii.activities.FindRoutesActivity;
import com.example.planificareexcursii.activities.TripList;
import com.example.planificareexcursii.utils.Trip;

import java.util.ArrayList;

public class CustomAdapter extends BaseExpandableListAdapter {



    private ArrayList<Trip> trips;
    private Context context;
    private LayoutInflater inflater;

    public CustomAdapter(ArrayList<Trip> trips, Context context) {
        this.trips = trips;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }



    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Trip trip = (Trip) getChild(groupPosition, childPosition);

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_item, null);

        }

//        CircleImageView circleImageView = convertView.findViewById(R.id.circleIMG);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), contacto.getImg());
//        circleImageView.setImageBitmap(bitmap);

        LinearLayout startDriving = convertView.findViewById(R.id.start_driving);
        LinearLayout loadMap = convertView.findViewById(R.id.loadmap);
        LinearLayout loadObjectiv = convertView.findViewById(R.id.ln_obiective);
        LinearLayout layoutInfo = convertView.findViewById(R.id.lnupdate);


        startDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(), "Trip : "
                // + trip.toString(), Toast.LENGTH_SHORT).show();

                String sSource="Bucharest";
                String sDestination=trip.getDestinatie1();
                String sDestination2=trip.getDestinatie2();
                String sDestination3=trip.getDestinatie3();


                try{
                    //when google map is installed
                    //initialize url
                    Uri uri=Uri.parse("http://www.google.co.in/maps/dir/"
                            +sSource+"/"+sDestination+"/"+sDestination2+"/"+sDestination3);

                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }


            }
        });

        loadMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Trip : "
                + trip.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), FindRoutesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        loadObjectiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Trip : "
                     //   + trip.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), DestinationsList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //List<String> index = new ArrayList<>(expandableListDetalles.keySet());


                Intent intent = new Intent(context.getApplicationContext(), AddTrip.class);
                intent.putExtra(AddTrip.TRIP_KEY, trip);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // context.start(intent, 100);
                context.startActivity(intent);
            }
        });


        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        convertView.startAnimation(animation);


        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        // String nombre = (String) getGroup(groupPosition);
        Trip trip = (Trip) getChild(groupPosition,0);

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_group, null);

        }

        TextView dataInc = convertView.findViewById(R.id.tvdatainceput);
        TextView dataFin = convertView.findViewById(R.id.tvdatafinal);
        TextView tvDestinatie1 = convertView.findViewById(R.id.tvRoute2);
        TextView tvDestinatie2 = convertView.findViewById(R.id.tvRoute3);
        TextView tvDestinatie3 = convertView.findViewById(R.id.tvRoute4);
        TextView tvN=convertView.findViewById(R.id.tvtxt);
        //  tvN.setText(nombre);
        //Date c = Calendar.getInstance().getTime();
        dataInc.setText(TripList.dateFormat.format(trip.getData()));
       // dataInc.setText("ceva");

        dataFin.setText(TripList.dateFormat.format(trip.getDataFinal()));

        tvDestinatie1.setText(trip.getDestinatie1());
        tvDestinatie2.setText(trip.getDestinatie2());
        tvDestinatie3.setText(trip.getDestinatie3());
        ;

        return convertView;
    }

    @Override
    public int getGroupCount() {
        return this.trips.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.trips.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.trips.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }




    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public void addElement(Trip trip){
        //activitati.add(activitate);
        trips.add(trip);
        notifyDataSetChanged();
    }
    public void update(){
        //activitati.add(activitate);
        //trips.(trip);
        notifyDataSetChanged();
    }



}
