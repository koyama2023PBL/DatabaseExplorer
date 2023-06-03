package jp.ac.databaseexplorer.async.main;

import jp.ac.databaseexplorer.async.repository.VisualizeRepository;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * DB・DBサーバの情報取得用サービス。
 * 非同期で実行し続ける必要がある。
 * OS側の情報取得と、RDBMS内のデータ取得を定期的に行う。
 */
@Service
@Async
@RequiredArgsConstructor
public class AsyncDataCollectionService {

  /**
   * Repository
   */
  private final VisualizeRepository visualizeRepository;
  // private final AnalyzeRepository analyzeRepository;
  // private final SuggestRepository suggestRepository;

  /**
   * データ取得処理のエントリーポイント
   */
  @Scheduled(fixedRateString = "${db.data.async.collection.interval}")
  public void start() throws ApplicationException {
//    visualizeRepository.start();
    // analyzeRepository.start();
    // suggestRepository.start();
  }
}
