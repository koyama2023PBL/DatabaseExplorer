package jp.ac.databaseexplorer.api.controller;

import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiResponse;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageData;
import jp.ac.databaseexplorer.api.service.visualization.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class VisualizeControllerTest {

  private final CpuUsageService cpuUsageService = Mockito.mock(CpuUsageService.class);
  private final PostgresProcessService postgresProcessService = Mockito.mock(PostgresProcessService.class);
  private final AverageQueryTimeService averageQueryTimeService = Mockito.mock(AverageQueryTimeService.class);
  private final CacheHitRateService cacheHitRateService = Mockito.mock(CacheHitRateService.class);
  private final SlowQueryCountService slowQueryCountService = Mockito.mock(SlowQueryCountService.class);

  private final VisualizeController visualizeController = new VisualizeController(cpuUsageService, postgresProcessService, averageQueryTimeService, cacheHitRateService, slowQueryCountService);

  //レスポンス200かつ正常にデータが取得されているパターン
  @Test
  public void testCpuUsageReturnsCorrectResponse() throws Exception {
    // API側から呼ばれた場合の挙動を定義
    String startTimeStr = "20230501000120";
    String endTimeStr = "20230501000130";
    SimpleDateFormat dtFt = new SimpleDateFormat("yyyyMMddHHmmss");
    Date startTimeDate = dtFt.parse(startTimeStr);
    Date endTimeDate = dtFt.parse(endTimeStr);
    CpuUsageApiRequest request = new CpuUsageApiRequest(startTimeDate, endTimeDate);
    // 期待するレスポンスを定義
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date1 = formatter.parse("2023/05/01 00:01:20");
    Date date2 = formatter.parse("2023/05/01 00:01:30");
    double maxUsage = 100;
    double actualIdleRate1 = 41.09;
    double actualIdleRate2 = 31.63;
    CpuUsageData data1 = new CpuUsageData(date1, maxUsage-actualIdleRate1);
    CpuUsageData data2 = new CpuUsageData(date2, maxUsage-actualIdleRate2);
    CpuUsageApiResponse expectedResponse = new CpuUsageApiResponse(date1, date2, new CpuUsageData[]{data1, data2});
    when(cpuUsageService.getCpuUsage(request)).thenReturn(expectedResponse);

    // Act
    ResponseEntity<?> responseEntity = visualizeController.cpuUsage(startTimeStr, endTimeStr);

    // Assert
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedResponse, responseEntity.getBody());
  }

  //レスポンス200かつ正常にデータが取得されているパターン


//  @Test
//  void processes() {
//    assertTrue(true);
//  }
//
//  @Test
//  void averageQueryTime() {
//    assertTrue(true);
//  }
//
//  @Test
//  void hitRate() {
//    assertTrue(true);
//  }
//
//  @Test
//  void slowQueryCounts() {
//    assertTrue(true);
//  }
}