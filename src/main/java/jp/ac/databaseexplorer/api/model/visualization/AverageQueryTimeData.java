package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * クエリ実行時間の平均データを保持するモデルクラス
 */
@Data
@AllArgsConstructor
public class AverageQueryTimeData {

  @NonNull
  @JsonProperty("date")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date timestamp;

  @NonNull
  @JsonProperty("kind")
  private Short queryKind;

  @NonNull
  @JsonProperty("time")
  private Double queryTime;
}
