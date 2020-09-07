package com.cad.entity;

import java.util.List;


/**
 * 疾病树型结构返回
 */
public class DiseaseTreeResult {
    private DiseaseTree diseaseTree;
    private List<String> path;

    public DiseaseTree getDiseaseTree() {
        return diseaseTree;
    }

    public void setDiseaseTree(DiseaseTree diseaseTree) {
        this.diseaseTree = diseaseTree;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public List<String> getPath() {
        return path;
    }
}
