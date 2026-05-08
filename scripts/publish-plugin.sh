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

for var in CERTIFICATE_CHAIN PRIVATE_KEY PRIVATE_KEY_PASSWORD PUBLISH_TOKEN; do
  if [[ -z "${!var:-}" ]]; then
    echo "Error: $var is missing in .env.secrets" >&2
    exit 1
  fi
done

VERSION=$(grep 'version = ' build.gradle.kts | head -1 | sed 's/.*"\(.*\)".*/\1/')
echo "About to publish version $VERSION to JetBrains Marketplace."
echo "Press Ctrl-C in 3s to abort..."
sleep 3

./gradlew publishPlugin --no-configuration-cache "$@"

echo
echo "Published: $VERSION"
echo "Check status at https://plugins.jetbrains.com/plugin/edit"
