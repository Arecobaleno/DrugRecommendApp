package com.cad.controller;

import com.cad.entity.Query;
import com.cad.pojo.Guide;
import com.cad.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/guide")
public class GuideController {
    @Autowired
    private GuideService guideService;

//    @PostMapping("/detail")
//    public List<> guideDetail(@RequestBody Query query){
//        String content = query.getContent();
//        return
//    }
//
//    @GetMapping("/maker")
//    public List<> getMaker(){
//
//    }

    @PostMapping(value = "/get")
    public List<Guide> getGuide(@RequestBody Query query){
        String category = query.getCategory();
        String word = query.getContent();
        return guideService.guideList(category, word);
    }
}
