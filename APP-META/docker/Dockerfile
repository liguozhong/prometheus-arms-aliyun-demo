FROM reg.docker.alibaba-inc.com/java

ENV TZ=Asia/Shanghai
ENV TLOG_HOME /home/admin/tlog
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64

RUN set -ex && \
    apt-get update && \
    apt-get install vim-tiny net-tools tcpdump inetutils-traceroute sudo dnsutils telnet -y && \
    ln -sf /bin/bash /bin/sh && \
    adduser --disabled-password --gecos '' --home /home/admin admin && \
    mkdir -p /home/admin && mkdir -p /home/admin/tlog && mkdir -p /home/admin/tlog/examples && mkdir -p /home/admin/logs && touch /home/admin/tlog/RELEASE

RUN chown -R admin:admin /home/admin

WORKDIR /home/admin
RUN chmod g+w /home/admin

USER admin

RUN echo 'alias ll="ls -l"' >> ~/.bashrc && \
    curl -L https://alibaba.github.io/arthas/install.sh | sh

