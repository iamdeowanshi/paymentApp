package com.tecsol.batua.user.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Farhan Ali
 */
public class Task extends BaseModel {

    @SerializedName("id")
    private int id;
    @SerializedName("taskName")
    private String taskName;
    @SerializedName("description")
    private String description;
    @SerializedName("status")
    private String status;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
