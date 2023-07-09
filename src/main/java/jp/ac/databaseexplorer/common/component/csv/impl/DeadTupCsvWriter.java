package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWriterBase;
import org.springframework.stereotype.Component;

@Component
public class DeadTupCsvWriter extends CsvWriterBase {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/dead-tup.csv";
  }

  @Override
  protected  String getHeader() {
    return "timestamp,deadTupCount,deadTupRatio";
  }
}
