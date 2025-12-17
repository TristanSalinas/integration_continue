#!/bin/bash

# Variables d'environnement à définir
JENKINS_URL=${JENKINS_URL:-"http://jenkins-controller:8080"}
AGENT_NAME=${AGENT_NAME:-"linux-agent"}
AGENT_SECRET=${AGENT_SECRET:-""}
AGENT_WORKDIR=${AGENT_WORKDIR:-"/home/jenkins/agent"}

echo "🚀 Démarrage de l'agent Jenkins..."
echo "Jenkins URL: $JENKINS_URL"
echo "Agent Name: $AGENT_NAME"

# Attendre que Jenkins soit prêt
until curl -s $JENKINS_URL > /dev/null; do
  echo "⏳ Attente de Jenkins..."
  sleep 5
done

echo "✅ Jenkins est prêt !"

# Télécharger agent.jar
cd $AGENT_WORKDIR
wget -q $JENKINS_URL/jnlpJars/agent.jar

# Lancer l'agent
exec java -jar agent.jar \
  -jnlpUrl $JENKINS_URL/computer/$AGENT_NAME/jenkins-agent. jnlp \
  -secret $AGENT_SECRET \
  -workDir "$AGENT_WORKDIR"
