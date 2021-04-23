package com.cad.service;
import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.graph.Path;
import com.baidu.hugegraph.structure.graph.Vertex;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.dao.MainDao;
import com.cad.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 首页模块Service
 */
@Service
public class MainService {
    @Autowired
    private MainDao mainDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    // 全局疾病搜索
    public List<Object> overallDisease(String content){
        List<Object> result = new ArrayList<>();
        result.addAll(queryList("disease", content));
        result.addAll(makeListObjects(mainDao.drugNeighbourDisease(content)));
        return result;
    }

    // 全局药品搜索
    public List<Map<String, Object>> overallDrug(String content) {
        List<Map<String, Object>> result = new ArrayList<>();
        result.addAll(medicineQueryList(content));
        List<Map<String, Object>> temp = new ArrayList<>();
        getNameAndLabel(mainDao.diseaseNeighbourDrug(content), temp);
        result.addAll(temp);
        return result;
    }

    // 返回疾病一级目录
    public TreeLayer getDiseaseCatalog(){
        ResultSet resultSet = mainDao.getFirstClass();
        Iterator<Result> results = resultSet.iterator();
        TreeLayer treeLayer = new TreeLayer();
        List<TreeNode> childes = new ArrayList<>();
        results.forEachRemaining(result -> {
            TreeNode node = new TreeNode();
            Object object = result.getObject();
            String label = ((Vertex)object).label();
            String name = ((Vertex)object).property("name").toString();
            node.setNodeName(name);
            node.setNodeLabel(label);
            childes.add(node);
        });
        treeLayer.setChildList(childes);
        return treeLayer;
    }

    // 返回疾病上级目录
    public TreeLayer getDiseaseBack(String disease) {
        TreeLayer treeLayer;
        ResultSet resultSet = mainDao.getFatherNode(disease);
        Iterator<Result> results = resultSet.iterator();
        Result result = results.next();
        Object object = result.getObject();
        String label = ((Vertex)object).label();
        String name = ((Vertex)object).property("name").toString();
        treeLayer = getDiseaseNext(name, label);
        return treeLayer;
    }

    // 返回疾病下级目录
    public TreeLayer getDiseaseNext(String disease, String category) {
        TreeLayer treeLayer = new TreeLayer();
        ResultSet resultSet = mainDao.getChildNode(disease);
        Iterator<Result> results = resultSet.iterator();
        TreeNode tempNode = new TreeNode();
        tempNode.setNodeName(disease);
        tempNode.setNodeLabel(category);
        List<TreeNode> childes = new ArrayList<>();
        results.forEachRemaining(result -> {
            TreeNode node = new TreeNode();
            Object object = result.getObject();
            String label = ((Vertex)object).label();
            String name = ((Vertex)object).property("name").toString();
            node.setNodeName(name);
            node.setNodeLabel(label);
            childes.add(node);
        });
        treeLayer.setNode(tempNode);
        treeLayer.setChildList(childes);
        return treeLayer;
    }

