package jp.ac.databaseexplorer.async.main;

import jp.ac.databaseexplorer.async.repository.VisualizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * DB・DBサーバの情報取得用サービス。
 * 非同期で実行し続ける必要がある。
 * OS側の情報取得と、RDBMS内のデータ取得を定期的に行う。
 */
@Async
@Service
@RequiredArgsConstructor
public class AsyncDataCollectionService {

  private final VisualizeRepository visualizeRepository;

  public void start() {
    visualizeRepository.start();
  }
}
