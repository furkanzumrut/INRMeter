package com.android.inrmeter.adaptor;

import java.util.List;

import com.android.inrmeter.activity.R;
import com.android.inrmeter.activity.R.id;
import com.android.inrmeter.model.Inr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InrAdaptor extends ArrayAdapter<Inr>{
	 
    Context context;
    int layoutResourceId;
    List<Inr> inrs = null;
     
    public InrAdaptor(Context context, int resource, List<Inr> inrs) {
        super(context, resource, inrs);
        this.context = context;
        this.layoutResourceId = resource;
        this.inrs = inrs;
    }
     
     
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        InrViewHolder holder = null;
         
         
        if(row == null){
             
            LayoutInflater inflater = LayoutInflater.from(context);
             
              
            row = inflater.inflate(layoutResourceId, parent, false);
             
            holder = new InrViewHolder();
             
           
            holder.inrValue = (TextView) row.findViewById(com.android.inrmeter.activity.R.id.inrValue);
            holder.inrDate = (TextView) row.findViewById(com.android.inrmeter.activity.R.id.inrDate);
            holder.inrTime = (TextView) row.findViewById(com.android.inrmeter.activity.R.id.inrTime);
            row.setTag(holder);
        }
        else{
            holder = (InrViewHolder) row.getTag();
        }
         
        Inr inr_ = inrs.get(position);
        
        holder.inrValue.setText(String.valueOf(inr_.getInrValue()));
        holder.inrDate.setText(String.valueOf(inr_.getInrDate()));
        holder.inrTime.setText(String.valueOf(inr_.getInrTime()));
        return row;
    }
     
     
    static class InrViewHolder{
        TextView inrValue;
        TextView inrDate;
        TextView inrTime;
    }
}