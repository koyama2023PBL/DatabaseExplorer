package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWithTimestampReaderBase;
import jp.ac.databaseexplorer.storage.visualization.SarCpu;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * sarコマンドで取得したCPU使用率のCSVファイルを読み込むクラス
 */
@Component
public class SarCpuCsvReader extends CsvWithTimestampReaderBase<SarCpu> {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/sar-cpu.csv";
  }

  @Override
  protected Class<SarCpu> modelClass() {
    return SarCpu.class;
  }
}
