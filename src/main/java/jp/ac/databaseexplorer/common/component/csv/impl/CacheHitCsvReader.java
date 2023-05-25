package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWithTimestampReaderBase;
import jp.ac.databaseexplorer.storage.visualization.CacheHit;
import org.springframework.stereotype.Component;

/**
 * データベースから取得したキャッシュヒットとディスクの読みとり回数を格納したCSVファイルを読み込むクラス
 */
@Component
public class CacheHitCsvReader extends CsvWithTimestampReaderBase<CacheHit> {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/pg-stat-database-cache.csv";
  }

  @Override
  protected Class<CacheHit> modelClass() {
    return CacheHit.class;
  }
}
