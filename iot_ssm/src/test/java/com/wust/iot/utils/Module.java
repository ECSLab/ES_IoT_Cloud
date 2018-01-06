package com.wust.iot.utils;

import java.util.ArrayList;
import java.util.List;

public class Module {

    int moduleId;

    String moduleName;

    List<Qx> qxList = new ArrayList<Qx>();

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<Qx> getQxList() {
        return qxList;
    }

    public void setQxList(List<Qx> qxList) {
        this.qxList = qxList;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                ", qxList=" + qxList +
                '}';
    }
}
