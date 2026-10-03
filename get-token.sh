#!/bin/bash

KEYCLOAK_URL="http://localhost:8180"
REALM="tp-microservices"
CLIENT_ID="11fvbo63BKE9S5elkMum98aZ0fxWiMh6inxfgUm9p8fJ6J8PPwG7WwpyWSnzjR8N1lZelGlCSEf1JrZKj7XAnZ"

echo
echo "================================"
echo "       Keycloak Token Tool"
echo "================================"
echo
echo "Keycloak:"
echo "$KEYCLOAK_URL/admin"
echo
echo "Available users:"
echo "1) alice"
echo "2) bob"
echo "3) testuser3"
echo

read -p "Which user do you want to connect with? " USERNAME

read -s -p "Password for $USERNAME: " PASSWORD
echo
echo

RESPONSE=$(curl -s -X POST \
  "$KEYCLOAK_URL/realms/$REALM/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=$CLIENT_ID" \
  -d "grant_type=password" \
  -d "username=$USERNAME" \
  -d "password=$PASSWORD")

TOKEN=$(echo "$RESPONSE" | python3 -c '
import sys
import json

try:
    data = json.load(sys.stdin)
    print(data.get("access_token", ""))
except:
    print("")
')

if [ -z "$TOKEN" ]; then
    echo
    echo "Authentication failed."
    echo
    echo "$RESPONSE"
    exit 1
fi

export TOKEN

echo
echo "Authenticated successfully as: $USERNAME"
echo
echo "TOKEN is ready."
echo
echo "Test:"
echo 'curl -i http://localhost:8080/api/produits -H "Authorization: Bearer $TOKEN"'
echo
