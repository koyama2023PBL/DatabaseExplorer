package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWriterBase;
import org.springframework.stereotype.Component;

@Component
public class ProcessStatusCsvWriter extends CsvWriterBase {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/process-status.csv";
  }

  @Override
  protected  String getHeader() {
    return "timestamp,master,checkPointer,autoVacuumLauncher,walWriter,writer,statisticsCollector,autoVacuumWorker";
  }
}
