mvn clean install -DskipTests
docker build -t promethues-demo:v0 . --no-cache
sudo docker tag promethues-demo:v0 reg.docker.alibaba-inc.com/fuling/promethues-demo:v0
sudo docker push reg.docker.alibaba-inc.com/fuling/promethues-demo:v0

# https://cr.console.aliyun.com/cn-hangzhou/instances/repositories