package br.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Enterprise extends Catalog implements Serializable {
    @SerializedName("enterprise_name")
    private String enterprise_name;

    @SerializedName("description")
    private String description;

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("photo")
    private String photo;

    @SerializedName("enterprise_type")
    private EnterpriseType enterprise_type;

    public Enterprise(String enterprise_name, String description, String city, String country, String photo, EnterpriseType enterprise_type) {
        this.enterprise_name = enterprise_name;
        this.description = description;
        this.city = city;
        this.country = country;
        this.photo = photo;
        this.enterprise_type = enterprise_type;
    }

    public Enterprise(String enterprise_name, String description, String photo) {
        this.enterprise_name = enterprise_name;
        this.description = description;
        this.photo = photo;
    }

    public Enterprise() {
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoto() {
        return "https://empresas.ioasys.com.br/" + photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public EnterpriseType getEnterprise_type() {
        return enterprise_type;
    }

    public void setEnterprise_type(EnterpriseType enterprise_type) {
        this.enterprise_type = enterprise_type;
    }
}
