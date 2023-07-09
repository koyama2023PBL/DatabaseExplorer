package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWriterBase;
import org.springframework.stereotype.Component;

@Component
public class DeadlockCsvWriter extends CsvWriterBase {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/pg-stat-database-deadlock.csv";
  }

  @Override
  protected  String getHeader() {
    return "timestamp,dbName,deadlocks";
  }
}
