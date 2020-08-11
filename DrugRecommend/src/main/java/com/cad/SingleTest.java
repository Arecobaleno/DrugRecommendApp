package com.cad;

import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;

import java.util.Iterator;

// g.V().hasLabel('药品商品名').hasValue("影响血液及造血系统的药物 (Haematopoietic Agents)")
// 搜索某一property的值 values("name")
// 模糊查询g.V().hasLabel("药品商品名").filter(values("name").is(Text.contains("高")))
public class SingleTest {
    public static void main(String[] args){
        HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640","hugegraph");
        GremlinManager gremlin = hugeClient.gremlin();
        ResultSet resultSet = gremlin.gremlin("g.V().hasLabel('药品分类').values()").execute();
        Iterator<Result> results = resultSet.iterator();
        results.forEachRemaining(result -> {
           System.out.println(result.getObject().getClass());
           String object = result.getObject().toString();
           System.out.println(object);
        });
    }
}
