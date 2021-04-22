package com.cad.service;

import com.cad.entity.MakerResponse;
import com.cad.pojo.Guide;
import com.cad.pojo.Maker;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class GuideService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    // 下载指南
    public void guideDownload(String title, HttpServletResponse response) throws IOException {
        Query query = new Query();
        query.addCriteria(Criteria.where("filename").is(title));
        GridFSFile result =  gridFsTemplate.findOne(query);
        GridFsResource gs = gridFsTemplate.getResource(result);
        OutputStream os = null;
        try {
            byte[] bs = new byte[1024];
            int len=0;
            InputStream in = gs.getInputStream();
            File tempFile = File.createTempFile(title,".pdf");
            os = new FileOutputStream(tempFile);
            while ((len = in.read(bs))!=-1) {
                os.write(bs, 0, len);
            }
            String path = tempFile.getAbsolutePath();
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(title+".pdf", "UTF-8"));
            response.setContentType("application/pdf");
            InputStream fis = new FileInputStream(tempFile);
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            len=0;
            while ((len = fis.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    // 获取指南详情
    public List<Guide> guideDetail(String title){
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        List<Guide> sample = mongoTemplate.find(query, Guide.class);
        if(sample.size()==1){
            Integer count = sample.get(0).getCount();
            Update update = Update.update("count", count+1);
            mongoTemplate.updateMulti(query, update, "Guideline");
        }
        return sample;
    }

    // 获取制定者列表
    public List<Maker> getMakerList(){
        Query query = new Query();
        return mongoTemplate.findAll(Maker.class);
    }

    // 制定者搜索
    public List<MakerResponse> getMakerSearch(String content){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(content));
        List<Maker> makers = mongoTemplate.find(query, Maker.class);
        List<MakerResponse> makerResponses = new ArrayList<>();
        for(Maker maker:makers){
            MakerResponse makerResponse = new MakerResponse();
            makerResponse.setName(maker.getName());
            makerResponses.add(makerResponse);
        }
        return makerResponses;
    }

    // 返回 搜索 指南列表
    public List<Guide> guideSearchList(String word){
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("title").regex(word),
                Criteria.where("time").regex(word),
                Criteria.where("maker").regex(word));
        Query query = new Query(criteria);
        query.with(Sort.by(
                Sort.Order.desc("count")
        ));
        return mongoTemplate.find(query, Guide.class);
    }

    // 返回 最新 指南列表
    public List<Guide> guideNewList() {
        Query query = new Query();
        query.with(Sort.by(
                Sort.Order.desc("time")
        ));
        query.limit(10);
        return mongoTemplate.find(query, Guide.class);
    }

    // 返回 按制定者查询 指南列表
    public List<Guide> guideMakerList(String word) {
        Query query = new Query();
        query.addCriteria(Criteria.where("maker").regex(word));
        return mongoTemplate.find(query, Guide.class);
    }

    // 返回 按年份查询 指南列表
    public List<Guide> guideYearList(String category, String word) {
        List<Guide> res;
        if(word.equals("all")){
            Query query = new Query();
            if(category.equals("hot")){
                query.with(Sort.by(
                        Sort.Order.desc("count")
                ));
            } else{
                query.with(Sort.by(
                        Sort.Order.desc("time")
                ));
            }
            res = mongoTemplate.find(query, Guide.class);
        } else {
            if(category.equals("hot")){
                Query query = new Query();
                query.addCriteria(Criteria.where("time").regex(word));
                query.with(Sort.by(
                        Sort.Order.desc("count")
                ));
                res = mongoTemplate.find(query, Guide.class);
            } else{
                Query query = new Query();
                query.addCriteria(Criteria.where("time").regex(word));
                query.with(Sort.by(
                        Sort.Order.desc("time")
                ));
                res = mongoTemplate.find(query, Guide.class);
            }
        }
        return res;
    }

}
