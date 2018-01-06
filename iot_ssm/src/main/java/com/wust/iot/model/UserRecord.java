package com.wust.iot.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserRecord {
    private Integer id;

    private Integer userId;

    private Integer recordId;

    private String data;

    public UserRecord() {
    }

    public UserRecord(Integer userId, Integer recordId, String data, Date createTime) {
        this.userId = userId;
        this.recordId = recordId;
        this.data = data;
        this.createTime = createTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", recordId=" + recordId +
                ", data='" + data + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}