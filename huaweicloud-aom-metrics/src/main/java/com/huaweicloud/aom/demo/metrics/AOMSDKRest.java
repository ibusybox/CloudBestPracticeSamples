package com.huaweicloud.aom.demo.metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.apigateway.sdk.utils.Client;
import com.cloud.apigateway.sdk.utils.Request;

public class AOMSDKRest
{

    public static void getData(String projectId, String ak, String sk, String aomEndpoint) throws Exception
    {
        // period 枚举值，取值范围： 60，1分钟粒度 300，5分钟粒度 900，15分钟粒度 3600，1小时粒度
        String period = "60";
        // 指标命名空间,根据上报时填入的值来确定。
        String namespace = "CUSTOMMETRICS.TEST";
        String metricName = "cpuUsage";
        // dimensions维度列表，kv数组
        JSONArray dimensions = new JSONArray();
        JSONObject dimension1 = new JSONObject();
        dimension1.put("name", "containerName");
        dimension1.put("value", "api-gateway-image");
        dimensions.add(dimension1);
        // statistics 统计方式maximum：最大值 minimum：最小值 sum：总计 average：平均 值 sampleCount：数据样本
        String statistics = "maximum";
        // 查询时间段，如最近五分钟可以表示为-1.-1.5，固定的时间范围（2017-08-01 08:00:00到2017-08-02 08:00:00）可以表示为1501545600.1501632000.1440。
        String timerange = "-1.-1.5";
        // 对应url
        String url = aomEndpoint + "/v1/" + projectId + "/ams/metricdata";
       
        // body
        JSONObject bodyParams = new JSONObject();
        bodyParams.put("period", Integer.parseInt(period));
        JSONArray metric = new JSONArray();
        JSONObject metricParam = new JSONObject();
		// namespace为指标分类，是一组指标的集合。
        metricParam.put("namespace", namespace);
		//metricName是对应的指标名称
        metricParam.put("metricName", metricName);
		// 资源信息，定位唯一一个资源信息。
        metricParam.put("dimensions", dimensions);
        metric.add(metricParam);
        bodyParams.put("metrics", metric);
        ArrayList<String> stat = new ArrayList<String>();
        stat.add(statistics);
        bodyParams.put("statistics", stat);
        bodyParams.put("timerange", timerange);
        System.out.println("queryMetricDataExternal body param :" + JSONObject.toJSONString(bodyParams));
        
        HttpResponse2 response = sendRequest(projectId, ak, sk, url, "POST", JSONObject.toJSONString(bodyParams));
        System.out.println("queryMetricDataExternal response statusCode: {}, content: {}" + response.getStausCode() + response.getContent());
        
    }

    public static void setData(String projectId, String ak, String sk, String aomEndpoint) throws Exception
    {
        // 对应url
        String url = aomEndpoint + "/v1/" + projectId + "/ams/report/metricdata";

        ReceiveMetricData data = new ReceiveMetricData();
        // 指标采集时间，UTC毫秒数
        data.setCollectTime(System.currentTimeMillis());
        
        RecieveMetricParam metricParam = new RecieveMetricParam();
        data.setMetric(metricParam);

        // value列表，把同一个资源的多个指标放到一个请求上报
        List<RecieveMetricValues> values = new ArrayList<RecieveMetricValues>();
        RecieveMetricValues value1 = new RecieveMetricValues();
        values.add(value1);
		//metricName是对应的指标名称
        String metricName = "cpuUsage";
        value1.setMetricName(metricName);
        value1.setType("int");
        value1.setUnit("Percent");
        value1.setValue(50);
        data.setValues(values);

        // 资源信息，定位唯一一个资源信息。举例：如果是车险订单，可以dimension.setName("oderType") dimension1.setValue("car")，存入数据时对应的资源信息为oderType=car
        List<Dimension> dims = new ArrayList<Dimension>();
        Dimension dimension1 = new Dimension();
        dimension1.setName("containerName");
        dimension1.setValue("api-gateway-image");
        dims.add(dimension1);
        metricParam.setDimensions(dims);
        // namespace为指标分类，是一组指标的集合。如果是订单相关的指标，可以设置为 Order
        String namespace = "CUSTOMMETRICS.TEST";
        metricParam.setNamespace(namespace);

        List<ReceiveMetricData> valueList = new ArrayList<ReceiveMetricData>();
        valueList.add(data);

    	HttpResponse2 response = sendRequest(projectId, ak, sk, url, "POST", JSONObject.toJSONString(valueList));
        System.out.println("set MetricDataExternal response statusCode: {}, content: {}" + response.getStausCode() + response.getContent());
        
    }

    public static HttpResponse2 sendRequest(String projectId, String ak, String sk, String url, String method, String bodyJson) throws Exception {
        //Create a new request.
        Request request = new Request();
        //Set the request parameters.
        //AppKey, AppSecrect, Method and Url are required parameters.
        request.setAppKey(ak);
        request.setAppSecrect(sk);
        request.setMethod(method);
        request.setUrl(url);
//            request.addQueryStringParam("name", "value");
        request.addHeader("Content-Type", "application/json");
        //if it was published in other envs(except for Release),you need to add the information x-stage and the value is env's name
        //request.addHeader("x-stage", "publish_env_name");
        request.setBody(bodyJson);

        
        CloseableHttpClient client = null;
        try {
            //Sign the request.
            HttpRequestBase signedRequest = Client.sign(request);
            for (Header h : signedRequest.getAllHeaders()) {
                System.out.println(h.getName() + ":" + h.getValue());
            }
            System.out.println("------------");
            //Send the request.
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).useTLS().build();
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, new AllowAllHostnameVerifier());
            client = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();
            HttpResponse response = client.execute(signedRequest);
            HttpResponse2 response2 = new HttpResponse2();
            response2.setStausCode(response.getStatusLine().getStatusCode());
            response2.setContent(EntityUtils.toString(response.getEntity(), "UTF-8"));
            return response2;
            
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception
    {
    	String ak = System.getenv("AK");
    	String sk = System.getenv("SK");
    	String projectId = System.getenv("PROJECT_ID");
    	// https://aom.cn-north-1.myhuaweicloud.com
    	String aomEndpoint = System.getenv("AOM_ENDPOINT");
    	
        setData(projectId, ak, sk, aomEndpoint);
        getData(projectId, ak, sk, aomEndpoint);
    }
}
