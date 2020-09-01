package com.cad.service;

import com.cad.pojo.Guide;
import com.cad.pojo.Maker;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
            System.out.println("path="+path);
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
        return mongoTemplate.find(query, Guide.class);
    }

    // 获取制定者列表
    public List<Maker> getMakerList(){
        Query query = new Query();
        return mongoTemplate.findAll(Maker.class);
    }

    // 获取指南列表
    public List<Guide> guideList(String category, String word){
        List<Guide> res;
        if (category.equals("search")){
            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("title").regex(word),
                    Criteria.where("time").regex(word),
                    Criteria.where("maker").regex(word));
            Query query = new Query(criteria);
            res = mongoTemplate.find(query, Guide.class);
        }
        else if(category.equals("new")){
            Query query = new Query();
            query.limit(6);
            res = mongoTemplate.find(query, Guide.class);
        }
        else if(category.equals("year")){
            Query query = new Query();
            query.addCriteria(Criteria.where("time").regex(word));
            res = mongoTemplate.find(query, Guide.class);
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
