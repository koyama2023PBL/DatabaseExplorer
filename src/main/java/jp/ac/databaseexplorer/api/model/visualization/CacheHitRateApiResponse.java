package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * キャッシュヒット率を取得するレスポンスメッセージ.
 */
@Data
@AllArgsConstructor
public class CacheHitRateApiResponse {

  @NonNull
  @JsonProperty("starttime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date startTime;

  @NonNull
  @JsonProperty("endtime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date endTime;

  /**
   * データベース名
   */
  @NonNull
  @JsonProperty("dbname")
  private String databaseName;

  /**
   * キャッシュヒット率
   */
  @NonNull
  @JsonProperty("hitrate")
  private Double hitRate;
}
