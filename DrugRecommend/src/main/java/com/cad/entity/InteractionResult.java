package com.cad.entity;

import java.util.List;

public class InteractionResult {
    private Object edgeResult;
    private Object targetName;

    public void setEdgeResult(Object edgeResult) {
        this.edgeResult = edgeResult;
    }

    public void setTargetName(Object targetName){
        this.targetName = targetName;
    }

    public Object getEdgeResult(){
        return edgeResult;
    }

    public Object getTargetName(){
        return targetName;
    }
}
