FROM jenkins/jenkins:lts

USER root

RUN apt-get update && apt-get install -y \
    wget \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    && curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg \
    && echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null \
    && apt-get update \
    && apt-get install -y docker-ce-cli \
    && rm -rf /var/lib/apt/lists/*

RUN apt-get update && apt-get install -y \
    maven \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /opt/maven \
    && wget https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz -O /tmp/apache-maven-3.9.6-bin.tar.gz \
    && tar -xzf /tmp/apache-maven-3.9.6-bin.tar.gz -C /opt/maven \
    && rm /tmp/apache-maven-3.9.6-bin.tar.gz

RUN jenkins-plugin-cli --plugins \
    git \
    workflow-aggregator \
    configuration-as-code:latest \
    docker-workflow \
    junit \
    maven-plugin \
    credentials-binding \
    ssh-agent

USER jenkins

# Configuration par défaut
ENV JAVA_OPTS="-Djenkins. install.runSetupWizard=false"
