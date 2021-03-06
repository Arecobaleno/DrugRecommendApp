package com.cad.dao;

import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import org.springframework.stereotype.Repository;

/**
 * 首页模块Dao
 */

@Repository
public class MainDao {
    HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640","hugegraph");
    GremlinManager gremlin = hugeClient.gremlin();

    // 搜索药品附近一跳的相关疾病
    public ResultSet drugNeighbourDisease(String content) {
        return gremlin.gremlin("g.V().and(filter(values('name').is(Text.contains('" + content + "')))," +
                "filter(label().is(Text.contains('药品')))).both().filter(label().is(Text.contains('疾病')))").execute();
    }

    // 搜索疾病附近一跳的相关药品
    public ResultSet diseaseNeighbourDrug(String content) {
        return gremlin.gremlin("g.V().and(filter(values('name').is(Text.contains('" + content + "')))," +
                "filter(label().is(Text.contains('疾病')))).both().filter(label().is(Text.contains('药品')))").execute();
    }

    // 根据名称寻找此节点
    public ResultSet getDiseaseNode(String name) {
        return gremlin.gremlin("g.V().hasValue('" + name + "').filter(label().is(Text.contains('疾病')))").execute();
    }

    // 根据疾病向上搜索其所属疾病大类
    public ResultSet getDiseaseClass(String name) {
        return gremlin.gremlin("g.V().hasValue('"+ name +"')" +
                ".repeat(both().filter(label().is(Text.contains('疾病'))).simplePath())" +
                ".until(hasLabel('疾病二级分类').or().loops().is(gte(6))).hasLabel('疾病二级分类').path()").execute();
    }

    // 返回 疾病一级分类 的疾病列表
    public ResultSet getFirstClass(){
        return gremlin.gremlin("g.V().hasLabel('疾病一级分类')").execute();
    }

    // 根据名称寻找疾病树的父节点
    public ResultSet getFatherNode(String name) {
        return gremlin.gremlin("g.V().hasValue('" + name + "').in().filter(label().is(Text.contains('级分类')))").execute();
    }


    // 根据名称寻找疾病树的子节点 ************
    public ResultSet getChildNode(String name) {
        return gremlin.gremlin("g.V().hasValue('" + name + "').out().filter(label().is(Text.contains('疾病')))").execute();
    }

    // 搜索所有的“药品分类”名称
    public ResultSet getMedicineClass(){
        return gremlin.gremlin("g.V().hasLabel('药品分类').properties('name')").execute();
    }

    // 根据药品化学名返回药品商品名
    public ResultSet getMedicineByClass(String category){
        return gremlin.gremlin("g.V().hasLabel(\"药品化学名\").hasValue('" + category + "')" +
                ".bothE().filter(label().is(Text.contains('成份'))).inV().values('name')").execute();
    }

    // 根据药品商品名逆向返回药品化学名
    public ResultSet chemicalByMedicine(String category){
        return gremlin.gremlin("g.V().hasLabel(\"药品商品名\").hasValue('" + category + "')" +
                ".bothE().filter(label().is(Text.contains('成份'))).outV().values('name')").execute();
    }

    // 根据药品类名返回药品化学名
    public ResultSet getChemicalByClass(String category) {
        return gremlin.gremlin("g.V().hasLabel(\"药品分类\").hasValue('" + category + "')" +
                ".bothE().filter(label().is(Text.contains('化学名子类'))).outV().values('name')").execute();
    }

    // 搜索药物商品名，返回药物名称list
    public ResultSet searchDrugList(String content){
        return gremlin.gremlin("g.V().and(filter(values('name').is(Text.contains('" + content + "')))," +
                "filter(label().is(Text.contains('药品'))))").execute();
    }

    // 搜索疾病，返回疾病list *************************************
    public ResultSet searchDiseaseList(String content){
        return gremlin.gremlin("g.V().hasLabel('疾病','疾病类型').filter(values('name')" +
                ".is(Text.contains('"+content+"'))).properties('name')").execute();
    }

    // 搜索相互作用，返回药品化学名list
    public ResultSet searchInteractionList(String content){
        return gremlin.gremlin("g.V().hasLabel('药品化学名').filter(values('name')" +
                ".is(Text.contains('"+content+"'))).properties('name')").execute();
    }

    // 返回药品的详情
    public ResultSet searchDrug(String name){
        return gremlin.gremlin("g.V().hasLabel('药品商品名').hasValue('"+ name +"')").execute();
    }

    // 返回疾病适应的药品
    public ResultSet indication(String name){
        return gremlin.gremlin("g.V().hasValue('"
                + name +"').bothE().filter(label().is(Text.contains('适应证')))").execute();
    }

    // 返回疾病禁忌的药品
    public ResultSet contraindication(String name){
        return gremlin.gremlin("g.V().hasValue('"
                + name +"').bothE().filter(label().is(Text.contains('禁忌证')))").execute();
    }

    // 根据id返回药品的用药目的
    public ResultSet purpose(String id, String group){
        return gremlin.gremlin("g.V(" + id + ").outE().filter(label().is(Text.contains('用药目的')))" +
                ".has('group'," + group + ").inV() ").execute();
    }

    // 根据id返回药品的禁忌人群
    public ResultSet people(String id, String group){
        return gremlin.gremlin("g.V(" + id + ").outE().filter(label().is(Text.contains('禁忌人群')))" +
                ".or(has('group'," + group + "), has('group',0)).inV()").execute();
    }

    // 返回name具有相互作用的边
    public ResultSet searchInteraction(String name){
        return gremlin.gremlin("g.V().hasLabel('药品化学名').hasValue('"
                + name +"').bothE().filter(label().is(Text.contains('相互作用')))").execute();
    }

    // 相互作用候选词
    public ResultSet interactionCandidateList(String content){
        return gremlin.gremlin("g.V().hasLabel('药品化学名').filter(values('name')" +
                ".is(Text.contains('"+content+"'))).values('name').limit(6)").execute();
    }

    public ResultSet getNameById(String id){
        return gremlin.gremlin("g.V().hasId("+ id +").values('name')").execute();
    }
}
