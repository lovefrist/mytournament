package com.example.firsttopic.RedGreenReed;

public class GetAppdata {
//    "YellowTime": 5,
//            "GreenTime": 55,
//            "RedTime": 5,
//            "RESULT": "S",
//            "ERRMSG": "成
    private int id;
    private int YellowTime;
    private int GreenTime;
    private int RedTime;
    private String RESULT;
    private String ERRMSG;

    public int getId() {
        return id;
    }

    public int getYellowTime() {
        return YellowTime;
    }

    public int getGreenTime() {
        return GreenTime;
    }

    public int getRedTime() {
        return RedTime;
    }

    public String getRESULT() {
        return RESULT;
    }

    public String getERRMSG() {
        return ERRMSG;
    }



    public void setId(int id) {
        this.id = id;
    }
}
