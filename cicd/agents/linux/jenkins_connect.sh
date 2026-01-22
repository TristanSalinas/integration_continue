#!/bin/bash
set -euo pipefail

JENKINS_URL=${JENKINS_URL:-"http://jenkins-controller:8080"}
AGENT_NAME=${AGENT_NAME:-"linux-agent"}
AGENT_WORKDIR=${AGENT_WORKDIR:-"/home/jenkins/agent"}

AGENT_SECRET="$1"
if [ -z "$AGENT_SECRET" ]; then
  echo "Usage: $0 <AGENT_SECRET>  (or set AGENT_SECRET env var)"
  exit 1
fi

# Télécharger agent.jar
mkdir -p "$AGENT_WORKDIR"
wget -q "$JENKINS_URL/jnlpJars/agent.jar"

exec java -jar agent.jar \
  -url "$JENKINS_URL" \
  -secret "$AGENT_SECRET" \
  -name "$AGENT_NAME" \
  -workDir "$AGENT_WORKDIR"
