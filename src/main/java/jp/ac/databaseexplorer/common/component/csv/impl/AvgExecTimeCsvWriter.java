package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWriterBase;
import org.springframework.stereotype.Component;

@Component
public class AvgExecTimeCsvWriter extends CsvWriterBase {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/pg-stat-statements-mean-exec-time.csv";
  }

  @Override
  protected  String getHeader() {
    return "timestamp,kind,calls,totalExecTime";
  }
}
