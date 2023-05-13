package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * CPUリソース情報のリクエストメッセージ.
 */
@Data
@AllArgsConstructor
public class CpuUsageApiRequest {

  @NonNull
  private Date startTime;

  @NonNull
  private Date endTime;
}
