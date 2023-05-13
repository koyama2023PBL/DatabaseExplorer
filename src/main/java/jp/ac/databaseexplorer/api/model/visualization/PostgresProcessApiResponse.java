package jp.ac.databaseexplorer.api.model.visualization;

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
  private Date startTime;

  @NonNull
  private Date endTime;

  /* ここから下は「各プロセスが有効かどうか」 */

  @NonNull
  private Boolean masterProcess;

  @NonNull
  private Boolean walWriter;

  @NonNull
  private Boolean writer;

  @NonNull
  private Boolean checkPointer;

  @NonNull
  private Boolean statisticsCollector;

  @NonNull
  private Boolean autoVacuumLauncher;

  @NonNull
  private Boolean autoVacuumWorker;

  @NonNull
  private Boolean backendProcess;
}
