package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * デッドタプル数と割合のレスポンスメッセージ
 */
@Data
@AllArgsConstructor
public class DeadTupApiResponse {

  @NonNull
  @JsonProperty("starttime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date startTime;

  @NonNull
  @JsonProperty("endtime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date endTime;

  /**
   * デッドタプル数と割合のデータを時系列で格納する配列
   */
  @NonNull
  @JsonProperty("deadTupData")
  private DeadTupData[] deadTupData;
}
