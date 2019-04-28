package com.huaweicloud.aom.demo.metrics;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * receive metric data.
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveMetricData
{
    @JsonProperty("metric")
    private RecieveMetricParam metric = null;

    @JsonProperty("collect_time")
    private long collectTime = 0;

    @JsonProperty("values")
    private List<RecieveMetricValues> values = new ArrayList<RecieveMetricValues>();

    /**
    **/
    public RecieveMetricParam getMetric()
    {
        return metric;
    }

    public void setMetric(RecieveMetricParam metric)
    {
        this.metric = metric;
    }

    /**
    **/
    public long getCollectTime()
    {
        return collectTime;
    }

    public void setCollectTime(long collectTime)
    {
        this.collectTime = collectTime;
    }

    /**
    **/
    public List<RecieveMetricValues> getValues()
    {
        return values;
    }

    public void setValues(List<RecieveMetricValues> values)
    {
        this.values = values;
    }

    @Override
    public String toString()
    {
        return "ReceiveMetricData[metric=" + (null == metric ? "" : metric.toString()) + ",collectTime=" + collectTime
                + ",values=" + values + "]";
    }
}
