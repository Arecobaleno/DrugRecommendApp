package com.cad.entity;

import java.util.List;

/**
 * 疾病详情结构
 */
public class DiseaseDetail {
    private String drugName;
    private String type;
    private Object property;
    private List<String> banPeople;
    private String purpose;

    public Object getProperty() {
        return property;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getPurpose() {
        return purpose;
    }

    public List<String> getBanPeople() {
        return banPeople;
    }

    public void setBanPeople(List<String> banPeople) {
        this.banPeople = banPeople;
    }

    public String getType() {
        return type;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setProperty(Object property) {
        this.property = property;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }


    public void setType(String type) {
        this.type = type;
    }
}
