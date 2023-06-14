package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.common.component.csv.impl.SarCpuCsvWriter;
import jp.ac.databaseexplorer.common.component.os.SshUtil;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.storage.visualization.SarCpu;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.time.ZonedDateTime;
import java.time.ZoneId;

/**
 * sarコマンドでCPUのシステム統計情報を収集します。
 */
@Service
@RequiredArgsConstructor
public class SarCpuJob extends VisualizeJobBase {

  /**
   * Logger
   */
  private static final Logger systemLogger =  LoggerFactory.getLogger("SYSTEM_LOG");

  /**
   * Bean
   */
  private final SshUtil ssh;

  //CSVへの書き込み処理をここで書く
  @Autowired
  private final SarCpuCsvWriter sarCpuCsvWriter;

  /**
   * CPUのシステム統計情報を収集します。
   */
  @Override
  public void execute() throws ApplicationException {
    systemLogger.info("SarCpuJob#execute() called.");
    try {
      String result = ssh.execute("sar -u 1 1");
      ZonedDateTime nowInTokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
      Date date = Date.from(nowInTokyo.toInstant());

      String[] lines = result.split("\n");
      for (String line : lines) {
        if (line.startsWith("Average")) {
          String[] data = line.split("\\s+");
          SarCpu sarCpu = new SarCpu();
          sarCpu.setTimestamp(date);
          sarCpu.setCpuUser(Double.parseDouble(data[2]));
          sarCpu.setCpuNice(Double.parseDouble(data[3]));
          sarCpu.setCpuSystem(Double.parseDouble(data[4]));
          sarCpu.setCpuIowait(Double.parseDouble(data[5]));
          sarCpu.setCpuSteal(Double.parseDouble(data[6]));
          sarCpu.setCpuIdle(Double.parseDouble(data[7]));
          sarCpuCsvWriter.write(sarCpu);
        }
      }
    } catch (Exception ex) {
      systemLogger.error("CPUのシステム統計情報の取得でエラーが発生しました");
      throw new ApplicationException("APP-00028", "CPUのシステム統計情報の取得でエラーが発生しました", ex);
    }
    //systemLogger.info(ssh.execute("sar -u 1 1"));
    //System.out.println(ssh.execute("ls"));
  }
}
