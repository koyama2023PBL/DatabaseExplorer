package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CPU使用率を取得するサービスクラス
 */
@Service
@RequiredArgsConstructor
public class CpuUsageService {

  /**
   * CPU使用率を取得する
   */
  public CpuUsageApiResponse getCpuUsage(CpuUsageApiRequest request) {
    return null;
  }
}
