package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.DeadlockCountApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.DeadlockCountApiResponse;
import jp.ac.databaseexplorer.common.component.csv.impl.DeadlockCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;
import jp.ac.databaseexplorer.storage.visualization.Deadlock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * デッドロック発生回数を取得するサービスクラス
 */
@Service
@AllArgsConstructor
public class DeadlockCountService {

  private final DeadlockCsvReader reader;

  /**
   * デッドロック発生回数を取得する
   */
  public DeadlockCountApiResponse getDeadlockCount(DeadlockCountApiRequest request) throws ApplicationException {
    try {

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }

      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();
      List<Deadlock> deadlocks = reader.read(startTime, endTime).stream().filter(x -> x.getDbName().equals(request.getDatabaseName())).toList();

      Long deadlockCount;

      if (deadlocks.size() == 1) {
        deadlockCount = deadlocks.stream().mapToLong(Deadlock::getDeadlocks).findFirst().orElse(0);
      } else if (deadlocks.size() > 1) {
        deadlockCount = deadlocks.stream().max(Comparator.comparing(CsvWithTimestampModelBase::getTimestamp)).get().getDeadlocks()
            - deadlocks.stream().min(Comparator.comparing(CsvWithTimestampModelBase::getTimestamp)).get().getDeadlocks();
      } else {
        deadlockCount = -1L; //デッドロックがゼロの場合とデータがない場合を区別するために-1を返す
      }


      return new DeadlockCountApiResponse(startTime, endTime, request.getDatabaseName(), deadlockCount);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00041", "デッドロック発生回数取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00042", "デッドロック発生回数取得処理で業務例外が発生しました", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00043", "デッドロック発生回数取得処理で予期せぬ例外が発生しました", e);
    }
  }
}
