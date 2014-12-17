package com.android.inrmeter.adaptor;



import java.util.List;

import com.android.inrmeter.activity.R;
import com.android.inrmeter.activity.R.id;
import com.android.inrmeter.model.Hospital;
import com.android.inrmeter.model.Inr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HospitalAdaptor extends ArrayAdapter<Hospital>{
	 
    Context context;
    int layoutResourceId;
    List<Hospital> inrs = null;
     
    public HospitalAdaptor(Context context, int resource, List<Hospital> inrs) {
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
             
           
            holder.hospitalName = (TextView) row.findViewById(com.android.inrmeter.activity.R.id.hospitalName);
       
            row.setTag(holder);
        }
        else{
            holder = (InrViewHolder) row.getTag();
        }
         
        Hospital inr_ = inrs.get(position);
        
        holder.hospitalName.setText(inr_.getHospitalName());
   
        
        return row;
    }
     
     
    static class InrViewHolder{
        TextView hospitalName;
   

    }
}