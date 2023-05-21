package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * スロークエリ数のデータを保持するモデルクラス
 */
@Data
@AllArgsConstructor
public class SlowQueryCountData {

  @NonNull
  @JsonProperty("date")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date timestamp;

  @NonNull
  @JsonProperty("pid")
  private Integer pid;

  @NonNull
  @JsonProperty("queryTime")
  private Double queryTime;
}
