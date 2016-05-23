package com.example.pratik.bts;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by prati on 23-May-16.
 */
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder> {
    private List<Driver> driverList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView driver_name, driver_no, bus_no;

        public MyViewHolder(View view) {
            super(view);
            driver_name = (TextView) view.findViewById(R.id.driver_name);
            driver_no = (TextView) view.findViewById(R.id.driver_no);
            bus_no = (TextView) view.findViewById(R.id.bus_no);
        }
    }

    public DriverAdapter(List<Driver> driverList){
        this.driverList = driverList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.driver_detail_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Driver driver = driverList.get(position);
        holder.driver_name.setText(driver.getDriver_name());
        holder.driver_no.setText(driver.getDriver_no());
        holder.bus_no.setText(driver.getBus_no());
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }


}
