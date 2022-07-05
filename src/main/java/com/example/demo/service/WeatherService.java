package com.example.demo.service;

import com.example.demo.config.RestTemplateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class WeatherService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public Object getWeather(Float lon, Float lat) {
        RestTemplate template = RestTemplateConfig.restTemplate;
        String url = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&hourly=temperature_2m";
//      old variables i made but found a more efficient method
//      String lonUrl = "longitude=".concat(lon);
//      String latUrl = "latitude=".concat(lat);
//      String urlReq = url.concat(lonUrl).concat("&").concat(latUrl);

        Object weatherApiResponse = null;
        HashMap<String, Float> params= new HashMap<>();
        params.put("lon", lon);
        params.put("lat", lat);
        try {
            weatherApiResponse = template.getForEntity(url, Object.class, params);
        } catch (RestClientException ex) {
            logger.error("ERROR : {}", ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        return weatherApiResponse;
    }
//    public Object GetWeatherCity(String city){
//        RestTemplate template = RestTemplateConfig.restTemplate;
//
//        Object weatherApiReponse = null;
//
//        if (city == "newyork") {
//            // block of code to be executed if the condition is true
//        }
//        String url = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&hourly=temperature_2m";
//
//        return 1;
//    }

}
