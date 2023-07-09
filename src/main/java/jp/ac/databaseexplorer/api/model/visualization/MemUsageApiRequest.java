package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * プロセスメモリの状態を取得するためのリクエストメッセージ.
 */
@Data
@AllArgsConstructor
public class MemUsageApiRequest {

  @NonNull
  private Date startTime;

  @NonNull
  private Date endTime;
}
