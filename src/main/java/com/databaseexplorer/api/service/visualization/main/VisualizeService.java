package com.databaseexplorer.api.service.visualization.main;

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

  public void hello(){
  }

}
