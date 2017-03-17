package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.AQI;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xindongli on 17/3/16.
 */

public class Utility {

    /**
     *解析和处理省级数据
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i ++) {
                    JSONObject provinceObject = allProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *城市数据
     */
    public static boolean handleCityResponse(String responce, int provinceID) {
        if (!TextUtils.isEmpty(responce)) {
            try {
                JSONArray allCitys = new JSONArray(responce);
                for (int i = 0; i<allCitys.length(); i++) {
                    JSONObject jsonObject = allCitys.getJSONObject(i);
                    City city = new City();
                    city.setCityName(jsonObject.getString("name"));
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setProvinceId(provinceID);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *县级数据
     */
    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i<allCounties.length(); i++) {
                    JSONObject countyObj = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObj.getString("name"));
                    county.setWeatherId(countyObj.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*将返回json数据解析成weather实体类*/
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
