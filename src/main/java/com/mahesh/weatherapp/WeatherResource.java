package com.mahesh.weatherapp;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
 
import java.util.HashMap;
 
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
 

public class WeatherResource {
 
    private static final String API_CONTEXT = "/api/v1";
 
    private final WeatherService weatherService;
 
    public WeatherResource(WeatherService weatherService) {
        this.weatherService = weatherService;
        setupEndpoints();
    }
 
    private void setupEndpoints() {
        post(API_CONTEXT + "/weather", "application/json", (request, response) -> {
        	weatherService.create(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());
 
        get(API_CONTEXT + "/weather/:id", "application/json", (request, response)-> {
                	String data = weatherService.findByCityCountry(request.params(":id"));
                	return data;
                });
 
        get(API_CONTEXT + "/weather/cities", "application/json", (request, response)
 
                -> weatherService.findAll(), new JsonTransformer());
 
        put(API_CONTEXT + "/weather/:id", "application/json", (request, response)
 
                -> weatherService.update(request.params(":id"), request.body()), new JsonTransformer());
    }
 
 
}