#!/bin/bash
set -euo pipefail
cd "$(dirname "$0")"

if [ -f .env ]; then
  echo ".env ya existe; no se sobrescribe."
  exit 0
fi

aleatorio() { openssl rand -base64 "$1" | tr -d '/+=\n' | cut -c1-"$2"; }

# Generar un JWT Secret limpio sin caracteres problemáticos para JJWT
jwt_secret=$(openssl rand 32 | base64 | tr -d '/+=\n')

ADMIN_PASSWORD="$(aleatorio 32 20)"

cat > .env <<EOF
DB_USERNAME=PELUQUERIA
DB_PASSWORD=$(aleatorio 32 24)
JWT_SECRET=${jwt_secret}
JWT_EXPIRATION_MS=900000
ADMIN_USERNAME=admin
ADMIN_PASSWORD=${ADMIN_PASSWORD}
EOF

chmod 600 .env
echo ".env creado para Oracle. Usuario inicial: admin / ${ADMIN_PASSWORD}"