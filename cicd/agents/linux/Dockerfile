FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven \
    mysql-client \
    git \
    curl \
    wget \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

RUN useradd -m -d /home/jenkins -s /bin/bash jenkins

RUN mkdir -p /home/jenkins/agent && \
    chown -R jenkins:jenkins /home/jenkins

COPY jenkins_connect.sh /usr/local/bin/jenkins_connect.sh
RUN sed -i 's/\r$//' /usr/local/bin/jenkins_connect.sh && chmod +x /usr/local/bin/jenkins_connect.sh

USER jenkins
WORKDIR /home/jenkins

CMD ["sleep", "infinity"]
