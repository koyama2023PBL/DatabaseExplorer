package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty("starttime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date startTime;

  @NonNull
  @JsonProperty("endtime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date endTime;

  /**
   * CPU使用率のデータを時系列で格納する配列
   */
  @NonNull
  @JsonProperty("data")
  private CpuUsageData[] cpuUsageData;

  @Override
  public String toString() {
    return "CpuUsageApiResponse(startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", cpuUsageData=" + java.util.Arrays.deepToString(this.getCpuUsageData()) + ")";
  }
}
