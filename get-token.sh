#!/bin/bash
source api-gateway/.env
curl -s -X POST "http://localhost:8180/realms/tp-microservices/protocol/openid-connect/token" \
  -d client_id=gateway-client \
  -d client_secret="$GATEWAY_CLIENT_SECRET" \
  -d grant_type=password \
  -d username="$1" \
  -d password="$2" | python3 -c 'import sys,json; d=json.load(sys.stdin); print(d.get("access_token") or d)'
