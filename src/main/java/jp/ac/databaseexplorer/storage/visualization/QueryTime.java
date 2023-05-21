package jp.ac.databaseexplorer.storage.visualization;

import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sarコマンドで取得したデータを保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTime extends CsvWithTimestampModelBase {
  private Integer pid;

  private Double queryTime;

}
