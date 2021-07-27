package br.com.myapplication.model;

import java.util.ArrayList;

public class Catalog {

    ArrayList<Enterprise> enterprises;

    public Catalog() {

    }

    public ArrayList<Enterprise> getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(ArrayList<Enterprise> enterprises) {
        this.enterprises = enterprises;
    }
}
