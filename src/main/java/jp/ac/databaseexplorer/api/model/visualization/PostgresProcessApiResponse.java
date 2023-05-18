package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * 主要なプロセスの状態を取得するためのレスポンスメッセージ.
 */
@Data
@AllArgsConstructor
public class PostgresProcessApiResponse {

  @NonNull
  @JsonProperty("starttime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date startTime;

  @NonNull
  @JsonProperty("endtime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date endTime;

  /* ここから下は「各プロセスが有効かどうか」 */

  @NonNull
  @JsonProperty("masterProcess")
  private Boolean masterProcess;

  @NonNull
  @JsonProperty("walWriter")
  private Boolean walWriter;

  @NonNull
  @JsonProperty("writer")
  private Boolean writer;

  @NonNull
  @JsonProperty("checkPointer")
  private Boolean checkPointer;

  @NonNull
  @JsonProperty("statisticsCollector")
  private Boolean statisticsCollector;

  @NonNull
  @JsonProperty("autoVacuumLauncher")
  private Boolean autoVacuumLauncher;

  @NonNull
  @JsonProperty("autoVacuumWorker")
  private Boolean autoVacuumWorker;

  @NonNull
  @JsonProperty("backendProcess")
  private Boolean backendProcess;
}
