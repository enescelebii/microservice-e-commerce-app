#!/bin/bash
set -euo pipefail

# main project root path (scripts/ klasörünün 1 üstü proje kökü)
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# import env file
ENV_FILE="$ROOT_PATH/.env"
if [[ -f "$ENV_FILE" ]]; then
  # .env içindeki anahtar=değer satırlarını yükle (yorum ve boş satırları atla)
  set -a
  # shellcheck disable=SC1090
  source <(grep -vE '^\s*#' "$ENV_FILE" | grep -vE '^\s*$')
  set +a
else
  echo ".env dosyası bulunamadı: $ENV_FILE" >&2
  exit 1
fi

# Template file location
TEMPLATE_PATH="$ROOT_PATH/realms/ecommerce-realm-template.json"
if [[ ! -f "$TEMPLATE_PATH" ]]; then
  echo "Template dosyası bulunamadı: $TEMPLATE_PATH" >&2
  exit 1
fi

# required env check
: "${MICRO_SERVICES_API_SECRET:?MICRO_SERVICES_API_SECRET .env içinde tanımlı olmalı}"

# output path
OUTPUT_PATH="$ROOT_PATH/realms/ecommerce-realm.json"

# replace placeholder and write output
# Not: sed delimiter olarak | kullanıyoruz ki secret içinde / olsa bile sorun çıkmasın.
sed "s|\${MICRO_SERVICES_API_SECRET}|${MICRO_SERVICES_API_SECRET}|g" \
  "$TEMPLATE_PATH" > "$OUTPUT_PATH"

echo "ecommerce-realm.json başarıyla oluşturuldu: $OUTPUT_PATH"