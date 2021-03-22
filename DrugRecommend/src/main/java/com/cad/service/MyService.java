package com.cad.service;

import com.cad.entity.Result;
import com.cad.entity.StatusCode;
import com.cad.pojo.AppUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    private MongoTemplate mongoTemplate;

    // 注册功能
    public Result register(AppUsers appUsers){
        Query query = new Query();
        query.addCriteria(Criteria.where("nickname").is(appUsers.getNickname()));
        if(mongoTemplate.find(query, AppUsers.class).size()>0){
            return new Result(false, StatusCode.ERROR,"用户名已存在");
        }
        appUsers.setAuthority(1);
        mongoTemplate.insert(appUsers, "AppUsers");
        return new Result(true, StatusCode.OK,"用户名注册成功");
    }

    // 登录功能
    public Result login(String nickname, String password){
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("nickname").is(nickname),
                Criteria.where("password").is(password));
        Query query = new Query(criteria);
        if(mongoTemplate.find(query, AppUsers.class).size()==1){
            return new Result(true, StatusCode.OK,"登录成功");
        }
        return new Result(false, StatusCode.LOGINERROR,"用户名或密码错误");
    }

    // 返回个人信息
    public AppUsers info(String nickname){
        Query query = new Query();
        query.addCriteria(Criteria.where("nickname").is(nickname));
        return mongoTemplate.find(query, AppUsers.class).get(0);
    }
}
