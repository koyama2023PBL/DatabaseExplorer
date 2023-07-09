package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWithTimestampReaderBase;
import jp.ac.databaseexplorer.storage.visualization.DeadTup;
import org.springframework.stereotype.Component;

/**
 * デッドタプル数と割合を保持するCSVファイルを読み込むクラス
 */
@Component
public class DeadTupCsvReader extends CsvWithTimestampReaderBase<DeadTup> {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/dead-tup.csv";
  }

  @Override
  protected Class<DeadTup> modelClass() {
    return DeadTup.class;
  }
}
