mvn clean install -DskipTests
docker build -t promethues-demo:v0 . --no-cache
sudo docker tag promethues-demo:v0 registry.cn-hangzhou.aliyuncs.com/fuling/promethues-demo:v0
sudo docker push registry.cn-hangzhou.aliyuncs.com/fuling/promethues-demo:v0

# https://cr.console.aliyun.com/cn-hangzhou/instances/repositories