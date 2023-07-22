package jp.ac.databaseexplorer.api.service.infomation;


import jp.ac.databaseexplorer.api.model.visualization.AverageQueryTimeApiResponse;
import jp.ac.databaseexplorer.storage.visualization.AvgExecTime;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.ac.databaseexplorer.api.model.information.DbInfomationApiResponse;
import jp.ac.databaseexplorer.common.component.csv.impl.AvgExecTimeCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class DbInfomationService {


  @Autowired
  private final JdbcTemplate jdbcTemplate;

  /**
   * ヘルスチェック
   * @return boolean
   * @throws ApplicationException
   */
  public boolean healthCheck() throws ApplicationException {
    try {

      String healthCheckQuery = "select now()";
      jdbcTemplate.query(healthCheckQuery, (rs, rowNum) -> rs.getString("now"));

      //ここまで辿り着けたらOK
      return true;

    } catch (SystemException se) {
      throw new ApplicationException("APP-00047", "DBヘルスチェック処理でシステム例外が発生しました", se);
    } catch (Exception e) {
      throw new ApplicationException("APP-00048", "DBヘルスチェック処理で予期せぬ例外が発生しました", e);
    }
  }

  /**
   * DB情報の取得
   * @return DbInfomationApiResponse
   * @throws ApplicationException
   */
  public DbInfomationApiResponse getInfomation() throws ApplicationException {
    try {

      String workMemSql = "select version();";
      List<String> versionList = jdbcTemplate.query(workMemSql, (rs, rowNum) -> rs.getString("version"));
      if (versionList.size() != 1) {
        throw new ApplicationException("", "", new Exception());
      }

      return new DbInfomationApiResponse(versionList.get(0));

    } catch (SystemException se) {
      throw new ApplicationException("APP-00044", "DB情報取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00045", "DB情報取得処理で業務例外が発生しました", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00046", "DB情報取得処理で予期せぬ例外が発生しました", e);
    }
  }
}
