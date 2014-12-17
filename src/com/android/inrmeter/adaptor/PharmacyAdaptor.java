package com.android.inrmeter.adaptor;



import java.util.List;

import com.android.inrmeter.activity.R;
import com.android.inrmeter.activity.R.id;
import com.android.inrmeter.model.Pharmacy;
import com.android.inrmeter.model.Inr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PharmacyAdaptor extends ArrayAdapter<Pharmacy>{
	 
    Context context;
    int layoutResourceId;
    List<Pharmacy> inrs = null;
     
    public PharmacyAdaptor(Context context, int resource, List<Pharmacy> inrs) {
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
             
           
            holder.pharmacyName = (TextView) row.findViewById(com.android.inrmeter.activity.R.id.pharmacyName);
       
            row.setTag(holder);
        }
        else{
            holder = (InrViewHolder) row.getTag();
        }
         
        Pharmacy inr_ = inrs.get(position);
        
        holder.pharmacyName.setText(inr_.getPharmacyName());
   
        
        return row;
    }
     
     
    static class InrViewHolder{
        TextView pharmacyName;
   

    }
}