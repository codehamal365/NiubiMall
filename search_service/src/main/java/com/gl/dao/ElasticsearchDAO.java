package com.gl.dao;

import com.alibaba.fastjson.JSON;
import com.gl.entity.ElasticsearchEntity;
import com.gl.entity.PageEntity;
import com.gl.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ElasticsearchDAO {
    @Autowired
    private RestHighLevelClient client;

    /**
     * 判断索引存在
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean existIndex(String indexName) throws IOException {
        //创建索引查询的请求
        GetIndexRequest request = new GetIndexRequest(indexName);
        //判断索引是否存在
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        log.info("{}是否存在{}",indexName,exists);
        return exists;
    }

    /**
     * 删除索引
     * @param indexName
     * @throws IOException
     */
    public void deleteIndex(String indexName) throws IOException {
        //删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        //发送删除请求
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        log.info("{}删除成功",indexName);
    }

    /**
     * 创建索引
     * @param indexName
     * @throws IOException
     */
    public void createIndex(String indexName) throws IOException {
        //创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        //发送创建索引请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        log.info("{}创建成功");
    }

    /**
     * 批量插入
     * @param indexName
     * @param list
     * @throws IOException
     */
    public void insertBatch(String indexName, List<ElasticsearchEntity> list) throws IOException {
        //创建批量操作的请求
        BulkRequest request = new BulkRequest(indexName);
        //请求加入每个插入数据
        list.forEach(entity -> {
            //每个索引请求，设置id和数据
            request.add(new IndexRequest().id(entity.getId()).source(JSON.toJSONString(entity.getData()),
                    XContentType.JSON));
        });
        //执行批量操作
        client.bulk(request,RequestOptions.DEFAULT);
        log.info("批量插入完成");
    }

    /**
     * 添加或更新数据
     */
    public void saveOrUpdate(String indexName,ElasticsearchEntity entity) throws IOException {
        IndexRequest request = new IndexRequest(indexName);
        request.id(entity.getId());
        request.source(JSON.toJSONString(entity.getData()), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        log.info("{}添加或更新数据成功 {}",indexName, JSON.toJSONString(response));
    }

    /**
     * 通过id批量删除
     */
    public <T> void deleteBatch(String indexName, Collection<T> idList) throws IOException {
        BulkRequest request = new BulkRequest();
        idList.forEach(item -> request.add(new DeleteRequest(indexName, item.toString())));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        log.info("{}批量删除数据成功 {}",indexName, JSON.toJSONString(response));
    }

    /**
     * 通过条件删除对象
     */
    public void deleteByQuery(String indexName, QueryBuilder builder) throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
        request.setQuery(builder);
        BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
        log.info("{}批查询删除数据成功 {}",indexName, JSON.toJSONString(response));
    }

    /**
     * 基本搜索功能
     */
    public <T> List<T> search(String indexName, SearchSourceBuilder builder,Class<T> clazz) throws IOException {
        // 创建搜索请求
        SearchRequest request = new SearchRequest(indexName);
        // 指定搜索数据源
        request.source(builder);
        // 指定搜索获得相应
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        List<T> list = new ArrayList<>();
        // 获得搜索数据，将数据加到集合
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            // 将json字符串转换成对象
            T t = JSON.parseObject(hit.getSourceAsString(), clazz);
            list.add(t);
        }
        return list;
    }

    /**
     * 多条件过滤 排序 分页查询
     */
    public <T> PageEntity<T> searchPage(String indexName, Map<String,String> map,
                                        Map<String,String> sort,int from,int size,Class<T> clazz) throws IOException {
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 执行布尔查询，通过多个条件进行过滤
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (map != null && map.size() > 0){
            for (String key : map.keySet()) {
                String value = map.get(key);
                if (StringUtils.isNotEmpty(value)){
                    boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery(key,value));
                }
            }
        }
        if (boolQueryBuilder.filter().size() > 0){
            builder.query(boolQueryBuilder);
        }
        //执行排序字段和方式
        if(sort != null && sort.size() > 0){
            String field = sort.get("field");
            String type = sort.get("type");
            builder.sort(new FieldSortBuilder(field).order(SortOrder.fromString(type)));
        }
        //指定分页位置和长度
        builder.from(from);
        builder.size(size);
        request.source(builder);
        //处理查询结果
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        List<T> res = new ArrayList<>();
        for (SearchHit hit : hits) {
            res.add(JSONUtil.parseObject(hit.getSourceAsString(), clazz));
        }
        PageEntity<T> entity = new PageEntity<>();
        entity.setData(res);
        entity.setTotal((int) response.getHits().getTotalHits().value);
        entity.setPageSize(size);
        return entity;
    }
}


