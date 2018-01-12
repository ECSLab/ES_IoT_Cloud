package com.example.dell.iotplatformdemo.Bean;

import java.io.Serializable;

/**
 * Created by DELL on 2017/12/11.
 */

public class Device implements Serializable{
    private String device_id;
    private String device_name;
    private String device_type;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public Device(String device_id, String device_name, String device_type) {
        this.device_id = device_id;
        this.device_name = device_name;
        this.device_type = device_type;
    }

    public Device() {
    }

    @Override
    public String toString() {
        return "Device{" +
                "device_id=" + device_id +
                ", device_name='" + device_name + '\'' +
                ", device_type='" + device_type + '\'' +
                '}';
    }
}
