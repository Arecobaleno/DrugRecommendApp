package com.cad.controller;

import com.cad.entity.MultiInteraction;
import com.cad.entity.Query;
import com.cad.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
首页模块controller
 */

@CrossOrigin
@RestController
public class MainController {
    @Autowired
    private MainService mainService;

    // 以List<String>形式返回“药品分类”，用于展示在药品总览中
    @GetMapping("/medicine_class")
    public List<Object> medicineClassList(){
        return mainService.getMedicineClassList();
    }

    // 各药品化学名所包含的药品展示
    @RequestMapping(value = "/medicine_by_chemical", method = RequestMethod.POST)
    public List<Object> medicineByClassList(@RequestBody Query query){
        String category = query.getContent();
        return mainService.getMedicineByClassList(category);
    }

    // 根据药品分类展示该类别下的药品化学名列表
    @PostMapping(value = "/chemical_by_class")
    public List<Object> chemicalByClass(@RequestBody Query query){
        String category = query.getContent();
        return mainService.getChemicalList(category);
    }

    // 药品商品名逆向获取药品化学名
    @RequestMapping(value = "/return_chemical", method = RequestMethod.POST)
    public List<Object> returnChemical(@RequestBody Query query) {
        String content = query.getContent();
        return mainService.chemicalByMedicine(content);
    }


    //药品查询
    @PostMapping("/medicine_query")
    public List<Map<String, Object>> medicineQuery(@RequestBody Query query){
        String content = query.getContent();
        return mainService.medicineQueryList(content);
    }

    // 查询通用接口，包括全局查询(all)、疾病查询(disease)、药品查询或相互作用(drug)
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public List<Object> query(@RequestBody Query query){
        String category = query.getCategory();
        String content = query.getContent();
        return mainService.queryList(category, content);
    }

    // 返回疾病、药品、相互作用的详情
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public List<Object> detail(@RequestBody Query query){
        String category = query.getCategory();
        String name = query.getContent();
        return mainService.queryDetail(category, name);
    }

    // 相互作用精准搜索返回详情
    @PostMapping("/interaction_accurate")
    public List<Object> interactionAccurate(@RequestBody MultiInteraction multiInteraction){
        List<String> content = multiInteraction.getContent();
        return mainService.getInteractionAccurate(content);
    }

    // 相互作用补全候选词
    @PostMapping("/interaction_candidate")
    public List<Object> interactionCandidate(@RequestBody Query query){
        String content = query.getContent();
        return mainService.getInteractionCandidate(content);
    }
}
