package com.example.user.jstore_android_muhammadirsyadthoyib;

public class Location {
    private String province;
    private String name;
    private String city;

    public Location(String province, String name, String city) {
        this.province = province;
        this.name = name;
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
