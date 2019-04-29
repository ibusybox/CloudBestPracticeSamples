# HuaweiCloudAOMMetrics
Sample for report customized metrics to Huaweicloud [AOM](https://www.huaweicloud.com/product/aom.html).

This sample is wroten in Java language, for other languages reference the [API FULL SDK EN](https://support.huaweicloud.com/en-us/devg-apig/apig-en-dev-180307017.html) or [API FULL SDK 中文](https://support.huaweicloud.com/devg-apig/apig-zh-dev-180307017.html).

## AOM help documents
https://support.huaweicloud.com/api-aom/aom_04_0000.html

## Build and Run
### Build
1. git clone
2. import to ide, e.g Eclipse
3. maven install

### Run

Main class: com.huaweicloud.aom.demo.metrics.AOMSDKRest

Should set 4 environments:
- PROJECT_ID: [your project id]
- AK: [your access key]
- SK: [your secret key]
- AOM_ENDPOINT: Query endpoint from [here](https://support.huaweicloud.com/api-aom/aom_04_0002.html)

#### Sample response of query metrics

```
{
    "errorCode": "SVCSTG_AMS_2000000",
    "errorMessage": "success",
    "metrics": [
        {
            "metric": {
                "namespace": "CUSTOMMETRICS.TEST",
                "metricName": "cpuUsage",
                "dimensions": [
                    {
                        "name": "containerName",
                        "value": "api-gateway-image"
                    }
                ]
            },
            "dataPoints": [
                {
                    "timestamp": 1546914480000,
                    "unit": "Percent",
                    "statistics": [
                        {
                            "statistic": "maximum",
                            "value": -1
                        }
                    ]
                },
                {
                    "timestamp": 1546914540000,
                    "unit": "Percent",
                    "statistics": [
                        {
                            "statistic": "maximum",
                            "value": -1
                        }
                    ]
                },
                {
                    "timestamp": 1546914600000,
                    "unit": "Percent",
                    "statistics": [
                        {
                            "statistic": "maximum",
                            "value": -1
                        }
                    ]
                },
                {
                    "timestamp": 1546914660000,
                    "unit": "Percent",
                    "statistics": [
                        {
                            "statistic": "maximum",
                            "value": -1
                        }
                    ]
                },
                {
                    "timestamp": 1546914720000,
                    "unit": "Percent",
                    "statistics": [
                        {
                            "statistic": "maximum",
                            "value": 50
                        }
                    ]
                },
                {
                    "timestamp": 1546914780000,
                    "unit": "Percent",
                    "statistics": [
                        {
                            "statistic": "maximum",
                            "value": 50
                        }
                    ]
                }
            ]
        }
    ]
}
```

#### 在AOM界面查看自定义指标
AOM->试图管理->指标监控->自定义指标->CUSTOMMETRICS.TEST