package com.example.planificareexcursii.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.planificareexcursii.R;
import com.example.planificareexcursii.utils.DataItem;

import java.util.ArrayList;
import java.util.List;

public class DestinationsAdapter extends ArrayAdapter<DataItem> {

    Context context;
    int layoutResourceId;
    List<DataItem> data=null;

    public DestinationsAdapter(@NonNull Context context, int resource, @NonNull List<DataItem> objects) {
        super(context, resource, objects);

        this.layoutResourceId=resource;
        this.context=context;
        this.data=objects;

    }

    static class DataHolder
    {
        ImageView ivFlag;
        TextView tvCountryName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataHolder holder=null;
        if(convertView==null)
        {
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            convertView=inflater.inflate(layoutResourceId,parent);
            holder=new DataHolder();
            holder.ivFlag=(ImageView)convertView.findViewById(R.id.imgCity);
            holder.tvCountryName=(TextView) convertView.findViewById(R.id.tv_destination);

            convertView.setTag(holder);


        }
        else
        {
            holder=(DataHolder)convertView.getTag();
        }

        DataItem dataItem=data.get(position);
        holder.tvCountryName.setText(dataItem.getCityName());
        holder.ivFlag.setImageResource(dataItem.getResIdThumbnail());

        return convertView;

    }
}
