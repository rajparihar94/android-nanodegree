package com.example.pratik.bts;

/**
 * Created by prati on 23-May-16.
 */
public class Driver {
    private String bus_no, driver_name, driver_no;

    public Driver(){
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_no() {
        return driver_no;
    }

    public void setDriver_no(String driver_no) {
        this.driver_no = driver_no;
    }

    public Driver(String bus_no, String driver_name, String driver_no){
        this.bus_no = bus_no;
        this.driver_name = driver_name;
        this.driver_no = driver_no;
    }
}
