package com.wust.iot.utils;

import java.util.ArrayList;
import java.util.List;

public class Result {

    int roleId;

    String roleName;

    List<Module> moduleList = new ArrayList<Module>();

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }


    @Override
    public String toString() {
        return "Result{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", moduleList=" + moduleList +
                '}';
    }
}
