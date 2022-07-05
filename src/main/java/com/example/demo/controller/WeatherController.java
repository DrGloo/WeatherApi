package com.example.demo.controller;

import com.example.demo.model.WeatherRequestModel;
import com.example.demo.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WeatherService weatherService;

    @PostMapping("/hello")
    public Object hello(@RequestBody WeatherRequestModel requestBody) {
        logger.info("endpoint /hello query hit sucessfully");
        return weatherService.getWeather(requestBody.getLongitude(), requestBody.getLatitude());
    }

}
