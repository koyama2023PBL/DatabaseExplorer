package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiResponse;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageData;
import jp.ac.databaseexplorer.common.component.csv.impl.SarCpuCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import lombok.NonNull;
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
  public CpuUsageApiResponse getCpuUsage(@NonNull CpuUsageApiRequest request) throws ApplicationException {
    try {
      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }

      CpuUsageData[] cpuUsageData = reader.read(startTime, endTime).stream()
          .map(record -> new CpuUsageData(record.getTimestamp(), 1.0 - record.getIdle()))
          .toArray(CpuUsageData[]::new);
      return new CpuUsageApiResponse(startTime, endTime, cpuUsageData);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00004", "CPU使用率取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00005", "CPU使用率取得処理で業務例外が発生しました。", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00006", "CPU使用率取得処理で予期せぬ例外が発生しました。", e);
    }
  }
}
