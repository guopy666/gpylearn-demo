package com.spring.springfamily.es;

import com.alibaba.fastjson.JSON;
import com.spring.springfamily.bean.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ESDemo
 * @Description
 * @Author guopy
 * @Date 2021/9/27 16:24
 */
@SpringBootTest
public class ESDemo {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 创建索引
    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("jd_goods");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("执行创建请求===>" + createIndexResponse);
    }

    // 获取索引
    @Test
    public void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("jd_goods");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("测试获取索引===>" + exists);
    }

    // 删除索引
    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("jd_goods");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println("是否删除成功===>" + delete);
    }

    @Test
    // 添加文档
    public void testAddDoc() throws IOException {
        // 创建对象
        User zhangsan = new User("zhangsan", 20);
        // 创建请求
        IndexRequest request = new IndexRequest("cc_index");

        // 规则
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        // 将数据放到json
        request.source(JSON.toJSONString(zhangsan), XContentType.JSON);

        // 客户端发送请求，获取响应
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());
        System.out.println(response.status());// 对应命令返回的状态

    }

    // 获取文档
    @Test
    public void testIsExists() throws IOException {
        GetRequest request = new GetRequest("cc_index", "1");

        // 不获取返回的 _source 上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);

        System.out.println(exists);
    }

    // 更新文档
    @Test
    public void testUpdateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest("cc_index", "1");
        request.timeout("1s");

        User lisi = new User("lisi", 12);
        request.doc(JSON.toJSONString(lisi), XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    // 删除文档
    @Test
    public void testDeleteDoc() throws IOException{
        DeleteRequest request = new DeleteRequest("cc_index", "1");
        request.timeout("2s");

        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    // 测试批量添加
    @Test
    public void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("wangwu", 23));
        users.add(new User("zhouqi", 23));
        users.add(new User("zhaoliu", 23));
        users.add(new User("sudongpo", 23));

        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(new IndexRequest("dd_index")
            .id("" +  (i+1))
                    .source(JSON.toJSONString(users.get(i)), XContentType.JSON));
        }
        BulkResponse responses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(responses.hasFailures()); // 是否失败，false 代表成功
    }

    // 查询
    @Test
    public void testSearch() throws IOException{
        SearchRequest searchRequest = new SearchRequest("dd_index");

        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 高亮
        sourceBuilder.highlighter();

        // 查询条件
        // QueryBuilders.termQuery(); // 精确查找
        // QueryBuilders.matchAllQuery();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "wangwu");

        MatchAllQueryBuilder allQueryBuilder = QueryBuilders.matchAllQuery();

        System.out.println("allQueryBuilder==>" + allQueryBuilder);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 放入请求
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("====================");
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
    }

}
