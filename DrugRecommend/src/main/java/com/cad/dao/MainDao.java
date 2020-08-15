package com.cad.dao;

import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import org.springframework.stereotype.Repository;

@Repository
public class MainDao {
    HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640","hugegraph");
    GremlinManager gremlin = hugeClient.gremlin();

    // 搜索所有的“药品分类”名称
    public ResultSet getMedicineClass(){
        return gremlin.gremlin("g.V().hasLabel('药品分类').values().properties('name')").execute();
    }

    // 根据药品类名返回药品
    public ResultSet getMedicineByClass(String category){
        return gremlin.gremlin("g.V().hasLabel('药品商品名').hasValue('"+ category +"').properties('name')").execute();
    }

    // 搜索药物，返回药物名称list
    public ResultSet searchDrugList(String content){
        return gremlin.gremlin("g.V().hasLabel(\"药品商品名\")" +
                ".filter(values(\"name\").is(Text.contains('"+content+"'))).properties('name')").execute();
    }

    // 搜索疾病，返回疾病list
    public ResultSet searchDiseaseList(String content){
        return gremlin.gremlin("g.V().hasLabel('疾病').filter(values('name')" +
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

    // 返回疾病详情(还需修改，需要添加用药推荐的功能
    public ResultSet searchDisease(String name){
        return gremlin.gremlin("g.V().hasLabel('疾病').hasValue('"+ name +"')").execute();
    }

    // 返回name商品具有相互作用的边
    public ResultSet searchInteraction(String name){
        return gremlin.gremlin("g.V().hasLabel('药品化学名').hasValue('"
                + name +"').inE().filter(label().is(Text.contains('相互作用')))").execute();
    }

    public ResultSet getNameById(String id){
        return gremlin.gremlin("g.V().hasId("+ id +").values('name')").execute();
    }
}
