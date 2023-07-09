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

  public ProcessStatus() {
    this.master = "0";
    this.checkPointer = "0";
    this.autoVacuumLauncher = "0";
    this.walWriter = "0";
    this.writer = "0";
    this.statisticsCollector = "0";
    this.autoVacuumWorker = "0";
  }

  @CsvBindByPosition(position = 1)
  protected String master;
  public Boolean getMasterProcess() {
    return ConvertToBoolean(master);
  }

  @CsvBindByPosition(position = 2)
  protected String checkPointer;
  public Boolean getCheckPointerProcess() {
    return ConvertToBoolean(checkPointer);
  }

  @CsvBindByPosition(position = 3)
  protected String autoVacuumLauncher;
  public Boolean getAutoVacuumLauncherProcess() {
    return ConvertToBoolean(autoVacuumLauncher);
  }

  @CsvBindByPosition(position = 4)
  protected String walWriter;
  public Boolean getWalWriterProcess() {
    return ConvertToBoolean(walWriter);
  }

  @CsvBindByPosition(position = 5)
  protected String writer;
  public Boolean getWriterProcess() {
    return ConvertToBoolean(writer);
  }

  @CsvBindByPosition(position = 6)
  protected String statisticsCollector;
  public Boolean getStatisticsCollectorProcess() {
    return ConvertToBoolean(statisticsCollector);
  }

  @CsvBindByPosition(position = 7)
  protected String autoVacuumWorker;
  public Boolean getAutoVacuumWorkerProcess() {
    return ConvertToBoolean(autoVacuumWorker);
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
