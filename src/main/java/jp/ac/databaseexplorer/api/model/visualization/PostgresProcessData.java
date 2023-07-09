package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * PosgreSQLのプロセスの状態を保持するモデルクラス
 */
@Data
@AllArgsConstructor
public class PostgresProcessData {

  @NonNull
  @JsonProperty("date") //なぜ必要？
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date timestamp;

  @JsonProperty("masterProcess")
  private Boolean masterProcess;

  @JsonProperty("checkPointer")
  private Boolean checkPointer;

  @JsonProperty("autoVacuumLauncher")
  private Boolean autoVacuumLauncher;

  @JsonProperty("walWriter")
  private Boolean walWriter;

  @JsonProperty("writer")
  private Boolean writer;

  @JsonProperty("statisticsCollector")
  private Boolean statisticsCollector;

  @JsonProperty("autoVacuumWorker")
  private Boolean autoVacuumWorker;

}
