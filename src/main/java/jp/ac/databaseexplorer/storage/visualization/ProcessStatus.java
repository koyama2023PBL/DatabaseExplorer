package jp.ac.databaseexplorer.storage.visualization;

import com.opencsv.bean.CsvBindByPosition;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * プロセスの状態を保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessStatus extends CsvWithTimestampModelBase{

  @CsvBindByPosition(position = 1)
  private String master;
  public Boolean getMasterProcess() {
    return ConvertToBoolean(master);
  }

  @CsvBindByPosition(position = 2)
  private String checkPointer;
  public Boolean getCheckPointer() {
    return ConvertToBoolean(checkPointer);
  }

  @CsvBindByPosition(position = 3)
  private String autoVacuumLauncher;
  public Boolean getAutoVacuumLauncher() {
    return ConvertToBoolean(autoVacuumLauncher);
  }

  @CsvBindByPosition(position = 4)
  private String walWriter;
  public Boolean getWalWriter() {
    return ConvertToBoolean(walWriter);
  }

  @CsvBindByPosition(position = 5)
  private String writer;
  public Boolean getWriter() {
    return ConvertToBoolean(writer);
  }

  @CsvBindByPosition(position = 6)
  private String statisticsCollector;
  public Boolean getStatisticsCollector() {
    return ConvertToBoolean(statisticsCollector);
  }

  @CsvBindByPosition(position = 7)
  private String autoVacuumWorker;
  public Boolean getAutoVacuumWorker() {
    return ConvertToBoolean(autoVacuumWorker);
  }

  @CsvBindByPosition(position = 8)
  private String backendProcess;
  public Boolean getBackendProcess() {
    return ConvertToBoolean(backendProcess);
  }


  private Boolean ConvertToBoolean(String value) {
    if ("1".equals(value)) {
      return true;
    } else if("0".equals(value)) {
      return false;
    }
    return false;
  }
}
