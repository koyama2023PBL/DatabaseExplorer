package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * プロセスメモリ使用率・量と実行中のクエリ数のデータを保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessMem extends CsvWithTimestampModelBase {
  @CsvBindByPosition(position = 1)
  private Integer memUsage;

  @CsvBindByPosition(position = 2)
  private Double memUsageRatio;

  @CsvBindByPosition(position = 3)
  private Integer connections;

  public void setMemUsage(Integer parseInteger) {
    this.memUsage = parseInteger;
  }

  public void setMemUsageRatio(Double parseDouble) {
    this.memUsageRatio = parseDouble;
  }

  public void setConnections(Integer parseInteger) {
    this.connections = parseInteger;
  }
}
