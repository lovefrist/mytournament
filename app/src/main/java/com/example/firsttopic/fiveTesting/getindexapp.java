package com.example.firsttopic.fiveTesting;

public class getindexapp {
    //    {
//        "pm2.5": 169,
//            "co2": 64,
//            "LightIntensity": 2087,
//            "humidity": 27,
//            "temperature": 15,
//            "RESULT": "S",
//            "ERRMSG": "成功"
//    }
    private String pm25;
    private String co2;
    private String LightIntensity;
    private String humidity;//湿度
    private String temperature;//温度
    private int Status;

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getStatus() {
        return Status;
    }

    public String getPm25() {
        return pm25;
    }


    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public void setLightIntensity(String lightIntensity) {
        LightIntensity = lightIntensity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCo2() {
        return co2;
    }

    public String getLightIntensity() {
        return LightIntensity;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemperature() {
        return temperature;
    }


}
