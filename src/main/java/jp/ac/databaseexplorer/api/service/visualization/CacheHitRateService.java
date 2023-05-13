package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.CacheHitRateApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CacheHitRateApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * キャッシュヒット率を取得するサービスクラス
 */
@Service
@AllArgsConstructor
public class CacheHitRateService {

  /**
   * キャッシュヒット率を取得する
   */
  public CacheHitRateApiResponse getCacheHitRate(CacheHitRateApiRequest request) {
    return null;
  }
}
