package com.mahesh.weatherapp;

import com.google.gson.Gson;
import com.mongodb.*;

import org.bson.types.ObjectId;
import org.eclipse.jetty.server.HttpConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WeatherService {
	
    private final DB db;
    private final DBCollection collection;
 
    public WeatherService(DB db) {
        this.db = db;
        this.collection = db.getCollection("weatherdata");
    }
 	
    public List<WeatherData> findAll() {
        List<WeatherData> weatherdata = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            weatherdata.add(new WeatherData((BasicDBObject) dbObject));
        }
        return weatherdata;
    }
 
    public void create(String body) {
        WeatherData weather = new Gson().fromJson(body, WeatherData.class);
        collection.insert(new BasicDBObject("city", weather.getCity())
                              .append("country", weather.getCountry())
                              .append("lastChangedOn", new Date())
                              .append("currentTemp", weather.getCurrentTemp()));
    }
 
    public WeatherData find(String id) {
        return new WeatherData((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id))));
    }
 
    public String findByCityCountry(String city) {
    	BasicDBObject query = new BasicDBObject();
    	query.put("city", city);
    	//query.put("country",country);
    	return new WeatherHttpClient().getWeatherData(city, "F");
        //return new WeatherData((BasicDBObject) collection.findOne(query));
    }

    public WeatherData update(String weatherId, String body) {
    	WeatherData weatherData = new Gson().fromJson(body, WeatherData.class);
    	BasicDBObject update = new BasicDBObject("currentTemp",weatherData.getCurrentTemp());

        collection.update(new BasicDBObject("_id", new ObjectId(weatherId)), new BasicDBObject("$set",update));
        return this.find(weatherId);
    }
    
}
