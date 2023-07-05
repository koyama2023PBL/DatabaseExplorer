package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * デッドタプル数とデッドタプル率のデータを保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeadTup extends CsvWithTimestampModelBase {
  @CsvBindByPosition(position = 1)
  private Integer deadTupCount;

  @CsvBindByPosition(position = 2)
  private Double deadTupRatio;


  public void setDeadTupCount(Integer parseInteger) {
    this.deadTupCount = parseInteger;
  }

  public void setDeadTupRatio(Double parseDouble) {
    this.deadTupRatio = parseDouble;
  }

}
