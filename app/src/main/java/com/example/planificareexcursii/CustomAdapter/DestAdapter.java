package com.example.planificareexcursii.CustomAdapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.planificareexcursii.R;
import com.example.planificareexcursii.utils.DataItem;

import java.util.ArrayList;
import java.util.Locale;

public class DestAdapter extends BaseAdapter implements Filterable {
    private ArrayList<DataItem> datas;
    private ArrayList<DataItem> datas2;

    private Context context;
    private LayoutInflater inflater;



    public DestAdapter(ArrayList<DataItem> datas, Context context) {
        this.datas = datas;

        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View item=inflater.inflate(R.layout.destination_item, parent, false);

        TextView tvCity=item.findViewById(R.id.tv_destination);
        ImageView img=item.findViewById(R.id.imgCity);

        DataItem data=datas.get(position);
        tvCity.setText(data.getCityName());
        img.setImageResource(data.getResIdThumbnail());


        return item;
    }

    public void addElement(DataItem activitate){
        datas.add(activitate);
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = datas;
                    results.count = datas.size();

                }
                else
                    {
                    ArrayList<DataItem> filter_items = new ArrayList<>();

                    for(int i = 0; i < datas.size(); i++){
                        DataItem item=datas.get(i);
                        if(item.getCityName().toLowerCase().
                                startsWith(constraint.toString().toLowerCase())){
                            item.setCityName(datas.get(i).getCityName());
                            item.setResIdThumbnail(datas.get(i).getResIdThumbnail());
                            filter_items.add(item);

                        }
                    }

                        int i=0;
                        for( i = 0; i < filter_items.size(); i++){
                            //datas=filter_items;
                          datas.get(i).setCityName(filter_items.get(i).getCityName());
                          datas.get(i).setDescription(filter_items.get(i).getDescription());
                          datas.get(i).setResIdThumbnail(filter_items.get(i).getResIdThumbnail());
                        }



                        //if(i<datas.size())


                    int nr=filter_items.size();


                   // filter_items.remove(filter_items.get(0));

                    results.values = datas;
                    results.count = datas.size();

                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count == 0) {
                    notifyDataSetInvalidated();
                }
                else {
                    datas = (ArrayList<DataItem>) results.values;
                    notifyDataSetChanged();
                }
            }

        } ;


    }
}
