package jp.ac.databaseexplorer.storage.visualization;

import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ＤＢより取得したクエリの実行時間（プロセスごと）を保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTime extends CsvWithTimestampModelBase {
  private Integer pid;

  private Double queryTime;

}
