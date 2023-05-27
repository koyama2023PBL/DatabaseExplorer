package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.common.component.os.SshUtil;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

  /**
   * CPUのシステム統計情報を収集します。
   */
  @Override
  public void execute() throws ApplicationException {
    systemLogger.info("SarCpuJob#execute() called.");
    System.out.println(ssh.execute("ls"));
  }
}
