package com.cad.service;

import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.dao.MainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MainService {
    @Autowired
    private MainDao mainDao;

    // 以List<String>形式返回“药品分类”，用于展示在药品总览中
    public List<String> getMedicineClassList() {
        ResultSet resultSet = mainDao.getMedicineClass();
        Iterator<Result> results = resultSet.iterator();
        List<String> classList = new ArrayList<>();
        results.forEachRemaining(result -> {
            String object = result.getObject().toString();
            System.out.println(object);
            classList.add(object);
        });
        return classList;
    }

    //返回某药品分类下的药品信息
    public List<Object> getMedicineByClassList(String category){
        ResultSet resultSet = mainDao.getMedicineByClass(category);
        Iterator<Result> results = resultSet.iterator();
        List<Object> medicineList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            System.out.println(object);
            medicineList.add(object);
        });
        return medicineList;
    }
}
