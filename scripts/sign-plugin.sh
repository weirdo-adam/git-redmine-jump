#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

if [[ ! -f .env.secrets ]]; then
  echo "Error: .env.secrets not found in $(pwd)" >&2
  exit 1
fi

set -a
# shellcheck disable=SC1091
source .env.secrets
set +a

for var in CERTIFICATE_CHAIN PRIVATE_KEY PRIVATE_KEY_PASSWORD; do
  if [[ -z "${!var:-}" ]]; then
    echo "Error: $var is missing in .env.secrets" >&2
    exit 1
  fi
done

# providers.environmentVariable(...) interacts badly with the configuration
# cache, so disable it here to ensure the current env values are used.
./gradlew signPlugin --no-configuration-cache "$@"

echo
echo "Signed artifact:"
ls -lh build/distributions/*-signed.zip
