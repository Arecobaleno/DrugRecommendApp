package com.cad.entity;

import java.util.List;
import java.util.Stack;

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
