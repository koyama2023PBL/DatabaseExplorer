package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * スロークエリ数を取得するリクエストメッセージ.
 */
@Data
@AllArgsConstructor
public class SlowQueryCountApiRequest {

  @NonNull
  private Date startTime;

  @NonNull
  private Date endTime;

  /**
   * クエリ実行時間の閾値
   * この値以上のクエリをスロークエリとしてカウントする
   */
  @NonNull
  private Double queryTimeAtLeast;
}
