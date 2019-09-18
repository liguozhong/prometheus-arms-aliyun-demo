例子 prometheus exporter url :

http://localhost:8081/prometheus-metrics

例子 jvm exporter:


		<dependency>
			<groupId>io.prometheus</groupId>
			<artifactId>simpleclient_hotspot</artifactId>
			<version>0.6.0</version>
		</dependency>

例子 prometheus-arms-aliyun url :

https://arms.console.aliyun.com/#/prom/plugin


例子 serviceMonitor.yaml:

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


例子 deploy.yaml:

apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: promethues-tomcat-demo
spec:
  replicas: 2
  template:
    metadata:
#      annotations:
#        prometheus.io/scrape: 'true'
#        prometheus.io/path: '/prometheus-metrics'
#        prometheus.io/port: '8081'
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


例子 service.yaml
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
