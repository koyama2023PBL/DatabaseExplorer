package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWithTimestampReaderBase;
import jp.ac.databaseexplorer.storage.visualization.ProcessMem;
import org.springframework.stereotype.Component;

/**
 * sarコマンドで取得したCPU使用率のCSVファイルを読み込むクラス
 */
@Component
public class ProcessMemCsvReader extends CsvWithTimestampReaderBase<ProcessMem> {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/process-mem.csv";
  }

  @Override
  protected Class<ProcessMem> modelClass() {
    return ProcessMem.class;
  }
}
