package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * キャッシュヒット率を取得するリクエストメッセージ.
 */
@Data
@AllArgsConstructor
public class CacheHitRateApiRequest {

  @NonNull
  private Date startTime;

  @NonNull
  private Date endTime;

  /**
   * データベース名
   */
  @NonNull
  private String databaseName;
}
