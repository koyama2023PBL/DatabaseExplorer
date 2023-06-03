package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * データベースから取得したクエリ実行時間の平均を格納するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AvgExecTime extends CsvWithTimestampModelBase {

  @CsvBindByPosition(position = 1)
  private Short kind;

  @CsvBindByPosition(position = 2)
  private Long calls;

  @CsvBindByPosition(position = 3)
  private Double totalExecTime;

}
