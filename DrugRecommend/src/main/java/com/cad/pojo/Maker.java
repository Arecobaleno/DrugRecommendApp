package com.cad.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Maker")
public class Maker {
    private ObjectId _id;
    private ObjectId guideline_id;
    private String name;

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void setGuideline_id(ObjectId guideline_id) {
        this.guideline_id = guideline_id;
    }

    public ObjectId getGuideline_id() {
        return guideline_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
