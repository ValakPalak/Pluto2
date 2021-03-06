package com.example.plutoacademy;

public class SmModel {
    public String getLogo() {
        return logo;
    }

    public String getLink() {
        return link;
    }

    public SmModel(String logo, String link) {
        this.logo = logo;
        this.link = link;
    }
    
    private String logo;
    String link;

}
