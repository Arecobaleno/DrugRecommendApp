package com.cad.service;

import com.cad.pojo.Reference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReferenceService {
    private MongoTemplate mongoTemplate;

    public ReferenceService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Reference> getReferenceList(String category, String word){
        List<Reference> res;
        if (category.equals("new")){
            Query query = new Query();
            query.limit(6);
            res = mongoTemplate.find(query, Reference.class);
        }
        else if (category.equals("search")){
            Query query = new Query();
            query.addCriteria(Criteria.where("title").regex(word));
            res = mongoTemplate.find(query, Reference.class);
        }
        else{
            res = null;
        }
        return res;
    }

    public List<Reference> referenceDetail(String word){
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(word));
        return mongoTemplate.find(query, Reference.class);
    }
}
