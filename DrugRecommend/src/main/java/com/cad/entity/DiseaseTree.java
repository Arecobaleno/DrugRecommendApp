package com.cad.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * 疾病树
 */

@Document(collection = "HypertensionTree")
public class DiseaseTree {
    private String name;
    private List<String> leafTitle;
    private List<DiseaseTree> subTitle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DiseaseTree> getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(List<DiseaseTree> subTitle) {
        this.subTitle = subTitle;
    }

    public void setLeafTitle(List<String> leafTitle) {
        this.leafTitle = leafTitle;
    }

    public List<String> getLeafTitle() {
        return leafTitle;
    }
}
