package com.example.dell.iotplatformdemo.Bean;

import java.util.List;

/**
 * Created by DELL on 2017/12/15.
 */

public class Scene {
    private int id;
    private String name;
    private int number;
    private List<Device> deviceList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Scene(String name, int number, List<Device> deviceList) {
        this.name = name;
        this.number = number;
        this.deviceList = deviceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public Scene() {
    }
    public Scene(int id, String name, int number, List<Device> deviceList) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.deviceList = deviceList;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", deviceList=" + deviceList +
                '}';
    }
}
