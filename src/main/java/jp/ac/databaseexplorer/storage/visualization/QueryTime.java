package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DBより取得したクエリの実行時間（プロセスごと）を保持するモデルクラス
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "pg_stat_activity")
public class QueryTime extends CsvWithTimestampModelBase {
  @CsvBindByPosition(position = 1)
  @Column(name = "pid")
  @Id
  private Integer pid;

  @CsvBindByPosition(position = 2)
  @Column(name = "execution_time")
  private Double queryTime;

}
