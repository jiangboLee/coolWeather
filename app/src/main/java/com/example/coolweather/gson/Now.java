package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xindongli on 17/3/16.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
