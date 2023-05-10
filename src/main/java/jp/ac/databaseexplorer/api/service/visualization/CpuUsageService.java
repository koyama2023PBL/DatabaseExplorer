package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.common.component.csv.impl.CPUSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CPU使用率を取得するサービスクラスです。
 */
@Service
@RequiredArgsConstructor
public class CpuUsageService {

  private final CPUSearcher cpuSearcher;

  public String testCSV() {
    return cpuSearcher.testRead();
  }

}
