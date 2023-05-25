package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWithTimestampReaderBase;
import jp.ac.databaseexplorer.storage.visualization.QueryTime;
import jp.ac.databaseexplorer.storage.visualization.SarCpu;
import org.springframework.stereotype.Component;

/**
 * `QueryTimeCsvReader`はCSVファイルを読み込み、その内容をQueryTimeオブジェクトのリストとして提供します。
 * <p>
 * このクラスは、特定のCSVファイル（この場合はpg-stat-statements-query-time.csv）を読み込むために設計されています。
 * このファイルは、PostgreSQLのpg_stat_statementsモジュールによって生成される情報を保存します。
 */

@Component
public class QueryTimeCsvReader extends CsvWithTimestampReaderBase<QueryTime> {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/pg-stat-statements-query-time.csv";
  }

  @Override
  protected Class<QueryTime> modelClass() {
    return QueryTime.class;
  }
}
