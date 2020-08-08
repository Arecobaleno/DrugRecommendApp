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
        return gremlin.gremlin("g.V().hasLabel('药品分类').values()").execute();
    }

    public ResultSet getMedicineByClass(String category){
        return gremlin.gremlin("g.V().hasLabel('药品商品名').hasValue('"+ category +"')").execute();
    }
}
