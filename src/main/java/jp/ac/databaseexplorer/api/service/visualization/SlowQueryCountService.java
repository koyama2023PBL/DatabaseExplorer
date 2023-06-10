package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.SlowQueryCountApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.SlowQueryCountApiResponse;
import jp.ac.databaseexplorer.common.component.csv.impl.QueryTimeCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import jp.ac.databaseexplorer.storage.visualization.QueryTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * スロークエリの数を取得するサービスクラス
 */
@Service
@AllArgsConstructor
public class SlowQueryCountService {

  private final QueryTimeCsvReader reader;

  /**
   * スロークエリの数を取得する
   */
  public SlowQueryCountApiResponse getSlowQueryCount(SlowQueryCountApiRequest request) throws ApplicationException {
    try{

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }

      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();
      List<QueryTime> queryTimes = reader.read(startTime, endTime);

      if(queryTimes.isEmpty()){
        throw new ApplicationException("","",new Exception());
      }

      int slowQueryNum = queryTimes.stream()
          .filter(queryTime -> queryTime.getQueryTime() >= request.getQueryTimeAtLeast())
          .collect(Collectors.groupingBy(QueryTime::getPid))
          .size();

      return new SlowQueryCountApiResponse(startTime, endTime, request.getQueryTimeAtLeast(), slowQueryNum);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00008", "スロークエリ数取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00009", "スロークエリ数取得処理で業務例外が発生しました。", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00010", "スロークエリ数取得処理で予期せぬ例外が発生しました。", e);
    }
  }
}
