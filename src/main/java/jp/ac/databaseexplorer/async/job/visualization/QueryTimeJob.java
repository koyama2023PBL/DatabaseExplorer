package jp.ac.databaseexplorer.async.job.visualization;

import jp.ac.databaseexplorer.async.repository.QueryTimeRepository;
import jp.ac.databaseexplorer.common.component.csv.impl.QueryTimeCsvWriter;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.storage.visualization.QueryTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryTimeJob extends VisualizeJobBase {

  /**
   * Logger
   */
  private static final Logger systemLogger =  LoggerFactory.getLogger("SYSTEM_LOG");


  /**
   * クエリ実行時間を取得のJPAのクエリメソッドを提供するリポジトリ
   */
  private final QueryTimeRepository QueryTimeRepository;

  /**
   * CSVファイルに書き込みのコンポーネント
   */
  @Autowired
  private QueryTimeCsvWriter queryTimeCsvWriter;

  /**
   * クエリ実行時間を収集する。
   */
  @Override
  public void execute() throws ApplicationException {
    try{
      Date now = new Date();
      List<QueryTime> entities = QueryTimeRepository.getQueryTime();
      for (QueryTime element : entities) {
        element.setTimestamp(now);
        queryTimeCsvWriter.write(element);
      }
    }
    catch (Exception ex){
      systemLogger.error("クエリ実行時間の取得にはエラーが発生しました");
      throw new ApplicationException("APP-00029", "クエリ実行時間の取得にはエラーが発生しました", ex);
    }
  }
}

