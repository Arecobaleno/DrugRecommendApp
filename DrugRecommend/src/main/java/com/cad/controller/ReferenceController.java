package com.cad.controller;

import com.cad.entity.Query;
import com.cad.pojo.Guide;
import com.cad.pojo.Reference;
import com.cad.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/reference")
public class ReferenceController {
    @Autowired
    private ReferenceService referenceService;

    // 返回 搜索 文献列表
    @PostMapping("/reference_search")
    public List<Reference> getReferenceSearch(@RequestBody Query query){
        String word = query.getContent();
        return referenceService.referenceSearchList(word);
    }

    // 返回 最新 文献列表
    @PostMapping("/reference_new")
    public List<Reference> getReferenceNew(){
        return referenceService.referenceNewList();
    }

    // 返回 按年份查询 文献列表
    @PostMapping("/reference_year")
    public List<Reference> getReferenceYear(@RequestBody Query query){
        String category = query.getCategory();
        String word = query.getContent();
        return referenceService.referenceYearList(category, word);
    }

    // 获取文献详情
    @PostMapping(value = "/detail")
    public List<Reference> referenceDetail(@RequestBody Query query){
        String word = query.getContent();
        return referenceService.referenceDetail(word);
    }
}
