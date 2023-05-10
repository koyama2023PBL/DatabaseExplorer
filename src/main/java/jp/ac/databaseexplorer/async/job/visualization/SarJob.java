package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.async.job.AbstractJob;
import org.springframework.stereotype.Service;

/**
 * sarコマンドでCPUのシステム統計情報を収集します。
 */
@Service
public class SarJob extends AbstractJob {

  @Override
  public void execute() {
    // ...
  }
}
