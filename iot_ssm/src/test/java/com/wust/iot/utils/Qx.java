package com.wust.iot.utils;

public class Qx {

    int qxId;

    String qxName;

    public Qx() {
    }

    public Qx(int qxId, String qxName) {
        this.qxId = qxId;
        this.qxName = qxName;
    }

    public int getQxId() {
        return qxId;
    }

    public void setQxId(int qxId) {
        this.qxId = qxId;
    }

    public String getQxName() {
        return qxName;
    }

    public void setQxName(String qxName) {
        this.qxName = qxName;
    }

    @Override
    public String toString() {
        return "Qx{" +
                "qxId=" + qxId +
                ", qxName='" + qxName + '\'' +
                '}';
    }
}
