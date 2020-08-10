package com.cad.controller;

import com.cad.dao.Query;
import com.cad.entity.MedicineClass;
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
    public List<String> medicineClassList(){
        return mainService.getMedicineClassList();
    }

    // 各药品分类所包含的药品展示
    @RequestMapping(value = "/medicine_by_class", method = RequestMethod.POST)
    public List<Object> medicineByClassList(@RequestBody MedicineClass category){
        String category_temp = category.getCategory();
        return mainService.getMedicineByClassList(category_temp);
    }

    // 查询通用接口，包括全局查询(all)、疾病查询(disease)、药品查询(drug)、相互作用查询(interaction)
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public List<Object> query(@RequestBody Query query){
        String category = query.getCategory();
        String content = query.getContent();
        return mainService.queryResult(category, content);
    }
}
