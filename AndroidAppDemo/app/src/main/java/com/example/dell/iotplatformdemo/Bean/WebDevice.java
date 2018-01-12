package com.example.dell.iotplatformdemo.Bean;

/**
 * Created by DELL on 2017/12/18.
 */

public class WebDevice {
    public int id;
    public String deviceId;
    public String dataValue;
    public String createTime;

    public WebDevice(int id, String deviceId, String dataValue, String createTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.dataValue = dataValue;
        this.createTime = createTime;
    }

    public WebDevice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
