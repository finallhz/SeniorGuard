package com.snnu.Utils;

public class SportData {
    private double acc;
    private double gyr;

    public SportData(double acc, double gyr) {
        this.acc = acc;
        this.gyr = gyr;
    }

    public SportData(){

    }

    public double getAcc() {
        return acc;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public double getGyr() {
        return gyr;
    }

    public void setGyr(double gyr) {
        this.gyr = gyr;
    }

    @Override
    public String toString() {
        return "SportData{" +
                "acc=" + acc +
                ", gyr=" + gyr +
                '}';
    }
}
