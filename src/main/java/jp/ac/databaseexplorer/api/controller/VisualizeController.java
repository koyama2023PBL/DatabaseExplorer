package jp.ac.databaseexplorer.api.controller;

import jp.ac.databaseexplorer.api.model.visualization.*;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.api.service.visualization.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Visualization層のAPIリクエストを受け付けるためAPIコントローラクラス.
 */
@RestController
@RequestMapping(value = "/database-explorer/api/visualization")
@RequiredArgsConstructor
public class VisualizeController {

  /**
   * Loggers
   */
  public static final Logger systemLogger =  LoggerFactory.getLogger("SYSTEM_LOG");
  public static final Logger errorLogger =  LoggerFactory.getLogger("ERROR_LOG");
  public static final Logger operationLogger =  LoggerFactory.getLogger("OPERATION_LOG");
  
  private static final String STRING_TO_DATE = "yyyyMMddHHmmss";

  /**
   * CPU使用率を取得するサービス
   */
  private final CpuUsageService cpuUsageService;

  /**
   * postgresのプロセス情報を取得するサービス
   */
  private final PostgresProcessService postgresProcessService;

  /**
   * クエリの平均実行時間を取得するサービス
   */
  private final AverageQueryTimeService averageQueryTimeService;

  /**
   * キャッシュヒット率を取得するサービス
   */
  private final CacheHitRateService cacheHitRateService;

  /**
   * スロークエリの数を取得するサービス
   */
  private final SlowQueryCountService slowQueryCountService;


