package com.kuang;

import com.alibaba.fastjson.JSON;
import com.kuang.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EsApiApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    // es的索引
    private final String INDEX = "test_2020";

    // 测试创建索引
    @Test
    void testCreateIndex() throws IOException {
        // 1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(INDEX);
        // 2.执行创建请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(response.index());
    }

    // 测试获取索引
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDEX);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 测试删除索引
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(INDEX);
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    // 测试添加文档
    @Test
    void testAddDocument() throws IOException {
        User user = new User("李四", 22);
        // 创建请求
        IndexRequest request = new IndexRequest(INDEX);

        // 规则 put cdd_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(3));

        // 将我们的数据放入请求
        request.source(JSON.toJSONString(user), XContentType.JSON);

        // 客户端发送请求,获取响应的结果
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }


    // 获取文档,判断是否存在 get /index/doc/1
    @Test
    void testIsExists() throws IOException {
        GetRequest getRequest = new GetRequest(INDEX, "1");
        // 不获取返回的_source的上下文
        getRequest.fetchSourceContext(new FetchSourceContext(true));
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }


    // 获取文档的信息
    @Test
    void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest(INDEX, "2");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());  // 打印文档的内容
        System.out.println(getResponse);
    }


    // 更新文档的信息
    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(INDEX, "1");
        updateRequest.timeout("1s");
        User user = new User("张三", 18);
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);

        UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update);
    }


    // 删除文档的信息
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, "2");
        deleteRequest.timeout("1s");

        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
        System.out.println(delete.status());
    }

    // 批量操作数据
    @Test
    void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        List<User> list = new LinkedList<>();
        list.add(new User("a", 120));
        list.add(new User("b", 12));
//        list.add(new User("c", 13));
//        list.add(new User("d", 54));
//        list.add(new User("e", 56));
//        list.add(new User("f", 76));
//        list.add(new User("g", 123));

        list.forEach(info -> {
            bulkRequest.add(
                    new IndexRequest(INDEX).source(JSON.toJSONString(info), XContentType.JSON)
            );
        });

        BulkResponse bulkItemResponses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkItemResponses);
        System.out.println(bulkItemResponses.status());
    }


    // 查询
    // SearchRequest 搜索请求
    // SearchSourceBuilder 条件构造 
    // TermQueryBuilder 精准查询
    // MatchAllQueryBuilder 查询全部
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "a");// 精确查询
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery(); //匹配所有

        searchSourceBuilder.query(matchAllQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(30, TimeUnit.SECONDS));
//        searchSourceBuilder.from(1).size(10); //分页

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(response.getHits());
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }


    }


}
