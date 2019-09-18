最终效果：http://arms-prom-grafana-hz.aliyun.com/dashboard/snapshot/5A0Uwzw62rabCTYQriSF8vJWNLZI0rlN

已完成案例：基于官方的jvm exporter完成grafana大盘。

##前置：验证前建议先导入Prometheus的grafana自监控大盘
https://grafana.com/grafana/dashboards/10876

##例子 prometheus exporter url :

http://localhost:8081/prometheus-metrics

##例子 jvm exporter:


		<dependency>
			<groupId>io.prometheus</groupId>
			<artifactId>simpleclient_hotspot</artifactId>
			<version>0.6.0</version>
		</dependency>

##例子 prometheus-arms-aliyun url :

https://arms.console.aliyun.com/#/prom/plugin

##例子：grafana 大盘模版

https://grafana.com/grafana/dashboards/10877
https://grafana.com/grafana/dashboards/4701

##例子 serviceMonitor.yaml:

apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  #  填写一个唯一的名字
  name: tomcat-demo
  #  填写要放到哪个命名空间下
  namespace: default
spec:
  endpoints:
  - interval: 30s
    #填写 service.yaml 里面 prometheus exporter 对应的 port的name字段的值
    port: tomcat-monitor
    #填写 prometheus exporter代码中暴露的地址
    path: /prometheus-metrics
  namespaceSelector:
    any: true
    #nginx demo的namespace
  selector:
    matchLabels:
      #填写 service.yaml 的label字段，用来定位目标service.yaml
      app: tomcat


##例子 deploy.yaml:

apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: promethues-tomcat-demo
spec:
  replicas: 2
  template:
    metadata:
      annotations:
        armsPilotAutoEnable: "on"
        armsPilotCreateAppName: "arms-prom-tomcat-demo"
      labels:
        app: tomcat
    spec:
      containers:
      - name: tomcat
        imagePullPolicy: Always
        image: registry.cn-hangzhou.aliyuncs.com/fuling/promethues-demo:v0
        ports:
        - containerPort: 8080
          name: tomcat-normal
        - containerPort: 8081
          name: tomcat-monitor


##例子 service.yaml

apiVersion: v1
kind: Service
metadata:
  labels:
    app: tomcat
  name: tomcat
spec:
  ports:
  - name: tomcat-normal
    port: 8080
    protocol: TCP
    targetPort: 8080
  - name: tomcat-monitor
    port: 8081
    protocol: TCP
    targetPort: 8081
  type: NodePort
  selector:
    app: tomcat



##TODO：高级jvm微服务例子
exporter：
https://github.com/mweirauch/micrometer-jvm-extras
grafana：
https://grafana.com/grafana/dashboards/4701