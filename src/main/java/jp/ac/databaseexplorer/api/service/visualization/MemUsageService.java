package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.*;
import jp.ac.databaseexplorer.common.component.csv.impl.ProcessMemCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * メモリ使用率を取得するサービスクラス
 */
@Service
@RequiredArgsConstructor
public class MemUsageService {

  /**
   * Reader
   */
  private final ProcessMemCsvReader reader;

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  /**
   * メモリ使用率を取得する
   */
  public MemUsageApiResponse getMemUsage(@NonNull MemUsageApiRequest request) throws ApplicationException {
    try {
      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }

      //CSVに保持されていないデータを取得
      //work_memに設定値（メガバイト）を取得
      String workMemSql = "SHOW work_mem;";
      List<String> workMems = jdbcTemplate.query(workMemSql, (rs, rowNum) -> rs.getString("work_mem"));
      Double workMem = Double.parseDouble(workMems.get(0).replaceAll("MB", ""));
      //max_connectionsの設定値を取得
      String maxConnectionsSql = "SHOW max_connections;";
      List<Integer> maxConnections = jdbcTemplate.query(maxConnectionsSql, (rs, rowNum) -> rs.getInt("max_connections"));

      MemUsageData[] memUsageData = reader.read(startTime, endTime).stream()
          .map(record -> new MemUsageData(record.getTimestamp(), record.getMemUsage(), record.getMemUsageRatio(), record.getConnections()))
          .toArray(MemUsageData[]::new);


      return new MemUsageApiResponse(startTime, endTime, workMem, maxConnections.get(0), memUsageData);
    } catch (SystemException se) {
      throw new ApplicationException("APP-00035", "プロセスメモリ使用率取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00036", "プロセスメモリ使用率取得処理で業務例外が発生しました", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00037", "プロセスメモリ使用率取得処理で予期せぬ例外が発生しました", e);
    }
  }
}
