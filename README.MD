//DB設定
//CREATE USER explorer WITH PASSWORD 'explorer' CREATEDB CREATEROLE;
CREATE USER explorer WITH PASSWORD 'explorer' SUPERUSER;

***DatabaseExplorer

こちらはデータベース

## Dockerでの開発環境準備の手順
### 手順
1. `databaseexplorer`直下でコンテナ立ち上げ
   ```bash
   $ docker-compose up -d --build
   ```
2. SSH接続できるか確認
   ```bash
   $ ssh explorer@localhost -p 2222
   ```
3. ポスグレが動いてるか確認
   ```bash
   $ psql -h localhost -p 5432 -U explorer -d explorer
   ```
4. `pg_stat_statements`を有効化
   ```bash
   # コンテナに入る
   $ docker exec -it de-psql bash
   
   # 設定ファイルに追記
   $ echo "shared_preload_libraries = 'pg_stat_statements'" >> /var/lib/postgresql/data/postgresql.conf

   # コンテナを再起動
   $ exit
   $ docker-compose restart
   
   # pg_stat_statementsを有効化
   $ psql -h localhost -p 5432 -U explorer -d explorer
   explorer=# CREATE EXTENSION pg_stat_statements;
   ```

### 注意点
- ポスグレをローカルで既に動かしている場合、ポートが重複します。
- SSHは2222ポートを使ってます。
