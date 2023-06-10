package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.PostgresProcessApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.PostgresProcessApiResponse;
import jp.ac.databaseexplorer.common.component.csv.impl.ProcessStatusCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
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
  public PostgresProcessApiResponse getPostgresProcess(PostgresProcessApiRequest request) throws ApplicationException {
    try {

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }

      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();
      List<ProcessStatus> postgresProcessData = reader.read(startTime, endTime);

      //プロセス情報がない場合はエラー
      if(postgresProcessData.isEmpty()) {
        throw new ApplicationException("", "", new Exception());
      }

      boolean masterProcess = postgresProcessData.stream().allMatch(ProcessStatus::getMasterProcess);
      boolean backendProcess = postgresProcessData.stream().allMatch(ProcessStatus::getBackendProcess);
      boolean autoVacuumLauncher = postgresProcessData.stream().allMatch(ProcessStatus::getAutoVacuumLauncher);
      boolean checkPointer = postgresProcessData.stream().allMatch(ProcessStatus::getCheckPointer);
      boolean statisticsCollector = postgresProcessData.stream().allMatch(ProcessStatus::getStatisticsCollector);
      boolean walWriter = postgresProcessData.stream().allMatch(ProcessStatus::getWalWriter);
      boolean writer = postgresProcessData.stream().allMatch(ProcessStatus::getWriter);
      boolean autoVacuumWorker = postgresProcessData.stream().allMatch(ProcessStatus::getAutoVacuumWorker);

      return new PostgresProcessApiResponse(startTime, endTime, masterProcess, walWriter, writer, checkPointer, statisticsCollector, autoVacuumLauncher, autoVacuumWorker, backendProcess);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00016", "プロセス情報取得処理でシステムエラーが発生しました", se);
    } catch(ApplicationException ae) {
      throw new ApplicationException("APP-00020", "プロセス情報取得処理で業務エラーが発生しました", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00017", "プロセス情報取得処理で予期せぬエラーが発生しました", e);
    }
  }
}
