package com.huaweicloud.aom.demo.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticValues
{

    @JsonProperty("maximum")
    private double maximum = 0;

    @JsonProperty("minimum")
    private double minimum = 0;

    @JsonProperty("samplecount")
    private double samplecount = 0;

    @JsonProperty("sum")
    private double sum = 0;

    /**
     * 最大值。
     **/
    public double getMaximum()
    {
        return maximum;
    }

    public void setMaximum(double maximum)
    {
        this.maximum = maximum;
    }

    /**
     * 最小值。
     **/
    public double getMinimum()
    {
        return minimum;
    }

    public void setMinimum(double minimum)
    {
        this.minimum = minimum;
    }

    /**
     * 采样点个数。
     **/
    public double getSamplecount()
    {
        return samplecount;
    }

    public void setSamplecount(double samplecount)
    {
        this.samplecount = samplecount;
    }

    /**
     * 总计。
     **/
    public double getSum()
    {
        return sum;
    }

    public void setSum(double sum)
    {
        this.sum = sum;
    }

    @Override
    public String toString()
    {
        return "StatisticValues[maximum=" + maximum + ",minimum=" + minimum + ",samplecount=" + samplecount + ",sum=" + sum + "]";
    }

}
