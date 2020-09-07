package com.cad.entity;

import java.util.List;

/**
 * 疾病详情返回结构
 */
public class DiseaseResult {
    private List<DiseaseDetail> indication;
    private List<DiseaseDetail> contraindication;

    public List<DiseaseDetail> getContraindication() {
        return contraindication;
    }

    public List<DiseaseDetail> getIndication() {
        return indication;
    }

    public void setIndication(List<DiseaseDetail> indication) {
        this.indication = indication;
    }

    public void setContraindication(List<DiseaseDetail> contraindication) {
        this.contraindication = contraindication;
    }
}
