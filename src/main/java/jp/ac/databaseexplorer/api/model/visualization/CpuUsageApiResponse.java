package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * CPUリソース情報のレスポンスメッセージ
 */
@Data
@AllArgsConstructor
public class CpuUsageApiResponse {

  @NonNull
  private Date startTime;

  @NonNull
  private Date endTime;

  /**
   * CPU使用率のデータを時系列で格納する配列
   */
  @NonNull
  private CpuUsageData[] cpuUsageData;
}
