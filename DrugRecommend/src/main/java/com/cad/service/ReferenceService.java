package com.cad.service;

import com.cad.pojo.Guide;
import com.cad.pojo.Reference;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.List;

@Service
public class ReferenceService {
    private MongoTemplate mongoTemplate;

    public ReferenceService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Reference> referenceSearchList(String word){
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(word));
        return mongoTemplate.find(query, Reference.class);
    }

    public List<Reference> referenceNewList(){
        Query query = new Query();
        query.limit(10);
        return mongoTemplate.find(query, Reference.class);
    }

    public List<Reference> referenceYearList(String category, String word){
        List<Reference> res;
        if(word.equals("all")){
            Query query = new Query();
            if(category.equals("hot")){
                query.with(Sort.by(
                        Sort.Order.desc("count")
                ));
            } else{
                query.with(Sort.by(
                        Sort.Order.desc("date")
                ));
            }
            res = mongoTemplate.find(query, Reference.class);
        } else {
            if(category.equals("hot")){
                Query query = new Query();
                query.addCriteria(Criteria.where("year").regex(word));
                query.with(Sort.by(
                        Sort.Order.desc("count")
                ));
                res = mongoTemplate.find(query, Reference.class);
            } else{
                Query query = new Query();
                query.addCriteria(Criteria.where("year").regex(word));
                query.with(Sort.by(
                        Sort.Order.desc("date")
                ));
                res = mongoTemplate.find(query, Reference.class);
            }
        }
        return res;
    }

    public List<Reference> referenceDetail(String word){
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(word));
        List<Reference> sample = mongoTemplate.find(query, Reference.class);
        if(sample.size()==1){
            Integer count = sample.get(0).getCount();
            Update update = Update.update("count", count+1);
            mongoTemplate.updateMulti(query, update, "TestCnkiPaper");
        }
        return sample;
    }
}
