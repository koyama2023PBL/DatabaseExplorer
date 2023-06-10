package jp.ac.databaseexplorer.async.job.visualization;
import jp.ac.databaseexplorer.async.repository.CacheHitRepository;
import jp.ac.databaseexplorer.common.component.csv.impl.CacheHitCsvWriter;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.storage.visualization.CacheHit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheHitJob extends VisualizeJobBase {

  /**
   * Logger
   */
  private static final Logger systemLogger =  LoggerFactory.getLogger("SYSTEM_LOG");


  /**
   * キャッシュヒット率の取得のJPAのクエリメソッドを提供するリポジトリ
   */
  private final CacheHitRepository cacheHitRepository;

  /**
   * CSVファイルに書き込みのコンポーネント
   */
  @Autowired
  private CacheHitCsvWriter cacheHitCsvWriter;

  /**
   * キャッシュヒット率を収集する。
   */
  @Override
  public void execute() throws ApplicationException {
    try{
      Date now = new Date();
      List<CacheHit> entities = cacheHitRepository.findAllWithTime();
      for (CacheHit element : entities) {
        element.setTimestamp(now);
        cacheHitCsvWriter.write(element);
      }
    }
    catch (Exception ex){
      systemLogger.error("プロセス情報取得処理で予期せぬエラーが発生しました");
      throw new ApplicationException("APP-00017", "プロセス情報取得処理で予期せぬエラーが発生しました", ex);
    }
  }
}
