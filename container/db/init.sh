#!/bin/bash
set -e

# PostgreSQLの設定ファイルのパスを取得
# CONF="$(find / -name postgresql.conf)"
CONF=/var/lib/postgresql/data/postgresql.conf

# 設定ファイルにshared_preload_librariesの設定を追加
echo "shared_preload_libraries = 'pg_stat_statements'" >> $CONF

# PostgreSQLを起動
exec "docker-entrypoint.sh" postgres