  /**
   * CPU使用率を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @return CpuUsageApiResponse
   */
  @GetMapping
  @RequestMapping(value = "/cpu-usage", method = RequestMethod.GET)
  public ResponseEntity<?> cpuUsage( @RequestParam("starttime")String startTime,@RequestParam("endtime")String endTime) {
    try {
      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      CpuUsageApiRequest request = new CpuUsageApiRequest(startTimeDate, endTimeDate);
      CpuUsageApiResponse response = cpuUsageService.getCpuUsage(request);

      // ダミーのレスポンスを作成する --START
      // サービスの実装が完了次第、作成する予定
      SimpleDateFormat dummyDtFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      CpuUsageData[] cpuUsageDatas = new CpuUsageData[]{
        new CpuUsageData(dummyDtFt.parse("2023/05/07 18:00:00"), 20.98),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 18:10:00"), 30.98),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 18:20:00"), 40.98),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 18:30:00"), 20.88),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 18:40:00"), 20.68),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 18:50:00"), 25.98),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 19:00:00"), 20.98),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 19:10:00"), 20.98),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 19:20:00"), 30.98),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 19:30:00"), 20.98),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 19:40:00"), 70.68),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 19:50:00"), 10.28),
        new CpuUsageData(dummyDtFt.parse("2023/05/07 20:00:00"), 20.98)
      };
      response = new CpuUsageApiResponse(startTimeDate, endTimeDate, cpuUsageDatas);
      // ダミーのレスポンスを作成する --END

      return ResponseEntity.ok(response);
      }
    catch (ParseException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * CPU使用率を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @return HttpResponse
   */
  @GetMapping
  @RequestMapping(value = "/cpu-usagewithexception", method = RequestMethod.GET)
  public ResponseEntity<?> cpuUsageWithException( @RequestParam("starttime")String startTime,@RequestParam("endtime")String endTime) {
    try {

      // TODO APIリクエスト時の処理 リクエスト項目をJSON化して出力する？
      systemLogger.info("cpuUsageWithExceptionAPI start: " + startTime + endTime);

      SimpleDateFormat dtFt = new SimpleDateFormat("yyyyMMddHHmmss");
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      CpuUsageApiRequest request = new CpuUsageApiRequest(startTimeDate, endTimeDate);
      CpuUsageApiResponse response = cpuUsageService.getCpuUsage(request);

      // ダミーのレスポンスを作成する --START
      SimpleDateFormat dummyDtFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      CpuUsageData[] cpuUsageDatas = new CpuUsageData[]{
          new CpuUsageData(dummyDtFt.parse("2023/05/07 18:00:00"), 20.98),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 18:10:00"), 30.98),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 18:20:00"), 40.98),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 18:30:00"), 20.88),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 18:40:00"), 20.68),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 18:50:00"), 25.98),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 19:00:00"), 20.98),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 19:10:00"), 20.98),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 19:20:00"), 30.98),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 19:30:00"), 20.98),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 19:40:00"), 70.68),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 19:50:00"), 10.28),
          new CpuUsageData(dummyDtFt.parse("2023/05/07 20:00:00"), 20.98)
      };
      response = new CpuUsageApiResponse(startTimeDate, endTimeDate, cpuUsageDatas);
      // ダミーのレスポンスを作成する --END

      // TODO APIリクエスト時の処理 リクエスト項目をJSON化して出力する？
      systemLogger.info("cpuUsageWithExceptionAPI end: " + startTime + endTime);
      // TODO オペレーションログの出力内容決定
      operationLogger.info("cpuUsageWithExceptionAPI operationTime:" + "1.2345 sec");

      return ResponseEntity.ok(response);
    } catch (ParseException pe) {
      // TODO リクエストの単体チェック（Filterなどの設定する？）
      errorLogger.error("cpuUsageWithException:ParseException",pe);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (ApplicationException ae){
      errorLogger.error("cpuUsageWithException:ApplicationException",ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      errorLogger.error("cpuUsageWithException:Exception",e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * postgresのプロセス情報を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @return PostgresProcessApiResponse
   */
  @GetMapping
  @RequestMapping(value = "/processes", method = RequestMethod.GET)
  public ResponseEntity<?> processes(@RequestParam("starttime")String startTime,@RequestParam("endtime")String endTime) {
    try {
      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      PostgresProcessApiRequest request = new PostgresProcessApiRequest(startTimeDate, endTimeDate);
      PostgresProcessApiResponse response = postgresProcessService.getPostgresProcess(request);

      // ダミーのレスポンスを作成する --START
      // サービスの実装が完了次第、作成する予定
      response = new PostgresProcessApiResponse(startTimeDate, endTimeDate, true,false,
       true,false,true,true,false,true);
      // ダミーのレスポンスを作成する --END

      return ResponseEntity.ok(response);
    }
    catch (ParseException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch(Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * クエリの平均実行時間を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @param kind SQL文の種類（1-select,2-update,3-delete,4-insert）
   * @return AverageQueryTimeApiResponse
   */
  @GetMapping
  @RequestMapping(value = "/average-query-time", method = RequestMethod.GET)
  public ResponseEntity<?> averageQueryTime(
      @RequestParam("starttime")String startTime,
      @RequestParam("endtime")String endTime,
      @RequestParam("kind")Short kind) {
    try {
      if(kind != 1 && kind != 2 && kind != 3 && kind != 4) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      AverageQueryTimeApiRequest request = new AverageQueryTimeApiRequest(startTimeDate, endTimeDate, kind);
      AverageQueryTimeApiResponse response = averageQueryTimeService.getAverageQueryTime(request);

      // ダミーのレスポンスを作成する --START
      // サービスの実装が完了次第、作成する予定
      response = new AverageQueryTimeApiResponse(startTimeDate, endTimeDate, kind, 1.33);
      // ダミーのレスポンスを作成する --END

      return ResponseEntity.ok(response);
    }
    catch (ParseException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch(Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * キャッシュヒット率を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @param dbname データベース名
   * @return CacheHitRateApiResponse
   */
  @GetMapping
  @RequestMapping(value = "/hit-rate", method = RequestMethod.GET)
  public ResponseEntity<?> hitRate(
      @RequestParam("starttime")String startTime,
      @RequestParam("endtime")String endTime,
      @RequestParam("dbname")String dbname) {
    try {
      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      CacheHitRateApiRequest request = new CacheHitRateApiRequest(startTimeDate, endTimeDate, dbname);
      CacheHitRateApiResponse response = cacheHitRateService.getCacheHitRate(request);

      // ダミーのレスポンスを作成する --START
      // サービスの実装が完了次第、作成する予定
      response = new CacheHitRateApiResponse(startTimeDate, endTimeDate, "databasename", 0.25);
      // ダミーのレスポンスを作成する --END

      return ResponseEntity.ok(response);
    }
    catch (ParseException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch(Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * スロークエリの数を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @param queryTime クエリ実行時間の閾値、この値以上のクエリをスロークエリとしてカウントする
   * @return SlowQueryCountApiResponse
   */
  @GetMapping
  @RequestMapping(value = "/slow-query-counts", method = RequestMethod.GET)
  public ResponseEntity<?> slowQueryCounts(
      @RequestParam("starttime")String startTime,
      @RequestParam("endtime")String endTime,
      @RequestParam("querytime")Double queryTime) {
    try {
      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      SlowQueryCountApiRequest request = new SlowQueryCountApiRequest(startTimeDate, endTimeDate, queryTime);
      SlowQueryCountApiResponse response = slowQueryCountService.getSlowQueryCount(request);

      // ダミーのレスポンスを作成する --START
      // サービスの実装が完了次第、作成する予定
      response = new SlowQueryCountApiResponse(startTimeDate, endTimeDate, 1.00, 3);
      // ダミーのレスポンスを作成する --END

      return ResponseEntity.ok(response);
    }
    catch (ParseException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch(Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
