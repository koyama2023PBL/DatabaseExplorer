package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.AverageQueryTimeApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.AverageQueryTimeApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * クエリの平均実行時間を取得するサービスクラス
 */
@Service
@AllArgsConstructor
public class AverageQueryTimeService {

  /**
   * クエリの平均実行時間を取得する
   */
  public AverageQueryTimeApiResponse getAverageQueryTime(AverageQueryTimeApiRequest request) {
    return null;
  }
}
