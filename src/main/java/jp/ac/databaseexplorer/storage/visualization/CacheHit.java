package jp.ac.databaseexplorer.storage.visualization;

import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * データベースから取得したキャッシュヒットとディスクの読みとり回数を格納するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CacheHit extends CsvWithTimestampModelBase {
  private String dbName;
  private Long hit;
  private Long read;

}
