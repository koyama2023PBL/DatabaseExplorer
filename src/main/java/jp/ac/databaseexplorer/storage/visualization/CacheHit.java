package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * データベースから取得したキャッシュヒットとディスクの読みとり回数を格納するモデルクラス
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "pg_stat_database")
public class CacheHit extends CsvWithTimestampModelBase {
  @CsvBindByPosition(position = 1)
  @Id
  @Column(name = "datname")
  private String dbName;

  @CsvBindByPosition(position = 2)
  @Column(name = "blks_hit")
  private Long hit;

  @CsvBindByPosition(position = 3)
  @Column(name = "blks_read")
  private Long read;

}
