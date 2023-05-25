package jp.ac.databaseexplorer.storage.visualization;

import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * プロセスの状態を保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessStatus extends CsvWithTimestampModelBase{


  private String master;
  public Boolean getMasterProcess() {
    return ConvertToBoolean(master);
  }


  private String checkPointer;
  public Boolean getCheckPointer() {
    return ConvertToBoolean(checkPointer);
  }

  private String autoVacuumLauncher;
  public Boolean getAutoVacuumLauncher() {
    return ConvertToBoolean(autoVacuumLauncher);
  }

  private String walWriter;
  public Boolean getWalWriter() {
    return ConvertToBoolean(walWriter);
  }

  private String writer;
  public Boolean getWriter() {
    return ConvertToBoolean(writer);
  }

  private String statisticsCollector;
  public Boolean getStatisticsCollector() {
    return ConvertToBoolean(statisticsCollector);
  }

  private String autoVacuumWorker;
  public Boolean getAutoVacuumWorker() {
    return ConvertToBoolean(autoVacuumWorker);
  }

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
