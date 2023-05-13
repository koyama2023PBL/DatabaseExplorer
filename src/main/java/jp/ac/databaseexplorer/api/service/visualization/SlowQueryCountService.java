package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.SlowQueryCountApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.SlowQueryCountApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * スロークエリの数を取得するサービスクラス
 */
@Service
@AllArgsConstructor
public class SlowQueryCountService {

  /**
   * スロークエリの数を取得する
   */
  public SlowQueryCountApiResponse getSlowQueryCount(SlowQueryCountApiRequest request) {
    return null;
  }
}
