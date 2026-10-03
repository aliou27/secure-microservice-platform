#!/bin/bash

(cd produit-service && ./mvnw spring-boot:run -Dmaven.test.skip=true) &

(cd commande-service && ./mvnw spring-boot:run -Dmaven.test.skip=true) &

(cd service-inventaire && ./mvnw spring-boot:run -Dmaven.test.skip=true) &

(cd api-gateway && ./mvnw spring-boot:run -Dmaven.test.skip=true) &

wait
