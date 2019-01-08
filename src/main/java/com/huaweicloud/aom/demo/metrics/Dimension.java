package com.huaweicloud.aom.demo.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dimension
{

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("value")
    private String value = null;

    /**
     * 维度名称。
     **/
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * 维度值。
     **/
    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "Dimension[name=" + name + ",value=" + value + "]";
    }

}
