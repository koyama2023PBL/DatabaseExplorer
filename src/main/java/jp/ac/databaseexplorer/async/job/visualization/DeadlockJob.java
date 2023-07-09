package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.common.component.csv.impl.DeadlockCsvWriter;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.storage.visualization.Deadlock;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * デッドロックの回数を取得するジョブ
 */
@Service
@RequiredArgsConstructor
public class DeadlockJob extends VisualizeJobBase {

  /**
   * Logger
   */
  private static final Logger systemLogger = LoggerFactory.getLogger("SYSTEM_LOG");

  /**
   * Bean
   */

  @Autowired
  private final DeadlockCsvWriter deadlockCsvWriter;

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  /**
   * デッドロックの状況を収集します。
   */
  @Override
  public void execute() throws ApplicationException {

    try {

      //DB名とデッドロック数の組を取得する
      String sql = "SELECT datname, deadlocks FROM pg_stat_database;";
      List<Map<String, Object>> dbStates = jdbcTemplate.queryForList(sql);
      Date now = new Date();


      for(Map<String, Object> row: dbStates){
        //データを取り出す
        String dbName = (String) row.get("datname");
        Long deadlocks = (Long) row.get("deadlocks");

        //DB名が空の場合はスキップする
        if (dbName == null) {
          continue;
        }

        //データを書き込む
        Deadlock deadlock = new Deadlock();
        deadlock.setTimestamp(now);
        deadlock.setDbName(dbName);
        deadlock.setDeadlocks(deadlocks);
        deadlockCsvWriter.write(deadlock);
      }


    } catch (Exception ex) {
      systemLogger.error("デッドロック発生回数取得の非同期処理でエラーが発生しました");
      throw new ApplicationException("APP-00033", "デッドロック発生回数取得の非同期処理でエラーが発生しました", ex);
    }
  }
}
