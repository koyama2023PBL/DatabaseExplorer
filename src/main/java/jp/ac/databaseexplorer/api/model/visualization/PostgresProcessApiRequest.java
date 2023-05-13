package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * 主要なプロセスの状態を取得するためのリクエストメッセージ.
 */
@Data
@AllArgsConstructor
public class PostgresProcessApiRequest {

  @NonNull
  private Date startTime;

  @NonNull
  private Date endTime;
}
