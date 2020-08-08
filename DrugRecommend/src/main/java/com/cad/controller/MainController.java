package com.cad.controller;

import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.cad.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
public class MainController {
    @Autowired
    private MainService mainService;

    @RequestMapping("/medicine_class")
    public List<String> medicineClassList(){
        return mainService.getMedicineClassList();
    }

}
