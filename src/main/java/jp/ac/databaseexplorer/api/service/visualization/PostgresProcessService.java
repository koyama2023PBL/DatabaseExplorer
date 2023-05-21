package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.PostgresProcessApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.PostgresProcessApiResponse;
import jp.ac.databaseexplorer.common.component.csv.impl.ProcessStatusCsvReader;
import jp.ac.databaseexplorer.storage.visualization.ProcessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Postgresのプロセス情報を取得するサービスクラス
 */
@Service
@RequiredArgsConstructor
public class PostgresProcessService {

  /**
   * Reader
   */
  private final ProcessStatusCsvReader reader;

  /**
   * Postgresのプロセス情報を取得する
   */
  public PostgresProcessApiResponse getPostgresProcess(PostgresProcessApiRequest request) {
    try {
      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();
      List<ProcessStatus> postgresProcessData = reader.read(startTime, endTime);

      boolean masterProcess = !(postgresProcessData.stream().anyMatch(processStatus -> !processStatus.getMasterProcess()));
      boolean backendProcess = !(postgresProcessData.stream().anyMatch(processStatus -> !processStatus.getBackendProcess()));
      boolean autoVacuumLauncher = !(postgresProcessData.stream().anyMatch(processStatus -> !processStatus.getAutoVacuumLauncher()));
      boolean checkPointer = !(postgresProcessData.stream().anyMatch(processStatus -> !processStatus.getCheckPointer()));
      boolean statisticsCollector = !(postgresProcessData.stream().anyMatch(processStatus -> !processStatus.getStatisticsCollector()));
      boolean walWriter = !(postgresProcessData.stream().anyMatch(processStatus -> !processStatus.getWalWriter()));
      boolean writer = !(postgresProcessData.stream().anyMatch(processStatus -> !processStatus.getWriter()));
      boolean autoVacuumWorker = !(postgresProcessData.stream().anyMatch(processStatus -> !processStatus.getAutoVacuumWorker()));

      return new PostgresProcessApiResponse(startTime, endTime, masterProcess, walWriter, writer, checkPointer, statisticsCollector, autoVacuumLauncher, autoVacuumWorker, backendProcess);
    } catch (Exception e) {
      return null;  // TODO: 例外処理
    }
  }
}
