package com.wust.iot.enums;

public enum  RecordEnums {
    CREATE_PROJECT(1,"添加新项目"),
    CREATE_DEVICE(2,"添加新设备"),
    DELETE_PROJECT(3,"删除项目"),
    DELETE_DEVICE(4,"删除设备"),
    ;

    public int id;

    public String name;

    RecordEnums(int id, String name) {
        this.id = id;
        this.name = name;
    }

    RecordEnums() {
    }
}
