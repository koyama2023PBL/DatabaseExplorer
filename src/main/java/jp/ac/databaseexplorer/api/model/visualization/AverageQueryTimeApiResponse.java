package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * 平均クエリ時間を取得するレスポンスメッセージ.
 */
@Data
@AllArgsConstructor
public class AverageQueryTimeApiResponse {

  @NonNull
  @JsonProperty("starttime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date startTime;

  @NonNull
  @JsonProperty("enddtime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date endTime;

  /**
   * クエリの種類
   * 1: SELECT
   * 2: INSERT
   * 3: UPDATE
   * 4: DELETE
   */
  @NonNull
  @JsonProperty("kind")
  private Short queryKind;

  /**
   * クエリ実行時間の平均値
   */
  @NonNull
  @JsonProperty("time")
  private Double queryTime;
}
