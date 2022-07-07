package com.example.demo.service;

import com.example.demo.config.RestTemplateConfig;
import com.example.demo.model.WeatherRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class WeatherService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * This method is the main service method.
     * It will determine whether the user passes lat / long combo or city / state combo
     * It will call the MapQuest geocoding API if user passes city state,
     *      then it will call the weather API using the latitude and longitude
     *
     * @param weatherRequestModel The request model parsed from the user's POST request
     *
     * @return a response object with weather information for the location requested
     */
    public Object getWeather(WeatherRequestModel weatherRequestModel){
        Float lat = weatherRequestModel.getLatitude();
        Float lon = weatherRequestModel.getLongitude();
        String city = weatherRequestModel.getCity();
        String state = weatherRequestModel.getState();

        // If latitude is null, we assume user passes valid city / state combination
        if(lat==null){
            // Get the latitude and longitude of the city / state combination
            List<LinkedHashMap> results = geocodeCityStateRequest(city, state);
            List<LinkedHashMap> locations = (List<LinkedHashMap>) results.get(0).get("locations");
            LinkedHashMap<String, Double> coords = (LinkedHashMap<String, Double>) locations.get(0).get("latLng");

            logger.info("Coordinates for {},{}: {}", city, state, coords);
            lat = coords.get("lat").floatValue();
            lon = coords.get("lng").floatValue();
        }

        // Call the weather API with latitude and longitude
        return callWeatherAPI(lat, lon);
    }

    /**
     * This method is the callWeatherAPI method.
     * It returns a array of information based on lat/ long combo
     * It will call the Open-Meteo API
     * @param lon longitude
     * @param lat latitude
     * @return a response object with weather information from the lat/long combo
     */
    public Object callWeatherAPI(Float lon, Float lat) {
        RestTemplate template = RestTemplateConfig.restTemplate;
        String url = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&hourly=temperature_2m";
//      Old code I wrote but found a more dynamic/efficient method
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
    public List<LinkedHashMap> geocodeCityStateRequest(String city, String state){
        RestTemplate template = RestTemplateConfig.restTemplate;

        ResponseEntity<LinkedHashMap> weatherApiReponse = null;
        HashMap<String, String> params= new HashMap<>();
        params.put("city", city);
        params.put("state", state);

        String url = "http://www.mapquestapi.com/geocoding/v1/address?key=Kt6l0gI1BIV4P6OZdI3GRvraN3GjCYaK&location={city},{state}";

        try {
            weatherApiReponse = template.getForEntity(url, LinkedHashMap.class, params);
        } catch(RestClientException ex){
            logger.error("ERROR : {}", ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        return (List<LinkedHashMap>) weatherApiReponse.getBody().get("results");
    }

}
