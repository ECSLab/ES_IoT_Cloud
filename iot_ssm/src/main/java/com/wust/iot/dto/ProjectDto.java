package com.wust.iot.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ProjectDto {

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "行业id",notes = "请调用行业接口获得")
    private Integer industryId;

    @ApiModelProperty(value = "项目简介",notes = "限制字数500")
    private String profile;

    public ProjectDto() {
    }

    public ProjectDto(String name, Integer industryId, String profile) {
        this.name = name;
        this.industryId = industryId;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.profile = profile;
    }
}
