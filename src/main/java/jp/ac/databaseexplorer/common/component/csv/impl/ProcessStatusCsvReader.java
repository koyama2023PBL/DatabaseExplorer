package jp.ac.databaseexplorer.common.component.csv.impl;


import jp.ac.databaseexplorer.common.component.csv.base.CsvWithTimestampReaderBase;
import jp.ac.databaseexplorer.storage.visualization.ProcessStatus;
import org.springframework.stereotype.Component;

/**
 * プロセスの状態を保存したCSVファイルを読み込むクラス
 */
@Component
public class ProcessStatusCsvReader extends CsvWithTimestampReaderBase<ProcessStatus> {
  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/process-status.csv";
  }

  @Override
  protected Class<ProcessStatus> modelClass() {
    return ProcessStatus.class;
  }
}
