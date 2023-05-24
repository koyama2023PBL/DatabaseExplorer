package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * データベースから取得したキャッシュヒット率を格納するモデルクラス
 */
@Data
@AllArgsConstructor
public class CacheHitRateData {

  @NonNull
  @JsonProperty("date")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date timestamp;

  @NonNull
  @JsonProperty("dbname")
  private String databaseName;

  @NonNull
  @JsonProperty("hitrate")
  private Double hitRate;
}
