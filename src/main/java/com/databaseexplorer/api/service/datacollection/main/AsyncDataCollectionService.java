package com.databaseexplorer.api.service.datacollection.main;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/*
DB・DBサーバの情報取得用サービス。
非同期で実行し続ける必要がある。
OS側の情報取得と、RDBMS内のデータ取得を定期的に行う。
 */
@Async
@Component
public class AsyncDataCollectionService {
}
