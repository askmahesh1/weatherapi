package com.mahesh.weatherapp;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
 
import java.util.Date;

public class WeatherData {

    private String id;
    private String city;
    private String country;
    private Double currentTemp;
    private Date lastChangedOn = new Date();
    private Integer sunrise;
    private Integer sunset;
    private String condition;
    private String conditionDescription;
 
    public WeatherData(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.city = dbObject.getString("city");
        this.country = dbObject.getString("country");
        this.currentTemp = dbObject.getDouble("currentTemp");
        this.lastChangedOn = dbObject.getDate("lastChangedOn");
    }
 
    public String getCity() {
        return city;
    }
 
    public String getCountry() {
        return country;
    }

    public Double getCurrentTemp() {
        return currentTemp;
    }

    public Date getLastChangedOn() {
        return lastChangedOn;
    }
}