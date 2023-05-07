package com.databaseexplorer.api.service.component.storage.impl;

import com.databaseexplorer.api.service.component.storage.base.StorageSearcherBase;
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
