package com.cad.service;
import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.graph.Vertex;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.dao.MainDao;
import com.cad.entity.InteractionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MainService {
    @Autowired
    private MainDao mainDao;

    // 返回“药品分类”列表，用于展示在药品总览中
    public List<Object> getMedicineClassList() {
        ResultSet resultSet = mainDao.getMedicineClass();
        return makeListObjects(resultSet);
    }

    // 以药品分类返回该分来下的药品化学名列表
    public List<Object> getChemicalList(String category) {
        ResultSet resultSet = mainDao.getChemicalByClass(category);
        return getNameList(resultSet);
    }

    //返回某药品化学名下的药品商品信息
    public List<Object> getMedicineByClassList(String category){
        ResultSet resultSet = mainDao.getMedicineByClass(category);
        return getNameList(resultSet);
    }

    private List<Object> getNameList(ResultSet resultSet) {
        List<Object> res = new ArrayList<>();
        Iterator<Result> results = resultSet.iterator();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            String tarId = ((Vertex)object).id().toString(); // 获取目标节点id
            Object nameObject = getName(tarId);
            res.add(nameObject);
        });
        return res;
    }

    // 药品查询返回列表
    public List<Map<String, Object>> medicineQueryList(String content){
        ResultSet medicineSet = mainDao.searchDrugList(content);
        ResultSet chemicalSet = mainDao.searchChemicalList(content);
        ResultSet classSet = mainDao.searchClassList(content);
        List<Map<String,Object>> res = new ArrayList<>();
        getNameAndLabel(medicineSet, res);
        getNameAndLabel(chemicalSet, res);
        getNameAndLabel(classSet, res);
        return res;
    }

    // 返回查询列表结果的通用接口，包括全局查询(all)、单疾病用药查询(disease)和相互作用查询(interaction)
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
        else if(category.equals("interaction")){
            /*
            相互作用查询
             */
            resultSet = mainDao.searchInteractionList(content);
        }
        return makeListObjects(resultSet);
    }

    private void getNameAndLabel(ResultSet medicineSet, List<Map<String, Object>> res) {
        Iterator<Result> medicineResults = medicineSet.iterator();
        medicineResults.forEachRemaining(result -> {
            Object object = result.getObject();
            Object name = ((Vertex)object).property("name"); // 获取名字
            Object label = ((Vertex)object).label(); // 获取label
            Map<String,Object> medicineTemp = new HashMap<>();
            medicineTemp.put("name",name);
            medicineTemp.put("label",label);
            res.add(medicineTemp);
        });
    }

    //返回查询详情结果的通用接口，包括全局查询(all)、单疾病用药查询(disease)、药品查询(drug)和相互作用查询(interaction)
    public List<Object> queryDetail(String category, String name){
        ResultSet resultSet = null;
        if(category.equals("disease")){
            /*
            单疾病用药推荐详情页
             */
            resultSet = mainDao.searchDisease(name);
            return getObjectAndName(resultSet);
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
            return getObjectAndName(resultSet);
        }
        return makeListObjects(resultSet);
    }

    // 相互作用精准搜索
    public List<Object> getInteractionAccurate(List<String> contents){
        List<Object> res = new ArrayList<>();
        for(int i=0;i<contents.size();i++){
            ResultSet resultSet = mainDao.searchInteraction(contents.get(i));
            Iterator<Result> results = resultSet.iterator();
            int finalI = i;
            results.forEachRemaining(result -> {
                Object object = result.getObject();
                String tarId = ((Edge)object).sourceId().toString(); // 获取目标节点id
                String name = getName(tarId).toString();
                for(int j = finalI +1; j<contents.size(); j++){
                    if(name.equals(contents.get(j))){
                        InteractionResult interactionResult = new InteractionResult();
                        interactionResult.setEdgeResult(object);
                        interactionResult.setTargetName(getName(tarId));
                        res.add(interactionResult);
                    }
                }
            });
        }
        return res;
    }

    // 相互作用补全候选词
    public List<Object> getInteractionCandidate(String content){
        ResultSet resultSet = mainDao.interactionCandidateList(content);
        return makeListObjects(resultSet);
    }

    private List<Object> getObjectAndName(ResultSet resultSet) {
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
