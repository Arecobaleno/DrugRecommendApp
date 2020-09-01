package com.cad.entity;

import org.bson.types.ObjectId;

import java.util.List;

public class InteractionResult {
    private Object sourceName;
    private Object edgeResult;
    private Object targetName;

    public Object getSourceName() {
        return sourceName;
    }

    public void setSourceName(Object sourceName) {
        this.sourceName = sourceName;
    }

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
