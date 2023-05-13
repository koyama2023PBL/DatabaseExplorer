package jp.ac.databaseexplorer.storage.visualization;

import jp.ac.databaseexplorer.storage.base.CsvModelBase;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sarコマンドで取得したデータを保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SarCpu extends CsvWithTimestampModelBase {
  private Double user;
  private Double nice;
  private Double system;
  private Double iowait;
  private Double steal;
  private Double idle;
}
