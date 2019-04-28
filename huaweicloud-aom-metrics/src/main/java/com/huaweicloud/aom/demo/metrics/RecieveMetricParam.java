package com.huaweicloud.aom.demo.metrics;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecieveMetricParam
{

    private String namespace = null;

    private List<Dimension> dimensions = new ArrayList<Dimension>();

    /**
     * 指标的命名空间。
     **/
    public String getNamespace()
    {
        return namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    /**
     * 指标的维度信息。
     **/
    public List<Dimension> getDimensions()
    {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions)
    {
        this.dimensions = dimensions;
    }

    @Override
    public String toString()
    {
        return "RecieveMetricParam[namespace=" + namespace + ",dimensions=" + (null == dimensions ? "" : dimensions.toString()) + "]";
    }

}
