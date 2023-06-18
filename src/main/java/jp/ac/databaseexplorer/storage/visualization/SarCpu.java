package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
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

  public void setCpuUser(double parseDouble) {
    this.user = parseDouble;
  }

  public void setCpuNice(double parseDouble) {
    this.nice = parseDouble;
  }

  public void setCpuSystem(double parseDouble) {
    this.system = parseDouble;
  }

  public void setCpuIowait(double parseDouble) {
    this.iowait = parseDouble;
  }

  public void setCpuSteal(double parseDouble) {
    this.steal = parseDouble;
  }

  public void setCpuIdle(double parseDouble) {
    this.idle = parseDouble;
  }
}
