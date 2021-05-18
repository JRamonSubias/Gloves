package com.esime.gloves.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class NotificationEntity {

    @PrimaryKey(autoGenerate = true)
    private int idNotification;

    private String patient;
    private String title;
    private String body;
    private String dataTime;
    private String type;


    public NotificationEntity(String patient, String title, String body,String dataTime,String type) {
        this.patient = patient;
        this.title = title;
        this.body = body;
        this.dataTime = dataTime;
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        patient = patient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
