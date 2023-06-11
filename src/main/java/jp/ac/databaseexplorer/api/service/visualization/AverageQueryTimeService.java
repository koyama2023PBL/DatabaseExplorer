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

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }

      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();
      List<AvgExecTime> execTimes = reader.read(startTime, endTime).stream().filter(x -> Objects.equals(x.getKind(), request.getQueryKind())).toList();

      Double averageExecTime = -1D; //Countが0の場合には-1を返す

      if(execTimes.size() == 0){
        averageExecTime = -1.0;
      }else if(execTimes.size() == 1){
        averageExecTime = execTimes.get(0).getTotalExecTime() / execTimes.get(0).getCalls();
      }else{
        Double totalExecTime = execTimes.stream().mapToDouble(AvgExecTime::getTotalExecTime).max().orElse(0) - execTimes.stream().mapToDouble(AvgExecTime::getTotalExecTime).min().orElse(0);
        Double totalExecCount = execTimes.stream().mapToDouble(AvgExecTime::getCalls).max().orElse(0) - execTimes.stream().mapToDouble(AvgExecTime::getCalls).min().orElse(0);

        if(totalExecCount != 0){
          averageExecTime = totalExecTime / totalExecCount;
        }
      }

      return new AverageQueryTimeApiResponse(startTime, endTime, request.getQueryKind(), averageExecTime);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00025", "クエリ平均実行時間取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00026", "クエリ平均実行時間取得処理で業務例外が発生しました。", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00027", "クエリ平均実行時間取得処理で予期せぬ例外が発生しました。", e);
    }
  }
}
