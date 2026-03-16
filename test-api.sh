#!/bin/bash

# Script para probar la API de ACME Envío de Pedidos

BASE_URL="http://localhost:8080/acme-envio-pedidos"

echo "=== Probando API de ACME Envío de Pedidos ==="
echo

# Health Check
echo "1. Health Check:"
curl -s "$BASE_URL/api/pedidos/health"
echo -e "\n"

# Enviar Pedido
echo "2. Enviar Pedido:"
curl -X POST "$BASE_URL/api/pedidos/enviar" \
  -H "Content-Type: application/json" \
  -d '{
    "enviarPedido": {
        "numPedido": "75630275",
        "cantidadPedido": "1",
        "codigoEAN": "00110000765191002104587",
        "nombreProducto": "Armario INVAL",
        "numDocumento": "1113987400",
        "direccion": "CR 72B 45 12 APT 301"
    }
  }'
echo -e "\n"

echo "=== Pruebas completadas ==="
