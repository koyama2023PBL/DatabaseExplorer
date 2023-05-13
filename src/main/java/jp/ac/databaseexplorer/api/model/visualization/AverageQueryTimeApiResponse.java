package jp.ac.databaseexplorer.api.model.visualization;

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
  private Date startTime;

  @NonNull
  private Date endTime;

  /**
   * クエリの種類
   * 1: SELECT
   * 2: INSERT
   * 3: UPDATE
   * 4: DELETE
   */
  @NonNull
  private Short queryKind;

  /**
   * クエリ実行時間の平均値
   */
  @NonNull
  private Double queryTime;
}
