package com.wust.iot.dto;

import io.swagger.annotations.ApiModelProperty;

public class DeviceDto {

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "协议id")//TODO 需要添加获取接口
    private Integer protocolId;

    @ApiModelProperty(value = "数据类型id")
    private Integer dataType;

    @ApiModelProperty(value = "设备名字")
    private String name;

    @ApiModelProperty(value = "设备编号",notes = "仅允许数字及字母")
    private String number;

    @ApiModelProperty(value = "设备保密性",notes = "0公开 1私有")
    private Integer privacy;

    public DeviceDto() {
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Integer protocolId) {
        this.protocolId = protocolId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Integer privacy) {
        this.privacy = privacy;
    }

    public DeviceDto(Integer projectId, Integer protocolId, Integer dataType, String name, String number, Integer privacy) {
        this.projectId = projectId;
        this.protocolId = protocolId;
        this.dataType = dataType;
        this.name = name;
        this.number = number;
        this.privacy = privacy;
    }
}
