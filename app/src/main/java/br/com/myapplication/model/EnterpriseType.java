package br.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class EnterpriseType {
    @SerializedName("id")
    private String id;

    @SerializedName("enterprise_type_name")
    private String enterprise_type_name;

    public EnterpriseType(String id, String enterprise_type_name) {
        this.id = id;
        this.enterprise_type_name = enterprise_type_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterprise_type_name() {
        return enterprise_type_name;
    }

    public void setEnterprise_type_name(String enterprise_type_name) {
        this.enterprise_type_name = enterprise_type_name;
    }

    @Override
    public String toString() {
        return enterprise_type_name;
    }
}
