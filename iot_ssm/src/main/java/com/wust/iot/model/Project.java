package com.wust.iot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;

public class Project {
    @ApiModelProperty(value = "项目id")
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目的行业id")
    private Integer industryId;

    @ApiModelProperty(value = "项目的行业名称")
    private String industryName;

    @ApiModelProperty(value = "项目简介")
    private String profile;

    @ApiModelProperty(value = "apiKey")
    private String apiKey;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建者id")
    private Integer userId;

    @ApiModelProperty(value = "包含的设备数目")
    private Integer deviceCount;

    @ApiModelProperty(value = "包含的设备")
    private List<Device> deviceList;



    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public Project() {
    }

    public Project(String name, Integer industryId, String profile, Integer userId) {
        this.name = name;
        this.industryId = industryId;
        this.profile = profile;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile == null ? null : profile.trim();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey == null ? null : apiKey.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", industryId=" + industryId +
                ", industryName='" + industryName + '\'' +
                ", profile='" + profile + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", deviceList=" + deviceList +
                ", deviceCount=" + deviceCount +
                '}';
    }
}