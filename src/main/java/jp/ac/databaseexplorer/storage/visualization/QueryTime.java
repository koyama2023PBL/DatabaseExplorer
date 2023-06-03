package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DBより取得したクエリの実行時間（プロセスごと）を保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTime extends CsvWithTimestampModelBase {
  @CsvBindByPosition(position = 1)
  private Integer pid;

  @CsvBindByPosition(position = 2)
  private Double queryTime;

}
