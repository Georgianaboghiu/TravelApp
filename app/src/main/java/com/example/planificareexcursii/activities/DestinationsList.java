package com.example.planificareexcursii.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.planificareexcursii.CustomAdapter.CustomAdapter;
import com.example.planificareexcursii.CustomAdapter.DestAdapter;
import com.example.planificareexcursii.CustomAdapter.DestinationsAdapter;
import com.example.planificareexcursii.R;
import com.example.planificareexcursii.utils.DataItem;

import java.util.ArrayList;
import java.util.List;

public class DestinationsList extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    EditText theFilter;
    List<DataItem> lstData;
    ArrayAdapter<String> arrayAdapter;
    DestAdapter adapter;
    int current_page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_destinations_list);
        searchView = findViewById(R.id.searhc_bar);
        listView = findViewById(R.id.lw_city);
       // listView.setTextFilterEnabled(true);
        theFilter=findViewById(R.id.searchFilter);




        lstData=new ArrayList<>();


        lstData.add( new DataItem(R.drawable.amsterdam,"Amsterdam",getResources().getString(R.string.amsterdam)));
        lstData.add( new DataItem(R.drawable.athens,"Athens",getResources().getString(R.string.athens)));
        lstData.add( new DataItem(R.drawable.barcelona,"Barcelona",getResources().getString(R.string.barcelona)));
        lstData.add( new DataItem(R.drawable.berlin,"Berlin",

                "Berlin is the capital and largest city of Germany by both area and population. Its 3,769,495 inhabitants, as of 31 December 2019 makes it the most-populous city of the European Union, according to population within city limits. One of Germany 16 constituent states,Berlin is surrounded by the state of Brandenburg, and contiguous with Potsdam, Brandenburg capital. Berlin urban area has a population of around 4.5 million and is the second most populous urban area in Germany after the Ruhr.The Berlin-Brandenburg capital region has about six million inhabitants and is Germany's third-largest metropolitan region after the Rhine-Ruhr and Rhine-Main regions."

                ));
        lstData.add( new DataItem(R.drawable.bologna,"Bologna",getResources().getString(R.string.bologna)));
        lstData.add( new DataItem(R.drawable.bratislava,"Bratislava",getResources().getString(R.string.bratislava)));

        lstData.add( new DataItem(R.drawable.bucharest,"Bucharest",
                "Bucharest is the capital and largest city of Romania, as well as its cultural, industrial, and financial centre. It is in the southeast of the country, on the banks of the Dâmbovița River, less than 60 km (37.3 mi) north of the Danube River and the Bulgarian border. Bucharest was first mentioned in documents in 1459. It became the capital of Romania in 1862 and is the centre of Romanian media, culture, and art. Its architecture is a mix of historical (Neoclassical and Art Nouveau), interbellum (Bauhaus and Art Deco), communist era and modern. In the period between the two World Wars, the city's elegant architecture and the sophistication of its elite earned Bucharest the nickname of 'Paris of the East' (Romanian: Parisul Estului) or 'Little Paris' (Romanian: Micul Paris). Although buildings and districts in the historic city centre were heavily damaged or destroyed by war, earthquakes, and even Nicolae Ceaușescu's program of systematization, many survived and have been renovated."
                ));

        lstData.add( new DataItem(R.drawable.budapest,"Budapesta",getResources().getString(R.string.budapesta)));
        lstData.add( new DataItem(R.drawable.cluj,"Cluj",getResources().getString(R.string.cluj)));
        lstData.add( new DataItem(R.drawable.constanta,"Constanta",getResources().getString(R.string.constanta)));
        lstData.add( new DataItem(R.drawable.copenhaga,"Copenhaga",getResources().getString(R.string.copenhaga)));
        lstData.add( new DataItem(R.drawable.dortmund,"Dortmund",getResources().getString(R.string.dortmund)));
        lstData.add( new DataItem(R.drawable.dublin,"Dublin","  Dublin is the capital and largest cityof Ireland. Situated on a bay on the east coast, at the mouth of the River Liffey, it lies within the province of Leinster. It is bordered on the south by the Dublin Mountains, a part of the Wicklow Mountains range. It has an urban area population of 1,173,179, while the population of the Dublin Region (formerly County Dublin) as of 2016 was 1,347,359. The population of the Greater\n" +
                "Dublin Area was 1,904,806 per the 2016 census. There is archaeological debate regarding precisely where Dublin was established by the Gaels in or before the 7th century AD. Later expanded as a Viking settlement, the Kingdom of Dublin, the city became Ireland's principal settlement following the Norman invasion"));
        lstData.add( new DataItem(R.drawable.florence,"Florence",getResources().getString(R.string.florence)));
        lstData.add( new DataItem(R.drawable.frankfurt,"Frankfurt","Frankfurt, officially Frankfurt am Main, is the most populous city in the German state of Hesse. Its 763,380 inhabitants as of December 31, 2019 make it the fifth-most populous city in Germany. On the River Main (a tributary of the Rhine), it forms a continuous conurbation with the neighboring city of Offenbach am Main and its urban area has a population of 2.3 million. The city is the heart of the larger Rhine-Main Metropolitan Region, which has a population of 5.5 million and is Germany's second-largest metropolitan region after the\n" +
                "        Rhine-Ruhr Region. Frankfurt's central business district lies about 90 km (56 mi) northwest of the geographic center of the EU at Gadheim, Lower Franconia. Like France and Franconia, the city is named after the Franks. Frankfurt is the largest city in the Rhine Franconian dialect area."));
        lstData.add( new DataItem(R.drawable.hamburg,"Hamburg","  Hamburg, officially the Free and Hanseatic City of Hamburg (German: Freie und Hansestadt Hamburg; Low Saxon: Friee un Hansestadt Hamborg), is the second-largest city in Germany after Berlin and 7th largest city in the European Union with a population of over 1.84 million. Hamburg urban area has a population of around 2.5 million\n" +
                "        and its metropolitan area is home to more than five million people. The city lies on the River Elbe and two of its tributaries, the River Alster and the River Bille. One of Germany's 16 federated states, Hamburg is surrounded by Schleswig-Holstein to the north and Lower Saxony to the south."));
        lstData.add( new DataItem(R.drawable.iasi,"Iasi",getResources().getString(R.string.iasi)));
        lstData.add( new DataItem(R.drawable.lisbon,"Lisbon"," Lisbon is the capital and the largest city of Portugal, with an estimated population of 505,526 within its administrative limits in an area of 100.05 km2. Lisbon urban area extends beyond the city administrative limits with a population of around 2.0 million people, being the 10th-most populous urban area in the European Union. About 2.8 million people live in the Lisbon metropolitan area, which represents approximately 27% of the country's population. It is mainland Europe's westernmost capital city and the only one along the Atlantic coast. Lisbon lies in the western Iberian Peninsula on the Atlantic Ocean and the River Tagus. The westernmost portions of its metro area, the Portuguese Riviera, form the westernmost point of Continental Europe, culminating at Cabo da Roca."));
        lstData.add( new DataItem(R.drawable.milan,"Milan",getResources().getString(R.string.milan)));
        lstData.add( new DataItem(R.drawable.munchen,"Munchen",getResources().getString(R.string.munich)));
        lstData.add( new DataItem(R.drawable.naples,"Naples",getResources().getString(R.string.naples)));
        lstData.add( new DataItem(R.drawable.paris,"Paris",getResources().getString(R.string.paris)));
        lstData.add( new DataItem(R.drawable.prague,"Prague",getResources().getString(R.string.prague)));

        lstData.add( new DataItem(R.drawable.rome,"Rome",getResources().getString(R.string.rome)));
        lstData.add( new DataItem(R.drawable.salonik,"Thesalloniki",getResources().getString(R.string.thessaloniki)));
        lstData.add( new DataItem(R.drawable.seville,"Seville",getResources().getString(R.string.seville)));
        lstData.add( new DataItem(R.drawable.sofia,"Sofia",getResources().getString(R.string.sofia)));
        lstData.add( new DataItem(R.drawable.stockholm,"Stockholm",getResources().getString(R.string.stockholm)));
        lstData.add( new DataItem(R.drawable.timisoara,"Timisoara",getResources().getString(R.string.timisoara)));
        lstData.add( new DataItem(R.drawable.turin,"Turin",getResources().getString(R.string.turin)));
        lstData.add( new DataItem(R.drawable.viena,"Viena",getResources().getString(R.string.viena)));
        lstData.add( new DataItem(R.drawable.warsaw,"Warsaw",getResources().getString(R.string.warsaw)));
        lstData.add( new DataItem(R.drawable.zagreb,"Zagreb",getResources().getString(R.string.zagreb)));




        //adapter=new DestAdapter((ArrayList<DataItem>) lstData,DestinationsList.this);

        getData("");
        listView.setAdapter(adapter);

        //adapter.addElement(d1);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // getData(query);
                //adapter.notifyDataSetChanged();
                DestinationsList.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // getData(newText);
                //adapter.notifyDataSetChanged();
                DestinationsList.this.adapter.getFilter().filter(newText);

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent();

                 intent.putExtra("City", lstData.get(position).getCityName());
                 intent.putExtra("Description", lstData.get(position).getDescription());
                 intent.putExtra("Picture", lstData.get(position).getResIdThumbnail());
                Log.v("info",lstData.get(position).getCityName());
                    intent.setClass(DestinationsList.this, DestinationActivity.class);
                    startActivity(intent);
            }
        });







    }

private void getData(CharSequence query)
    {
        List<DataItem> output=new ArrayList<>();
        List<DataItem> filteredOutput=new ArrayList<>();

        for(DataItem item:lstData)
        {
            output.add(item);
        }

            for (DataItem item:lstData) {
                if(item.getCityName().toLowerCase().startsWith(query.toString().toLowerCase()))
                    filteredOutput.add(item);
            }
        if(filteredOutput!=null)
       output=filteredOutput;
        List<DataItem> lst2=new ArrayList<>();
        adapter=new DestAdapter((ArrayList<DataItem>) output, DestinationsList.this);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.search_menu,menu);
//        MenuItem searchItem=menu.findItem(R.id.app_bar_search);
//        searchView=(SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setQueryHint("Search");
//        searchView.setIconifiedByDefault(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                getData(newText);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
}