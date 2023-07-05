package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.common.component.csv.impl.DeadTupCsvWriter;
import jp.ac.databaseexplorer.common.component.os.SshUtil;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.storage.visualization.DeadTup;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * プロセスメモリの使用状況を収集します。
 */
@Service
@RequiredArgsConstructor
public class DeadTupJob extends VisualizeJobBase {

  /**
   * Logger
   */
  private static final Logger systemLogger = LoggerFactory.getLogger("SYSTEM_LOG");

  /**
   * Bean
   */

  //プロセスメモリの使用状況の書き込み処理をここで書く
  @Autowired
  private final DeadTupCsvWriter deadTupCsvWriter;

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  /**
   * デッドタプルの状況を収集します。
   */
  @Override
  public void execute() throws ApplicationException {

    try {

      //デッドタプル数と割合を取得する
      String sql = "SELECT " +
          "SUM(n_dead_tup) dead_tup_count, " +
          "TRUNC(SUM(n_dead_tup)::NUMERIC / (SUM(n_dead_tup + n_live_tup) + 1) * 100, 1) dead_tup_ratio " +
          "FROM pg_stat_user_tables;";
      List<Integer> deadTupCount = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("dead_tup_count"));
      List<Double> deadTupRatio = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getDouble("dead_tup_count"));
      Date now = new Date();



      DeadTup deadTup = new DeadTup();
      deadTup.setTimestamp(now);
      deadTup.setDeadTupCount(deadTupCount.get(0));
      deadTup.setDeadTupRatio(deadTupRatio.get(0));
      deadTupCsvWriter.write(deadTup);
    } catch (Exception ex) {
      systemLogger.error("デッドタプル数・割合取得の非同期処理でエラーが発生しました");
      throw new ApplicationException("APP-00032", "デッドタプル数・割合取得の非同期処理でエラーが発生しました", ex);
    }
  }
}
