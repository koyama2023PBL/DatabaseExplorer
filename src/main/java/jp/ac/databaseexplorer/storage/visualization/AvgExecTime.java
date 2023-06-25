package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * データベースから取得したクエリ実行時間の平均を格納するモデルクラス
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class AvgExecTime extends CsvWithTimestampModelBase {

  @CsvBindByPosition(position = 1)
  @Column(name = "kind")
  @Id
  private Short kind;

  @CsvBindByPosition(position = 2)
  @Column(name = "calls")
  private Long calls;

  @CsvBindByPosition(position = 3)
  @Column(name = "total_exec_time")
  private Double totalExecTime;


}
