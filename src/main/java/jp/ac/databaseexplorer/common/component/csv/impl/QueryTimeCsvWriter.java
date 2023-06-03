package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWriterBase;

public class QueryTimeCsvWriter extends CsvWriterBase {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/pg-stat-statements-query-time.csv";
  }

  @Override
  protected  String getHeader() {
    return "timestamp,pid,queryTime";
  }
}
