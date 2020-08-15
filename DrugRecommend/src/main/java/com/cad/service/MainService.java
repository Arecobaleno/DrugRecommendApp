package com.cad.service;


import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.dao.MainDao;
import com.cad.entity.InteractionResult;
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
    public List<Object> getMedicineClassList() {
        ResultSet resultSet = mainDao.getMedicineClass();
        return makeListObjects(resultSet);
    }

    //返回某药品分类下的药品信息
    public List<Object> getMedicineByClassList(String category){
        ResultSet resultSet = mainDao.getMedicineByClass(category);
        return makeListObjects(resultSet);
    }

    // 返回查询列表结果的通用接口，包括全局查询(all)、单疾病用药查询(disease)、药品查询(drug)和相互作用查询(interaction)
    public List<Object> queryList(String category, String content){
        ResultSet resultSet = null;
        if (category.equals("all")){
            /*
            全局查询
             */
        }
        else if(category.equals("disease")){
            /*
            单疾病用药查询
             */
            resultSet = mainDao.searchDiseaseList(content);
        }
        else if(category.equals("drug")){
            /*
            药品查询
             */
            resultSet = mainDao.searchDrugList(content);
        }
        else if(category.equals("interaction")){
            /*
            相互作用查询
             */
            resultSet = mainDao.searchInteractionList(content);
        }
        return makeListObjects(resultSet);
    }

    //返回查询详情结果的通用接口，包括全局查询(all)、单疾病用药查询(disease)、药品查询(drug)和相互作用查询(interaction)
    public List<Object> queryDetail(String category, String name){
        ResultSet resultSet = null;
        if(category.equals("disease")){
            /*
            单疾病用药推荐详情页
             */
            resultSet = mainDao.searchDisease(name);
        }
        else if(category.equals("drug")){
            /*
            药品详情页
             */
            resultSet = mainDao.searchDrug(name);
        }
        else if(category.equals("interaction")){
            /*
            相互作用详情页
             */
            resultSet = mainDao.searchInteraction(name);
            Iterator<Result> results = resultSet.iterator();
            List<Object> someList = new ArrayList<>();
            results.forEachRemaining(result -> {
                Object object = result.getObject();
                String tarId = ((Edge)object).sourceId().toString(); // 获取目标节点id
                Object nameObject = getName(tarId);
                InteractionResult interactionResult = new InteractionResult();
                interactionResult.setEdgeResult(object);
                interactionResult.setTargetName(nameObject);
                someList.add(interactionResult);
            });
            return someList;
        }
        return makeListObjects(resultSet);
    }

    //构建List<Object>的返回
    private List<Object> makeListObjects(ResultSet resultSet) {
        Iterator<Result> results = resultSet.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            someList.add(object);
        });
        return someList;
    }

    private Object getName(String id){
        ResultSet nameResult = mainDao.getNameById(id); // 通过id获取目标name
        Iterator<Result> results = nameResult.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            someList.add(object);
        });
        return someList.get(0);
    }
}
