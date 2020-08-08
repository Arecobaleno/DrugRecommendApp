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

    public List<String> getMedicineClassList() {
        ResultSet resultSet = mainDao.getMedicineClass();
        Iterator<Result> results = resultSet.iterator();
        List<String> medicineList = new ArrayList<>();
        results.forEachRemaining(result -> {
            String object = result.getObject().toString();
            System.out.println(object);
            medicineList.add(object);
        });
        return medicineList;
    }
}
