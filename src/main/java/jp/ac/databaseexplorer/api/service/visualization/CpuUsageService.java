package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiResponse;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageData;
import jp.ac.databaseexplorer.common.component.csv.impl.SarCpuCsvReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * CPU使用率を取得するサービスクラス
 */
@Service
@RequiredArgsConstructor
public class CpuUsageService {

  /**
   * Reader
   */
  private final SarCpuCsvReader reader;

  /**
   * CPU使用率を取得する
   */
  public CpuUsageApiResponse getCpuUsage(CpuUsageApiRequest request) {
    try {
      Date startTime = request.getStartTime();
      Date endTime   = request.getEndTime();
      CpuUsageData[] cpuUsageData = reader.read(startTime, endTime).stream()
          .map(record -> new CpuUsageData(record.getTimestamp(), 1.0 - record.getIdle()))
          .toArray(CpuUsageData[]::new);
      return new CpuUsageApiResponse(startTime, endTime, cpuUsageData);
    } catch (Exception e) {
      return null;  // TODO: 例外処理
    }
  }
}
