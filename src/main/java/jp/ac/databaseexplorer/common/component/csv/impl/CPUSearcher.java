package jp.ac.databaseexplorer.common.component.csv.impl;

import jp.ac.databaseexplorer.common.component.csv.base.StorageSearcherBase;
import org.springframework.stereotype.Component;


/**
 * CPUリソースについての収集を行うためのクラス.
 */
@Component
public class CPUSearcher extends StorageSearcherBase {

  public String testRead(){
    return "test OK!";
  }
}
