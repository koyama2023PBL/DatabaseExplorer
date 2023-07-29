package jp.ac.databaseexplorer.api.controller;


import jp.ac.databaseexplorer.api.model.information.DbInfomationApiResponse;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiResponse;
import jp.ac.databaseexplorer.api.service.infomation.DbInfomationService;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/database-explorer/api/information")
@RequiredArgsConstructor
public class InfomationController {

  /**
   * Loggers
   */
  public static final Logger systemLogger = LoggerFactory.getLogger("SYSTEM_LOG");
  public static final Logger errorLogger = LoggerFactory.getLogger("ERROR_LOG");
  public static final Logger operationLogger = LoggerFactory.getLogger("OPERATION_LOG");

  private static final String STRING_TO_DATE = "yyyyMMddHHmmss";

  /**
   * postgres情報を取得するサービス
   */
  private final DbInfomationService dbInfomationService;

  /**
   * DB情報を取得するAPI
   *
   * @return DbInfomationApiResponse
   */
  @GetMapping
  @RequestMapping(value = "/dbinfo", method = RequestMethod.GET)
  public ResponseEntity<?> dbinfo() {
    try {
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("information.dbinfo start. startTime:" + new Date());

      DbInfomationApiResponse response = dbInfomationService.getInfomation();

      systemLogger.info("information.dbinfo end. Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("information.dbinfo operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);

    } catch (ApplicationException ae) {
      errorLogger.error("cpuUsage:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      errorLogger.error("cpuUsage:Exception", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * DBヘルスチェックを実行するAPI
   *
   * @return boolean
   */
  @GetMapping
  @RequestMapping(value = "/dbhealthcheck", method = RequestMethod.GET)
  public ResponseEntity<?> dbhealthcheck() {
    try {
      long methodBeginTime = System.currentTimeMillis();
      systemLogger.info("information.dbhealthcheck start. startTime:" + new Date());

      Boolean response = dbInfomationService.healthCheck();

      systemLogger.info("information.dbhealthcheck end. Response:" + response);
      long methodEndTime = System.currentTimeMillis();
      operationLogger.info("information.dbhealthcheck operationTime:" + (methodEndTime - methodBeginTime) + " ms");

      return ResponseEntity.ok(response);

    } catch (ApplicationException ae) {
      errorLogger.error("cpuUsage:ApplicationException", ae);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      errorLogger.error("cpuUsage:Exception", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
