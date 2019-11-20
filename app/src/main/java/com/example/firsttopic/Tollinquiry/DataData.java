package com.example.firsttopic.Tollinquiry;

public class DataData {
    private int []month = {31,28,31,30,31,30,31,31,30,31,30,31};
    private boolean leapryear;
     DataData(int year) {
        if ((year % 100 == 0 && year % 400 == 0) || year % 4 == 0) {
            leapryear= true;
        } else leapryear= false;
        if(leapryear)
            month[1] = 29;
        else
            month[1] = 28;
    }

    public int getMonth(int i) {
        return month[i];
    }

}
