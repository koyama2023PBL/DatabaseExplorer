package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.*;
import jp.ac.databaseexplorer.common.component.csv.impl.DeadTupCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * デッドタプル数と割合を取得するサービスクラス
 */
@Service
@RequiredArgsConstructor
public class DeadTupService {

  /**
   * Reader
   */
  private final DeadTupCsvReader reader;

  /**
   * デッドタプル数と割合を取得する
   */
  public DeadTupApiResponse getDeadTup(@NonNull DeadTupApiRequest request) throws ApplicationException {
    try {
      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }

      DeadTupData[] deadTupData = reader.read(startTime, endTime).stream()
          .map(record -> new DeadTupData(record.getTimestamp(), record.getDeadTupCount(), record.getDeadTupRatio()))
          .toArray(DeadTupData[]::new);
      return new DeadTupApiResponse(startTime, endTime, deadTupData);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00038", "デッドタプル情報取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00039", "デッドタプル情報取得処理で業務例外が発生しました", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00040", "デッドタプル情報取得処理で予期せぬ例外が発生しました", e);
    }
  }
}
