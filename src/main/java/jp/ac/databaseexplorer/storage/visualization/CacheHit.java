package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * データベースから取得したキャッシュヒットとディスクの読みとり回数を格納するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CacheHit extends CsvWithTimestampModelBase {
  @CsvBindByPosition(position = 1)
  private String dbName;

  @CsvBindByPosition(position = 2)
  private Long hit;

  @CsvBindByPosition(position = 3)
  private Long read;

}
