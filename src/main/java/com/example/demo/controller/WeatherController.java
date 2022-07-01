package com.example.demo.controller;

import com.example.demo.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WeatherService weatherService;

    @GetMapping("/hello")
    public Object hello() {
        logger.info("endpoint /hello query hit sucessfully");
        return weatherService.getWeather((float) -71.00, 42.00F);
    }
}
