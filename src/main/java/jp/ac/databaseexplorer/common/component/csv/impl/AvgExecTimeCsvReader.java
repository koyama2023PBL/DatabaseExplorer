package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWithTimestampReaderBase;
import jp.ac.databaseexplorer.storage.visualization.AvgExecTime;
import org.springframework.stereotype.Component;

/**
 * データベースから取得したクエリ実行時間の平均のCSVファイルを読み込むクラス
 */
@Component
public class AvgExecTimeCsvReader extends CsvWithTimestampReaderBase<AvgExecTime> {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/pg-stat-statements-mean-exec-time.csv";
  }

  @Override
  protected Class<AvgExecTime> modelClass() {
    return AvgExecTime.class;
  }
}
