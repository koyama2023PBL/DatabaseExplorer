package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.CsvWriterBase;
import org.springframework.stereotype.Component;

@Component
public class SarCpuCsvWriter extends CsvWriterBase {

  @Override
  protected String filePath() {
    return "src/main/resources/storage/visualization/sar-cpu.csv";
  }

  @Override
  protected  String getHeader() {
    return "timestamp,user,nice,system,iowait,steal,idle";
  }
}
