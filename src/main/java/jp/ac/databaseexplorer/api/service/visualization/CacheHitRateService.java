package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.CacheHitRateApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CacheHitRateApiResponse;
import jp.ac.databaseexplorer.common.component.csv.impl.CacheHitCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import jp.ac.databaseexplorer.storage.visualization.CacheHit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * キャッシュヒット率を取得するサービスクラス
 */
@Service
@AllArgsConstructor
public class CacheHitRateService {

  private final CacheHitCsvReader reader;

  /**
   * キャッシュヒット率を取得する
   */
  public CacheHitRateApiResponse getCacheHitRate(CacheHitRateApiRequest request) throws ApplicationException {
    try {

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }

      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();
      List<CacheHit> cacheHits = reader.read(startTime, endTime).stream().filter(x -> x.getDbName().equals("explorer")).toList();

      Double numHit = 0D;
      Double numRead = 0D;

      if (cacheHits.size() == 1) {
        numHit = cacheHits.stream().mapToDouble(CacheHit::getHit).findFirst().orElse(0);
        numRead = cacheHits.stream().mapToDouble(CacheHit::getRead).findFirst().orElse(0);
      } else if (cacheHits.size() > 1) {
        numHit = cacheHits.stream().mapToDouble(CacheHit::getHit).max().orElse(0) - cacheHits.stream().mapToDouble(CacheHit::getHit).min().orElse(0);
        numRead = cacheHits.stream().mapToDouble(CacheHit::getRead).max().orElse(0) - cacheHits.stream().mapToDouble(CacheHit::getRead).min().orElse(0);
      }

      Double cacheHitRateValue = -1D; //要検討。Hitが０のときは０を返すでよい。該当時間内で1回もクエリが実行されていないときは-1を返すでよい？(nullを返すとマズいので）
      if (numRead != 0 || numHit != 0) {
        cacheHitRateValue = numHit / (numRead + numHit);
      }

      return new CacheHitRateApiResponse(startTime, endTime, request.getDatabaseName(), cacheHitRateValue);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00022", "キャッシュヒット率取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00023", "キャッシュヒット率取得処理で業務例外が発生しました。", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00024", "キャッシュヒット率取得処理で予期せぬ例外が発生しました。", e);
    }
  }
}
