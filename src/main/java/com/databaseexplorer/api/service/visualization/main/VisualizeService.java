package com.databaseexplorer.api.service.visualization.main;

import com.databaseexplorer.api.service.component.storage.impl.CPUSearcher;
import com.databaseexplorer.api.service.visualization.businesslogic.VisualizationBusinessLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 可視化サービスクラス。
 * ストレージのCSVファイルに記録された情報をもとに、可視化すべき情報を返却するクラス。
 */
@Component
@RequiredArgsConstructor
public class VisualizeService {

  private final VisualizationBusinessLogic logic;
  private final CPUSearcher cpuSearcher;

  public void hello(){
  }

  public String testCSV(){
    return cpuSearcher.testRead();
  }

}
