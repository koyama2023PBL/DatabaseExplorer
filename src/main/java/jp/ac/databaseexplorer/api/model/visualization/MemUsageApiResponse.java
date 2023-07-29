package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * プロセスメモリ使用率・量を取得するレスポンスメッセージ.
 */
@Data
@AllArgsConstructor
public class MemUsageApiResponse {

  @NonNull
  @JsonProperty("starttime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date startTime;

  @NonNull
  @JsonProperty("endtime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date endTime;

  @NonNull
  @JsonProperty("workMem")
  private Double workMem;

  @NonNull
  @JsonProperty("maxConnections")
  private Integer maxConnections;

  /**
   * プロセスメモリのデータを時系列で格納する配列
   */
  @NonNull
  @JsonProperty("data")
  private MemUsageData[] memUsageData;
}
