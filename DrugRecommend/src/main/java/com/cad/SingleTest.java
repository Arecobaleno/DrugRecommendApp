package com.cad;


import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.service.MainService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// g.V().hasLabel('药品商品名').hasValue("影响血液及造血系统的药物 (Haematopoietic Agents)")
// 搜索某一property的值 values("name")
// 模糊查询g.V().hasLabel("药品商品名").filter(values("name").is(Text.contains("高")))
// 相互作用 g.V().hasLabel('药品商品名').hasValue('开富林 Kai Fu Lin').inE().outV().path()
// 含相互作用的关系 g.V().hasLabel('药品商品名').hasValue('开富林 Kai Fu Lin').inE().filter(label().is(Text.contains("相互作用")))
public class SingleTest {
    public static void main(String[] args){
        HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640","hugegraph");
        GremlinManager gremlin = hugeClient.gremlin();

        ResultSet resultSet = gremlin.gremlin("g.V().hasId(352292334324416512).values(\"name\")").execute();
        Iterator<Result> results = resultSet.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            someList.add(object);
        });
        System.out.println(someList.get(0));
    }
}
