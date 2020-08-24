package com.cad.pojo;

import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fs.chunks")
public class Chunks {
    private ObjectId _id;
    private ObjectId files_id;
    private int n;
    private Binary data;
}
