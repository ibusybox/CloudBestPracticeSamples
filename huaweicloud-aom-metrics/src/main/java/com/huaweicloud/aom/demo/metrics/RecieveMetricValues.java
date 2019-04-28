package com.huaweicloud.aom.demo.metrics;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecieveMetricValues
{
    @JsonProperty("metric_name")
    private String metricName = null;

    @JsonProperty("value")
    private double value = 0;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("unit")
    private String unit = null;

    @JsonProperty("statisticvalues")
    private StatisticValues statisticvalues = null;
    
    @JsonProperty("statistictype")
    private String statistictype = null;

    /**
     * 指标名称。
     **/
    public String getMetricName()
    {
        return metricName;
    }

    public void setMetricName(String metricName)
    {
        this.metricName = metricName;
    }

    /**
     * 统计方式类型。
     **/
    public String getStatistictype()
    {
        return statistictype;
    }

    public void setStatistictype(String statistictype)
    {
        this.statistictype = statistictype;
    }

    /**
     * 指标值。
     **/
    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    /**
     * 指标值类型。
     **/
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * 单位。
     **/
    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    /**
     * 指标数据的统计结果。
     **/
    public StatisticValues getStatisticvalues()
    {
        return statisticvalues;
    }

    public void setStatisticvalues(StatisticValues statisticvalues)
    {
        this.statisticvalues = statisticvalues;
    }

    @Override
    public String toString()
    {
        return "RecieveMetricValues [metricName=" + metricName + ", value=" + value + ", type=" + type + ", unit="
                + unit + ", statisticvalues=" + statisticvalues + ", statistictype=" + statistictype + "]";
    }
}
