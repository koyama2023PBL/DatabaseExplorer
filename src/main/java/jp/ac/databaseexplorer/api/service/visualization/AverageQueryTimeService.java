package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.AverageQueryTimeApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.AverageQueryTimeApiResponse;
import jp.ac.databaseexplorer.common.component.csv.impl.AvgExecTimeCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import jp.ac.databaseexplorer.storage.visualization.AvgExecTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;/**
 * データベースから取得したクエリ実行時間の平均を取得するサービスクラス
 */
@Service
@AllArgsConstructor
public class AverageQueryTimeService {

  /**
   * Reader
   */
  private final AvgExecTimeCsvReader reader;

  /**
   * クエリの平均実行時間を取得する
   */
  public AverageQueryTimeApiResponse getAverageQueryTime(AverageQueryTimeApiRequest request)throws ApplicationException {
    try{

      //TODO サービスクラスへのインプット項目チェック処理
      //if (Objects.nonNull(request)) {throw new ApplicationException("","",new Exception());}

      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();
      List<AvgExecTime> execTimes = reader.read(startTime, endTime).stream().filter(x -> Objects.equals(x.getKind(), request.getQueryKind())).toList();
      Double totalExecTime = execTimes.stream().mapToDouble(AvgExecTime::getTotalExecTime).max().orElse(0) - execTimes.stream().mapToDouble(AvgExecTime::getTotalExecTime).min().orElse(0);
      Double totalExecCount = execTimes.stream().mapToDouble(AvgExecTime::getCalls).max().orElse(0) - execTimes.stream().mapToDouble(AvgExecTime::getCalls).min().orElse(0);
      Double averageExecTime = -1D; //Countが0の場合には-1を返す
      if(totalExecCount != 0){
        averageExecTime = totalExecTime / totalExecCount;
      }

      return new AverageQueryTimeApiResponse(startTime, endTime, request.getQueryKind(), averageExecTime);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00004", "CPU使用率取得処理でシステム例外が発生しました", se);
//    } catch (ApplicationException ae) {
//      throw new ApplicationException("APP-00005", "CPU使用率取得処理で業務例外が発生しました。", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00006", "CPU使用率取得処理で予期せぬ例外が発生しました。", e);
    }
  }
}
