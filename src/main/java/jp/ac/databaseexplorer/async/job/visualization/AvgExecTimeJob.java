package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.common.component.csv.impl.AvgExecTimeCsvWriter;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.storage.visualization.AvgExecTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * sarコマンドでCPUのシステム統計情報を収集します。
 */
@Service
@RequiredArgsConstructor
public class AvgExecTimeJob extends VisualizeJobBase {

  /**
   * Logger
   */
  private static final Logger systemLogger = LoggerFactory.getLogger("SYSTEM_LOG");

  //CSVへの書き込み用コンポーネント
  private final AvgExecTimeCsvWriter avgExecTimeCsvWriter;


  /**
   * クエリ合計実行時間取得のJPAのクエリメソッドを提供するリポジトリ
   */
  private final jp.ac.databaseexplorer.async.repository.AvgExecTimeRepository avgExecTimeRepository;

  /**
   * クエリの合計実行時間を収集する。
   */
  @Override
  public void execute() throws ApplicationException {

    try {
      Date now = new Date();
      List<AvgExecTime> entities = avgExecTimeRepository.getAvgExecTime();
      for (AvgExecTime element : entities) {
        if (element == null) {
          continue;
        }
        element.setTimestamp(now);
        avgExecTimeCsvWriter.write(element);
      }

    } catch (Exception ex) {
      systemLogger.error("クエリの合計実行時間取得でエラーが発生しました");
      throw new ApplicationException("APP-00030", "クエリ平均実行時間取得の非同期処理でエラーが発生しました", ex);
    }

  }
}