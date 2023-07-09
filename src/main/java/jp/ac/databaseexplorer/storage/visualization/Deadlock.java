package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * デッドロック数のデータを保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Deadlock extends CsvWithTimestampModelBase {
  @CsvBindByPosition(position = 1)
  private String dbName;

  @CsvBindByPosition(position = 2)
  private Long deadlocks;


  public void setDbName(String parseString) { this.dbName = parseString; }

  public void setDeadlocks(Long parseLong) { this.deadlocks = parseLong; }

}
