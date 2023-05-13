package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * キャッシュヒット率を取得するレスポンスメッセージ.
 */
@Data
@AllArgsConstructor
public class CacheHitRateApiResponse {

  @NonNull
  private Date startTime;

  @NonNull
  private Date endTime;

  /**
   * データベース名
   */
  @NonNull
  private String databaseName;

  /**
   * キャッシュヒット率
   */
  @NonNull
  private Double hitRate;
}