    // 根据所搜索的疾病返回的疾病树结构（自底向上）
    public DiseaseTreeResult getDiseaseTreeResult(String disease) {
        List<String> path = new LinkedList<>();
        String diseaseClass;
        ResultSet resultSet = mainDao.getDiseaseClass(disease);
        if(resultSet.size()!=0){
            Object object = resultSet.get(0).getObject();
            List<Object> elements = ((Path) object).objects();
            elements.forEach(element -> {
                path.add(0,((Vertex)element).property("name").toString());
            });
            diseaseClass=path.get(0);
        }
        else {
            diseaseClass = disease;
            path.add(disease);
        }
//        DiseaseTree diseaseTree = buildDiseaseTree(diseaseClass);
//        mongoTemplate.insert(diseaseTree);
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(diseaseClass));
        List<DiseaseTree> sample = mongoTemplate.find(query, DiseaseTree.class);
        if(sample.size()==0){
            DiseaseTree diseaseTree = buildDiseaseTree(diseaseClass);
            mongoTemplate.insert(diseaseTree);
            sample = mongoTemplate.find(query, DiseaseTree.class);
        }
        DiseaseTree diseaseTree = sample.get(0);
        DiseaseTreeResult diseaseTreeResult = new DiseaseTreeResult();
        diseaseTreeResult.setDiseaseTree(diseaseTree);
        diseaseTreeResult.setPath(path);
        return diseaseTreeResult;
    }

    // 根据此疾病节点构建子树（自顶向下）
    public DiseaseTree buildDiseaseTree(String disease){
        DiseaseTree diseaseTree = new DiseaseTree();
        diseaseTree.setName(disease);
        ResultSet resultSet = mainDao.getChildNode(disease);
        Iterator<Result> results = resultSet.iterator();
        List<DiseaseTree> subTitle = new ArrayList<>();
        List<String> leafTitle = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            String label = ((Vertex)object).label();
            String name = ((Vertex)object).property("name").toString();
            if(label.equals("疾病三级分类")){
                subTitle.add(buildDiseaseTree(name));
            }
            else if(label.equals("疾病")){
                leafTitle.add(name);
            }
        });
        diseaseTree.setSubTitle(subTitle);
        diseaseTree.setLeafTitle(leafTitle);
        return diseaseTree;
    }

    // 返回“药品分类”列表，用于展示在药品总览中
    public List<Object> getMedicineClassList() {
        ResultSet resultSet = mainDao.getMedicineClass();
        return makeListObjects(resultSet);
    }

    // 以药品分类返回该分来下的药品化学名列表
    public ResultSet getChemicalList(String category) {
        return mainDao.getChemicalByClass(category);
    }

    //返回某药品化学名下的药品商品信息
    public ResultSet getMedicineByClassList(String category){
        return mainDao.getMedicineByClass(category);
    }

    // 药品商品名逆向返回药品化学名
    public ResultSet chemicalByMedicine(String content){
        return mainDao.chemicalByMedicine(content);
    }

    // 药品查询返回列表
    public List<Map<String, Object>> medicineQueryList(String content){
        ResultSet medicineSet = mainDao.searchDrugList(content);
//        ResultSet chemicalSet = mainDao.searchChemicalList(content);
//        ResultSet classSet = mainDao.searchClassList(content);
        List<Map<String,Object>> res = new ArrayList<>();
        getNameAndLabel(medicineSet, res);
//        getNameAndLabel(chemicalSet, res);
//        getNameAndLabel(classSet, res);
        return res;
    }

    // 返回查询列表结果的通用接口,单疾病用药查询(disease)和相互作用查询(interaction)
    public List<Object> queryList(String category, String content){
        ResultSet resultSet = null;
        if(category.equals("disease")){
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

    //疾病用药详情
    public DiseaseResult diseaseDetail(String name) {
        DiseaseResult diseaseResult = new DiseaseResult();
        List<DiseaseDetail> indication = new ArrayList<>();
        List<DiseaseDetail> contraindication = new ArrayList<>();
        ResultSet resultSet = mainDao.indication(name);
        Iterator<Result> results = resultSet.iterator();
        results.forEachRemaining(result -> {
            DiseaseDetail diseaseDetail = new DiseaseDetail();
            String type = "适应";
            diseaseDetail.setType(type);
            buildDiseaseDetail(indication, result, diseaseDetail);
        });
        ResultSet resultSet_ = mainDao.contraindication(name);
        Iterator<Result> results_ = resultSet_.iterator();
        results_.forEachRemaining(result -> {
            DiseaseDetail diseaseDetail = new DiseaseDetail();
            String type = "禁忌";
            diseaseDetail.setType(type);
            buildDiseaseDetail(contraindication, result, diseaseDetail);
        });
        diseaseResult.setIndication(indication);
        diseaseResult.setContraindication(contraindication);
        return diseaseResult;
    }

    private void buildDiseaseDetail(List<DiseaseDetail> indication, Result result, DiseaseDetail diseaseDetail) {
        diseaseDetail.setProperty(result.getEdge().properties());
        String drugId = result.getEdge().sourceId().toString(); // 获取目标节点id
        String group = result.getEdge().property("group").toString();
        String drugName = getName(drugId).toString();
        diseaseDetail.setDrugName(drugName);
        ResultSet purposeResult = mainDao.purpose(drugId, group);
        if (purposeResult.size()!=0){
            diseaseDetail.setPurpose(getName(purposeResult.get(0).getVertex().id().toString()).toString());
        }
        ResultSet banResult = mainDao.people(drugId, group);
        List<String> banPeople = new ArrayList<>();
        Iterator<Result> banResults = banResult.iterator();
        banResults.forEachRemaining(sult -> {
            Object object = sult.getObject();
            String tarId = ((Vertex)object).id().toString(); // 获取目标节点id
            String nameObject = getName(tarId).toString();
            banPeople.add(nameObject);
        });
        diseaseDetail.setBanPeople(banPeople);
        indication.add(diseaseDetail);
    }

    //返回查询详情结果的通用接口，包括药品查询(drug)和相互作用查询(interaction)
    public List<Object> queryDetail(String category, String name){
        ResultSet resultSet = null;
        if(category.equals("drug")){
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
                String sourceId = ((Edge)object).sourceId().toString(); // 获取目标节点id
                String sourceName = getName(sourceId).toString();
                String tarId = ((Edge)object).targetId().toString(); // 获取源节点id
                String tarName = getName(tarId).toString();
                String tName;
                String sid;
                String tid;
                if (sourceName.equals(name)){
                    tName = tarName;
                    tid = tarId;
                    sid = sourceId;
                }
                else {
                    tName = sourceName;
                    tid = sourceId;
                    sid = tarId;
                }
                InteractionResult interactionResult = new InteractionResult();
                interactionResult.setSourceName(getName(sid));
                interactionResult.setEdgeResult(object);
                interactionResult.setTargetName(getName(tid));
                someList.add(interactionResult);
            });
            return someList;
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
                String sourceId = ((Edge)object).sourceId().toString(); // 获取目标节点id
                String sourceName = getName(sourceId).toString();
                String tarId = ((Edge)object).targetId().toString(); // 获取目标节点id
                String tarName = getName(tarId).toString();
                String name;
                String sid;
                String tid;
                if(sourceName.equals(contents.get(finalI))){
                    name = tarName;
                    tid = tarId;
                    sid = sourceId;
                }
                else {
                    name = sourceName;
                    tid = sourceId;
                    sid = tarId;
                }
                for(int j = finalI +1; j<contents.size(); j++){
                    if(name.equals(contents.get(j))){
                        InteractionResult interactionResult = new InteractionResult();
                        interactionResult.setSourceName(getName(sid));
                        interactionResult.setEdgeResult(object);
                        interactionResult.setTargetName(getName(tid));
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
