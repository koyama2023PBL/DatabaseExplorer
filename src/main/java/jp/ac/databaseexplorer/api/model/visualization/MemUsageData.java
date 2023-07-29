package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * プロセスメモリのデータを保持するモデルクラス
 */
@Data
@AllArgsConstructor
public class MemUsageData {

  @NonNull
  @JsonProperty("timestamp")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date timestamp;

  @NonNull
  @JsonProperty("memUsage")
  private Integer memUsage;

  @NonNull
  @JsonProperty("memUsageRatio")
  private Double memUsageRatio;

  @NonNull
  @JsonProperty("connections")
  private Integer connections;
}
