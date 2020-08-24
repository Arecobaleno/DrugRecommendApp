package com.cad.controller;

import com.cad.entity.Query;
import com.cad.pojo.Guide;
import com.cad.service.GuideService;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/guide")
public class GuideController {
    @Autowired
    private GuideService guideService;

    @RequestMapping("/download")
    public void guideDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("filename");
        // System.out.println(title);
        guideService.guideDownload(title, response);
    }

    @PostMapping("/detail")
    public List<Guide> guideDetail(@RequestBody Query query){
        String title = query.getContent();
        return guideService.guideDetail(title);
    }

//    @GetMapping("/maker")
//    public List<> getMaker(){
//
//    }

    @PostMapping("/get")
    public List<Guide> getGuide(@RequestBody Query query){
        String category = query.getCategory();
        String word = query.getContent();
        return guideService.guideList(category, word);
    }
}
