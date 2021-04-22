package com.cad.controller;

import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.entity.*;
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

    // 全局疾病搜索条目，实现药品附近一跳功能
    @PostMapping(value = "/overall_disease")
    public List<Object> overallDisease(@RequestBody Query query){
        String content = query.getContent();
        return mainService.overallDisease(content);
    }

    // 全局药品搜索，实现疾病附近一跳
    @PostMapping(value = "/overall_drug")
    public List<Map<String, Object>> overallDrug(@RequestBody Query query){
        String content = query.getContent();
        return mainService.overallDrug(content);
    }

    // 以List<String>形式返回“药品分类”，用于展示在药品总览中
    @GetMapping("/medicine_class")
    public List<Object> medicineClassList(){
        return mainService.getMedicineClassList();
    }

    // 各药品化学名所包含的药品展示
    @PostMapping(value = "/medicine_by_chemical")
    public ResultSet medicineByClassList(@RequestBody Query query){
        String category = query.getContent();
        return mainService.getMedicineByClassList(category);
    }

    // 根据药品分类展示该类别下的药品化学名列表
    @PostMapping(value = "/chemical_by_class")
    public ResultSet chemicalByClass(@RequestBody Query query){
        String category = query.getContent();
        return mainService.getChemicalList(category);
    }

    // 药品商品名逆向获取药品化学名
    @PostMapping(value = "/return_chemical")
    public ResultSet returnChemical(@RequestBody Query query) {
        String content = query.getContent();
        return mainService.chemicalByMedicine(content);
    }

    // 根据疾病获取疾病树形信息
    @PostMapping(value = "/disease_tree")
    public DiseaseTreeResult getDiseaseTree(@RequestBody Query query) {
        String content = query.getContent();
        return mainService.getDiseaseTreeResult(content);
    }

    // 疾病树 返回疾病上级目录
//    @PostMapping(value = "/disease_back")
//    public TreeNodeList getDiseaseBack(@RequestBody Query query) {
//        String content = query.getContent();
//        return mainService.
//    }

    // 疾病树 返回疾病下级目录
    @PostMapping(value = "/disease_next")
    public TreeNodeList getDiseaseNext(@RequestBody Query query) {
        String content = query.getContent();
        return mainService.getDiseaseNext(content);
    }

    //药品查询
    @PostMapping("/medicine_query")
    public List<Map<String, Object>> medicineQuery(@RequestBody Query query){
        String content = query.getContent();
        return mainService.medicineQueryList(content);
    }

    // 疾病查询(disease)和相互作用查询(disease)
    @PostMapping(value = "/query")
    public List<Object> query(@RequestBody Query query){
        String category = query.getCategory();
        String content = query.getContent();
        return mainService.queryList(category, content);
    }

    // 疾病详情
    @PostMapping(value = "/disease_detail")
    public DiseaseResult diseaseDetail(@RequestBody Query query){
        String name = query.getContent();
        return mainService.diseaseDetail(name);
    }

    // 返回药品、相互作用的详情
    @PostMapping(value = "/detail")
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
