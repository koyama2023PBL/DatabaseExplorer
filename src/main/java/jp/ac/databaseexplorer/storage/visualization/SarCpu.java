package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
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
  @CsvBindByPosition(position = 1)
  private Double user;

  @CsvBindByPosition(position = 2)
  private Double nice;

  @CsvBindByPosition(position = 3)
  private Double system;

  @CsvBindByPosition(position = 4)
  private Double iowait;

  @CsvBindByPosition(position = 5)
  private Double steal;

  @CsvBindByPosition(position = 6)
  private Double idle;
}
