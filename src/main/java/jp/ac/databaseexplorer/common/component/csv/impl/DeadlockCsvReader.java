package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWithTimestampReaderBase;
import jp.ac.databaseexplorer.storage.visualization.Deadlock;
import org.springframework.stereotype.Component;

/**
 * デッドロック数を保持するCSVファイルを読み込むクラス
 */
@Component
public class DeadlockCsvReader extends CsvWithTimestampReaderBase<Deadlock> {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/pg-stat-database-deadlock.csv";
  }

  @Override
  protected Class<Deadlock> modelClass() {
    return Deadlock.class;
  }
}
