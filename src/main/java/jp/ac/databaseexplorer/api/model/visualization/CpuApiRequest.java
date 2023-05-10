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
public class CpuApiRequest {

  @NonNull
  private Date fromDate;

  @NonNull
  private Date toDate;

}
