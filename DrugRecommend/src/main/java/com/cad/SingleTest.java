package com.cad;


import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.service.MainService;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// g.V().hasLabel('药品商品名').hasValue("影响血液及造血系统的药物 (Haematopoietic Agents)")
// 搜索某一property的值 values("name")
// 模糊查询g.V().hasLabel("药品商品名").filter(values("name").is(Text.contains("高")))
// 相互作用 g.V().hasLabel('药品商品名').hasValue('开富林 Kai Fu Lin').inE().outV().path()
// 含相互作用的关系 g.V().hasLabel('药品商品名').hasValue('开富林 Kai Fu Lin').inE().filter(label().is(Text.contains("相互作用")))
public class SingleTest {
    public static void main(String[] args){
        //MongoClient mongoClient = MongoClients.create("mongodb://114.67.200.39:27817");
//        MongoClient mongoClient = MongoClients.create(
//                MongoClientSettings.builder()
//                        .applyToClusterSettings(builder ->
//                                builder.hosts(Arrays.asList(new ServerAddress("114.67.200.39", 27817))))
//                        .build());
//        MongoDatabase database = mongoClient.getDatabase("Hypertension");
//        MongoCollection<Document> collection = database.getCollection("Paper");
//        System.out.println(collection.countDocuments());

    }
}

