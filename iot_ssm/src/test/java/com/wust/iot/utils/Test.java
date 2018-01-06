package com.wust.iot.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {


    public static void main(String[] args) {
        List<Map<String,Object>> sqlList = new ArrayList<Map<String, Object>>();
        Map<String,Object> map1 = new HashMap<String,Object>();
        Map<String,Object> map2 = new HashMap<String,Object>();
        Map<String,Object> map3 = new HashMap<String,Object>();

        //添加元素map1
        map1.put("roleId",1);
        map1.put("roleName","角色1");
        map1.put("moduleId",1);
        map1.put("moduleName","模块1");
        map1.put("qxId",1);
        map1.put("qxName","权限1");
        //添加元素map2
        map2.put("roleId",1);
        map2.put("roleName","角色1");
        map2.put("moduleId",2);
        map2.put("moduleName","模块2");
        map2.put("qxId",2);
        map2.put("qxName","权限2");
        //添加元素map3
        map3.put("roleId",1);
        map3.put("roleName","角色1");
        map3.put("moduleId",3);
        map3.put("moduleName","模块3");
        map3.put("qxId",3);
        map3.put("qxName","权限3");
        sqlList.add(map1);
        sqlList.add(map2);
        sqlList.add(map3);


        //声明一个返回前端的list
        List<Result> resultList = new ArrayList<Result>();
        //遍历sqlList
        for (Map m : sqlList){
            int roleId = Integer.parseInt(m.get("roleId").toString());
            String roleName = m.get("roleName").toString();
            int resultFlag = 0;
            for (Result r : resultList){
                if (r.getRoleId() == roleId){
                    resultFlag = 1;
                    //存在,则查看是否需要添加的module
                    int moduleId = Integer.parseInt(m.get("moduleId").toString());
                    String moduleName = m.get("moduleName").toString();
                    int moduleFlag = 0;
                    for (Module mo : r.getModuleList()){
                        if (mo.getModuleId() == moduleId){
                            moduleFlag = 1;
                            //存在moudleId,则查看是否有需要添加的权限
                            int qxFlag = 0;
                            int qxId = Integer.parseInt(m.get("qxId").toString());
                            String qxName = m.get("qxName").toString();
                            for (Qx qx : mo.getQxList()){
                                if (qxId == qx.getQxId()){
                                    //存在，flag置位1，退出
                                    qxFlag = 1;
                                    break;
                                }
                            }
                            if(qxFlag == 0){
                                //添加
                                Qx qx = new Qx();
                                qx.setQxId(qxId);
                                qx.setQxName(qxName);
                                mo.getQxList().add(qx);
                            }
                            break;
                        }
                    }
                    if (moduleFlag == 0){
                        //不存在moduleId
                        //添加module
                        Module module = new Module();
                        module.setModuleId(moduleId);
                        module.setModuleName(moduleName);
                        //添加qx
                        Qx qx = new Qx();
                        int qxId = Integer.parseInt(m.get("qxId").toString());
                        String qxName = m.get("qxName").toString();
                        qx.setQxId(qxId);
                        qx.setQxName(qxName);
                    }
                    break;
                }
            }
            if (resultFlag == 0){
                //不存在该用户
                Result newR = new Result();
                newR.setRoleId(roleId);
                newR.setRoleName(roleName);
                //添加module
                Module module = new Module();
                int moduleId = Integer.parseInt(m.get("moduleId").toString());
                String moduleName = m.get("moduleName").toString();
                module.setModuleId(moduleId);
                module.setModuleName(moduleName);

                //添加qx
                Qx qx = new Qx();
                int qxId = Integer.parseInt(m.get("qxId").toString());
                String qxName = m.get("qxName").toString();
                qx.setQxId(qxId);
                qx.setQxName(qxName);
                module.getQxList().add(qx);
                newR.getModuleList().add(module);

                resultList.add(newR);
            }
        }


        for (Result result : resultList){
            System.out.println(result);
        }
    }

}
