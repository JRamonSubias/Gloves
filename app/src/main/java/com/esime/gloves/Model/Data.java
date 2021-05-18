package com.esime.gloves.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;

public class Data{

    @SerializedName("Patient")
    @Expose
    private String patient;

    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Body")
    @Expose
    private String body;

    @SerializedName("story_id")
    @Expose
    private String storyId;

    @SerializedName("Time")
    @Expose
    private String Time;

    @SerializedName("Type")
    @Expose
    private String type;



    public Data(String patient, String title, String body, String storyId,String Time,String type) {
        this.patient = patient;
        this.title = title;
        this.body = body;
        this.storyId = storyId;
        this.Time = Time;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }
}