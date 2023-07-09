package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.common.component.csv.impl.ProcessStatusCsvWriter;
import jp.ac.databaseexplorer.common.component.os.SshUtil;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.storage.visualization.SarCpu;
import jp.ac.databaseexplorer.storage.visualization.ProcessStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProcessStatusJob extends VisualizeJobBase {
  /**
   * Logger
   */
  private static final Logger systemLogger =  LoggerFactory.getLogger("SYSTEM_LOG");

  /**
   * プロセスの生存状態を取得のJPAのクエリメソッドを提供するリポジトリ
   */
  private final jp.ac.databaseexplorer.async.repository.ProcessStatusRepository Repository;

  /**
   * Bean
   */
  private final SshUtil ssh;

  //CSVへの書き込み処理をここで書く
  @Autowired
  private final ProcessStatusCsvWriter CsvWriter;

  /**
   * プロセスの生存状態を収集します。
   */
  @Override
  public void execute() throws ApplicationException {
    ProcessStatus processStatus = new ProcessStatus();
    Date now = new Date();
    processStatus.setTimestamp(now);
    try {
      int res = Repository.selectOne();
      if(res == 1) {
        processStatus.setMaster("1");
      }
      String result = ssh.execute("ps -aux | grep postgres");

      String[] lines = result.split("\n");
      for (String line : lines) {
        if (line.contains("checkpointer")) {
          processStatus.setCheckPointer("1");
        } else if (line.contains("autovacuum launcher")) {
          processStatus.setAutoVacuumLauncher("1");
        } else if (line.contains("walwriter")) {
          processStatus.setWalWriter("1");
        } else if (line.contains("writer")) {
          processStatus.setWriter("1");
        } else if (line.contains("stats collector")) {
          processStatus.setStatisticsCollector("1");
        } else if (line.contains("autovacuum worker")) {
          processStatus.setAutoVacuumWorker("1");
        }
      }
      CsvWriter.write(processStatus);
    }catch (DataAccessException ex){
      processStatus.setMaster("0");
      processStatus.setCheckPointer("0");
      processStatus.setAutoVacuumLauncher("0");
      processStatus.setWalWriter("0");
      processStatus.setWriter("0");
      processStatus.setStatisticsCollector("0");
      processStatus.setAutoVacuumWorker("0");
      CsvWriter.write(processStatus);
    }
    catch (Exception ex) {
      systemLogger.error("PostgreSQLのプロセスの生存報取得の非同期処理でエラーが発生しました");
      throw new ApplicationException("APP-00034", "PostgreSQLのプロセスの生存報取得の非同期処理でエラーが発生しました", ex);
    }
  }
}
