package com.lvqingyang.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 一句话功能描述
 * 功能详细描述
 *
 * @author Lv Qingyang
 * @date 2017/12/3
 * @email biloba12345@gamil.com
 * @github https://github.com/biloba123
 * @blog https://biloba123.github.io/
 */
public class Reponse implements Parcelable {
    private int code;
    private String message;
    private String data;

    public Reponse(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeString(this.data);
    }

    protected Reponse(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<Reponse> CREATOR = new Parcelable.Creator<Reponse>() {
        @Override
        public Reponse createFromParcel(Parcel source) {
            return new Reponse(source);
        }

        @Override
        public Reponse[] newArray(int size) {
            return new Reponse[size];
        }
    };
}
