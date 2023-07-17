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
   * プロセスメモリ使用量を取得するサービス
   */
  private final MemUsageService memUsageService;

  /**
   * デッドタプル数と割合を取得するサービス
   */
  private final DeadTupService deadTupService;


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
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("cpuUsage start。 startTime:" + startTime + " endTime:" + endTime);

      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      CpuUsageApiRequest request = new CpuUsageApiRequest(startTimeDate, endTimeDate);
      CpuUsageApiResponse response = cpuUsageService.getCpuUsage(request);

      systemLogger.info("cpuUsage end。 Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("cpuUsage operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);
      }
    catch (ParseException pe) {
      errorLogger.error("cpuUsage:ParseException", pe);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (ApplicationException ae) {
      errorLogger.error("cpuUsage:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    catch (Exception e) {
      errorLogger.error("cpuUsage:Exception", e);
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
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("processes start。 startTime:" + startTime + " endTime:" + endTime);

      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      PostgresProcessApiRequest request = new PostgresProcessApiRequest(startTimeDate, endTimeDate);
      PostgresProcessApiResponse response = postgresProcessService.getPostgresProcess(request);

      systemLogger.info("processes end。 Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("processes operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);
    }
    catch (ParseException pe) {
      errorLogger.error("processes:ParseException", pe);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (ApplicationException ae) {
      errorLogger.error("processes:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    catch(Exception e) {
      errorLogger.error("processes:Exception", e);
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
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("averageQueryTime start。 startTime:" + startTime + " endTime:" + endTime);

      if(kind != 1 && kind != 2 && kind != 3 && kind != 4) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      AverageQueryTimeApiRequest request = new AverageQueryTimeApiRequest(startTimeDate, endTimeDate, kind);
      AverageQueryTimeApiResponse response = averageQueryTimeService.getAverageQueryTime(request);

      systemLogger.info("averageQueryTime end。 Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("averageQueryTime operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);
    }
    catch (ParseException pe) {
      errorLogger.error("averageQueryTime:ParseException", pe);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (ApplicationException ae) {
      errorLogger.error("averageQueryTime:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    catch(Exception e) {
      errorLogger.error("averageQueryTime:Exception", e);
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
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("hitRate start。 startTime:" + startTime + " endTime:" + endTime);

      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      CacheHitRateApiRequest request = new CacheHitRateApiRequest(startTimeDate, endTimeDate, dbname);
      CacheHitRateApiResponse response = cacheHitRateService.getCacheHitRate(request);

      systemLogger.info("hitRate end。 Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("hitRate operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);
    }
    catch (ParseException pe) {
      errorLogger.error("hitRate:ParseException", pe);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (ApplicationException ae) {
      errorLogger.error("hitRate:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    catch(Exception e) {
      errorLogger.error("hitRate:Exception", e);
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
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("slowQueryCounts start。 startTime:" + startTime + " endTime:" + endTime);

      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      SlowQueryCountApiRequest request = new SlowQueryCountApiRequest(startTimeDate, endTimeDate, queryTime);
      SlowQueryCountApiResponse response = slowQueryCountService.getSlowQueryCount(request);

      systemLogger.info("slowQueryCounts end。 Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("slowQueryCounts operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);
    }
    catch (ParseException pe) {
      errorLogger.error("slowQueryCounts:ParseException", pe);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (ApplicationException ae) {
      errorLogger.error("slowQueryCounts:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    catch(Exception e) {
      errorLogger.error("slowQueryCounts:Exception", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /** プロセスメモリ使用量を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @return MemUsageApiResponse
   */

  @GetMapping
  @RequestMapping(value = "/mem-usage", method = RequestMethod.GET)
  public ResponseEntity<?> memUsage(
      @RequestParam("starttime")String startTime,
      @RequestParam("endtime")String endTime) {
    try {
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("memUsage start。 startTime:" + startTime + " endTime:" + endTime);

      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      MemUsageApiRequest request = new MemUsageApiRequest(startTimeDate, endTimeDate);
      MemUsageApiResponse response = memUsageService.getMemUsage(request);

      systemLogger.info("memUsage end。 Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("memUsage operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);
    }
    catch (ParseException pe) {
      errorLogger.error("memUsage:ParseException", pe);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (ApplicationException ae) {
      errorLogger.error("memUsage:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    catch(Exception e) {
      errorLogger.error("memUsage:Exception", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /** デッドタプル数と割合を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @return DeadTupApiResponse
   */

  @GetMapping
  @RequestMapping(value = "/dead-tup", method = RequestMethod.GET)
  public ResponseEntity<?> deadTup(
      @RequestParam("starttime")String startTime,
      @RequestParam("endtime")String endTime) {
    try {
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("deadTup start。 startTime:" + startTime + " endTime:" + endTime);

      SimpleDateFormat dtFt = new SimpleDateFormat(STRING_TO_DATE);
      Date startTimeDate = dtFt.parse(startTime);
      Date endTimeDate = dtFt.parse(endTime);
      DeadTupApiRequest request = new DeadTupApiRequest(startTimeDate, endTimeDate);
      DeadTupApiResponse response = deadTupService.getDeadTup(request);

      systemLogger.info("deadTup end。 Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("deadTup operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);
    }
    catch (ParseException pe) {
      errorLogger.error("deadTup:ParseException", pe);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (ApplicationException ae) {
      errorLogger.error("deadTup:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    catch(Exception e) {
      errorLogger.error("deadTup:Exception", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



}
