#!/bin/bash
docker image push manju0707/crowd-projects-app-microservice-docker-cloud-config-server:0.0.1-SNAPSHOT 
docker image push manju0707/crowd-projects-app-microservice-docker-eureka-naming-server:0.0.1-SNAPSHOT 
docker image push manju0707/crowd-projects-app-microservice-docker-oauth2-authentication-server:0.0.1-SNAPSHOT 
docker image push manju0707/crowd-projects-app-microservice-docker-user-service:0.0.1-SNAPSHOT
docker image push manju0707/crowd-projects-app-microservice-docker-project-service:0.0.1-SNAPSHOT 
docker image push manju0707/crowd-projects-app-microservice-docker-zuul-api-gateway-server:0.0.1-SNAPSHOT