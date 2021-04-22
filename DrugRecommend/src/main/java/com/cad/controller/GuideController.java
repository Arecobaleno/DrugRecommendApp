package com.cad.controller;

import com.cad.entity.MakerResponse;
import com.cad.entity.Query;
import com.cad.pojo.Guide;
import com.cad.pojo.Maker;
import com.cad.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/guide")
public class GuideController {
    @Autowired
    private GuideService guideService;

    // 指南下载
    @RequestMapping("/download")
    public void guideDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("filename");
        guideService.guideDownload(title, response);
    }

    // 指南详情
    @PostMapping("/detail")
    public List<Guide> guideDetail(@RequestBody Query query){
        String title = query.getContent();
        return guideService.guideDetail(title);
    }

    // 制定者列表
    @GetMapping("/maker")
    public List<Maker> getMaker(){
        return guideService.getMakerList();
    }

    // 制定者搜索
    @PostMapping("/maker_search")
    public List<MakerResponse> makerSearch(@RequestBody Query query) {
        String content = query.getContent();
        return guideService.getMakerSearch(content);
    }

    // 返回 搜索 指南列表
    @PostMapping("/guide_search")
    public List<Guide> getGuideSearch(@RequestBody Query query){
        String word = query.getContent();
        return guideService.guideSearchList(word);
    }

    // 返回 最新 指南列表
    @PostMapping("/guide_new")
    public List<Guide> getGuideNew(){
        return guideService.guideNewList();
    }

    // 返回 按制定者查询 指南列表
    @PostMapping("/guide_maker")
    public List<Guide> getGuideMaker(@RequestBody Query query){
        String word = query.getContent();
        return guideService.guideMakerList(word);
    }

    // 返回 按年份查询 指南列表
    @PostMapping("/guide_year")
    public List<Guide> getGuideYear(@RequestBody Query query){
        String category = query.getCategory();
        String word = query.getContent();
        return guideService.guideYearList(category, word);
    }
}
