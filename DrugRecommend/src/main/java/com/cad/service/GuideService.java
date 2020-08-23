package com.cad.service;

import com.cad.pojo.Guide;
import com.cad.pojo.Reference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuideService {
    private MongoTemplate mongoTemplate;

    public GuideService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //public List<>

    public List<Guide> guideList(String category, String word){
        List<Guide> res;
        if (category.equals("search")){
            Query query = new Query();
            query.addCriteria(Criteria.where("title").regex(word));
            res = mongoTemplate.find(query, Guide.class);
        }
        else if(category.equals("new")){
            Query query = new Query();
            query.limit(6);
            res = mongoTemplate.find(query, Guide.class);
        }
        else if(category.equals("year")){
            Query query = new Query();
            // query.addCriteria(Criteria.where("year").regex(word));
            res = mongoTemplate.findAll(Guide.class);
        }
        else if(category.equals("maker")){
            Query query = new Query();
            query.addCriteria(Criteria.where("maker").regex(word));
            res = mongoTemplate.find(query, Guide.class);
        }
        else {
            res = null;
        }
        return res;
    }

}
