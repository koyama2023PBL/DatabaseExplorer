package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * デッドタプル数と割合のデータを保持するモデルクラス
 */
@Data
@AllArgsConstructor
public class DeadTupData {

  @NonNull
  @JsonProperty("timestamp")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date timestamp;

  @NonNull
  @JsonProperty("deadTupCount")
  private Integer deadTupCount;

  @NonNull
  @JsonProperty("deadTupRatio")
  private Double deadTupRatio;
}
