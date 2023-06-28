package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.common.component.csv.impl.ProcessMemCsvWriter;
import jp.ac.databaseexplorer.common.component.os.SshUtil;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.storage.visualization.ProcessMem;
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
public class ProcessMemJob extends VisualizeJobBase {

  /**
   * Logger
   */
  private static final Logger systemLogger =  LoggerFactory.getLogger("SYSTEM_LOG");

  /**
   * Bean
   */
  private final SshUtil ssh;

  //プロセスメモリの使用状況の書き込み処理をここで書く
  @Autowired
  private final ProcessMemCsvWriter processMemCsvWriter;

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  /**
   * プロセスメモリの使用状況を収集します。
   */
  @Override
  public void execute() throws ApplicationException {

    try {
      //プロセスメモリの使用状況を取得する
      String result = ssh.execute("ps aux | grep postgres | awk '{print $1, $6, $4}'");
      //実行中のクエリ数を取得する
      String sql = "SELECT COUNT(state = 'active' OR NULL) connections FROM pg_stat_activity;";
      List<Integer> connectionNums = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("connections"));
      Date now = new Date();

      //メモリ使用率と使用量を合計するための変数
      Integer memUsageSum = 0;
      Double memUsageRatioSum = 0.0;

      //プロセスメモリの使用状況（プロセスごと）を合計する
      String[] lines = result.split("\n");
      for (String line : lines) {
          String[] data = line.split("\\s+");
          memUsageSum += Integer.parseInt(data[1]);
          memUsageRatioSum += Double.parseDouble(data[2]);
      }

      ProcessMem processMem = new ProcessMem();
      processMem.setTimestamp(now);
      processMem.setMemUsage(memUsageSum);
      processMem.setMemUsageRatio(memUsageRatioSum);
      processMem.setConnections(connectionNums.get(0));
      processMemCsvWriter.write(processMem);
    } catch (Exception ex) {
      systemLogger.error("プロセスメモリ使用率取得の非同期処理でエラーが発生しました");
      throw new ApplicationException("APP-00031", "プロセスメモリ使用率取得の非同期処理でエラーが発生しました", ex);
    }
  }
}
