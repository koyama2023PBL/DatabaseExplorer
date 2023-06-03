package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWriterBase;

public class CacheHitCsvWriter extends CsvWriterBase {
  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/pg-stat-database-cache.csv";
  }

  @Override
  protected  String getHeader() {
    return "timestamp,dbName,hit,read";
  }
}
