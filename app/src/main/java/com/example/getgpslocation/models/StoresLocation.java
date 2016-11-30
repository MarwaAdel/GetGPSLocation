package com.example.getgpslocation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user on 11/29/2016.
 */

public class StoresLocation implements Serializable {
    private String id;
    private String title;
    private String agent_id;
    private String agent_name;
    private String lat;
    @SerializedName("long")
    private String loge;
    private String created_at;
    private int counter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLoge() {
        return loge;
    }

    public void setLoge(String loge) {
        this.loge = loge;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}