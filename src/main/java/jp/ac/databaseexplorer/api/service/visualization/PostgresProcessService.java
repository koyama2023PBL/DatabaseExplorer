package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.PostgresProcessApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.PostgresProcessApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Postgresのプロセス情報を取得するサービスクラス
 */
@Service
@RequiredArgsConstructor
public class PostgresProcessService {

  /**
   * Postgresのプロセス情報を取得する
   */
  public PostgresProcessApiResponse getPostgresProcess(PostgresProcessApiRequest request) {
    return null;
  }
}
