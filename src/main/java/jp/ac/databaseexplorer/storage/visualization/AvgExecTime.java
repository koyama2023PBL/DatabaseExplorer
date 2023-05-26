package jp.ac.databaseexplorer.storage.visualization;

import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * データベースから取得したクエリ実行時間の平均を格納するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AvgExecTime extends CsvWithTimestampModelBase {
  private Short kind;
  private Long calls;
  private Double totalExecTime;

}
