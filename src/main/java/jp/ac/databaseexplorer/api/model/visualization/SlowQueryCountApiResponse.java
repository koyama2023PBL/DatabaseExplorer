package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * スロークエリ数を取得するレスポンスメッセージ.
 */
@Data
@AllArgsConstructor
public class SlowQueryCountApiResponse {

  @NonNull
  @JsonProperty("starttime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date startTime;

  @NonNull
  @JsonProperty("endtime")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date endTime;

  /**
   * クエリ実行時間の閾値
   * この値以上のクエリをスロークエリとしてカウントする
   */
  @NonNull
  @JsonProperty("querytime")
  private Double queryTimeAtLeast;

  /**
   * スロークエリ数
   */
  @NonNull
  @JsonProperty("count")
  private Integer queryCount;
}
