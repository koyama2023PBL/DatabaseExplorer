package jp.ac.databaseexplorer.api.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
class VisualizeControllerTest {

  private MockMvc mockMvc;

  @Autowired
  VisualizeController visualizeController;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(visualizeController).build();
  }

  //ここからCPU使用率のテスト
  @Test
  public void testCpuUsageDataIsCorrectlyRetrieved() throws Exception {

    // Arrange
    String startTimeStr = "20230501001200";
    String endTimeStr = "20230501001210";
    Double maxUsage = 100D;
    Double idle1 = 52.12;
    Double idle2 = 29.96;
    String expectResponse = "{\"starttime\":\"2023/05/01 00:12:00\",\"endtime\":\"2023/05/01 00:12:10\"," +
        "\"data\":[{\"date\":\"2023/05/01 00:12:00\",\"usage\":" + (maxUsage - idle1) + "}," +
        "{\"date\":\"2023/05/01 00:12:10\",\"usage\":" + (maxUsage - idle2) + "}]}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
      .param("starttime", startTimeStr)
      .param("endtime", endTimeStr))
      .andExpect(status().isOk())
      .andExpect(content().json(expectResponse))
      .andReturn();

  }

  //日付の指定が不正なパターン
  //1: 取得していない日付が含まれる
  //2: その間にデータがない
  @Test
  public void testCpuUsageEndTimeIsOutOfRange() throws Exception {

    // Arrange
    String startTimeStr = "20230528235950";
    String endTimeStr = "20240501001210";
    Double maxUsage = 100D;
    Double idle1 = 41.69;
    //Double idle2 = 29.96;
    String expectResponse = "{\"starttime\":\"2023/05/28 23:59:50\",\"endtime\":\"2024/05/01 00:12:10\"," +
        "\"data\":[{\"date\":\"2023/05/28 23:59:50\",\"usage\":" + (maxUsage - idle1) + "}]}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
      .param("starttime", startTimeStr)
      .param("endtime", endTimeStr))
      .andExpect(status().isOk())
      .andExpect(content().json(expectResponse))
      .andReturn();

  }

  @Test
  public void testCpuUsageStartTimeIsOutOfRange() throws Exception {

    // Arrange
    String startTimeStr = "19900528235950";
    String endTimeStr = "20230501000010";
    Double maxUsage = 100D;
    Double idle1 = 51.23;
    Double idle2 = 50.56;
    String expectResponse = "{\"starttime\":\"1990/05/28 23:59:50\",\"endtime\":\"2023/05/01 00:00:10\"," +
        "\"data\":[{\"date\":\"2023/05/01 00:00:00\",\"usage\":" + (maxUsage - idle1) + "}," +
        "{\"date\":\"2023/05/01 00:00:10\",\"usage\":" + (maxUsage - idle2) + "}]}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
      .param("starttime", startTimeStr)
      .param("endtime", endTimeStr))
      .andExpect(status().isOk())
      .andExpect(content().json(expectResponse))
      .andReturn();

  }

  @Test
  public void testCpuUsageBothTimeSameTimestamp() throws Exception {

    // Arrange
    String startTimeStr = "20230501000010";
    String endTimeStr = "20230501000010";
    Double maxUsage = 100D;
    //Double idle1 = 51.23;
    Double idle2 = 50.56;
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:10\",\"endtime\":\"2023/05/01 00:00:10\"," +
        "\"data\":[{\"date\":\"2023/05/01 00:00:10\",\"usage\":" + (maxUsage - idle2) + "}]}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testCpuUsageNoData() throws Exception {

    // Arrange
    String startTimeStr = "20230501000011";
    String endTimeStr = "20230501000012";
    //Double maxUsage = 100D;
    //Double idle1 = 51.23;
    //Double idle2 = 50.56;
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:11\",\"endtime\":\"2023/05/01 00:00:12\"," +
        "\"data\":[]}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testCpuUsageBothTimesAreBeforeRange() throws Exception {

    // Arrange
    String startTimeStr = "19900501235950";
    String endTimeStr = "19900501000010";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
      .param("starttime", startTimeStr)
      .param("endtime", endTimeStr))
      .andExpect(status().is5xxServerError())
      .andReturn();

  }

  @Test
  public void testCpuUsageBothTimesAreAfterRange() throws Exception {

    // Arrange
    String startTimeStr = "20250501235950";
    String endTimeStr = "20250501000010";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
      .param("starttime", startTimeStr)
      .param("endtime", endTimeStr))
      .andExpect(status().is5xxServerError())
      .andReturn();

  }

  @Test
  public void testCpuUsageTimesAreInverted() throws Exception {

    // Arrange
    String startTimeStr = "20230501001210";
    String endTimeStr = "20230501001200";


    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
      .param("starttime", startTimeStr)
      .param("endtime", endTimeStr))
      .andExpect(status().is5xxServerError())
      .andReturn();

  }

  @Test
  public void testCpuUsageTimeIsInvalid() throws Exception {

    // Arrange
    String startTimeStr = "";
    String endTimeStr = "20230501001200";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/cpu-usage")
      .param("starttime", startTimeStr)
      .param("endtime", endTimeStr))
      .andExpect(status().isBadRequest());
  }



  //ここからポスグレのプロセスのテスト

  @Test
  public void testProcessesDataIsCorrectlyRetrieved() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000200";
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:00\",\"endtime\":\"2023/05/01 00:02:00\"," +
        "\"masterProcess\":true," +
        "\"walWriter\":true," +
        "\"writer\":true," +
        "\"checkPointer\":false," +
        "\"statisticsCollector\":true," +
        "\"autoVacuumLauncher\":false," +
        "\"autoVacuumWorker\":true," +
        "\"backendProcess\":true}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testProcessesEndTimeIsOutOfRange() throws Exception {

    //Arrange
    String startTimeStr = "20230528235710";
    String endTimeStr = "20240501000200";
    String expectResponse = "{\"starttime\":\"2023/05/28 23:57:10\",\"endtime\":\"2024/05/01 00:02:00\"," +
        "\"masterProcess\":false," +
        "\"walWriter\":true," +
        "\"writer\":true," +
        "\"checkPointer\":true," +
        "\"statisticsCollector\":true," +
        "\"autoVacuumLauncher\":true," +
        "\"autoVacuumWorker\":true," +
        "\"backendProcess\":true}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testProcessesStartTimeIsOutOfRange() throws Exception {

    //Arrange
    String startTimeStr = "20200528235710";
    String endTimeStr = "20230501000000";
    String expectResponse = "{\"starttime\":\"2020/05/28 23:57:10\",\"endtime\":\"2023/05/01 00:00:00\"," +
        "\"masterProcess\":true," +
        "\"walWriter\":true," +
        "\"writer\":true," +
        "\"checkPointer\":false," +
        "\"statisticsCollector\":true," +
        "\"autoVacuumLauncher\":true," +
        "\"autoVacuumWorker\":true," +
        "\"backendProcess\":true}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testProcessesDateAreSameTimestamp() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000000";
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:00\",\"endtime\":\"2023/05/01 00:00:00\"," +
        "\"masterProcess\":true," +
        "\"walWriter\":true," +
        "\"writer\":true," +
        "\"checkPointer\":false," +
        "\"statisticsCollector\":true," +
        "\"autoVacuumLauncher\":true," +
        "\"autoVacuumWorker\":true," +
        "\"backendProcess\":true}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testProcessesNoData() throws Exception {

    //Arrange
    String startTimeStr = "20230501000001";
    String endTimeStr = "20230501000002";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().is5xxServerError());

  }

  @Test
  public void testProcessesBothTimesAreBeforeRange() throws Exception {

    // Arrange
    String startTimeStr = "19900501235950";
    String endTimeStr = "19900501000010";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testProcessesBothTimesAreAfterRange() throws Exception {

    // Arrange
    String startTimeStr = "20400501235950";
    String endTimeStr = "20400501000010";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testProcessesTimesAreInverted() throws Exception {

    // Arrange
    String startTimeStr = "20230501001210";
    String endTimeStr = "20230501001200";


    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testProcessesTimeIsInvalid() throws Exception {

    // Arrange
    String startTimeStr = "";
    String endTimeStr = "20230501001200";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/processes")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isBadRequest());
  }


  //ここからスロークエリのテスト

  @Test
  public void testSlowQueryDataIsCorrectlyRetrieved() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000200";
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:00\",\"endtime\":\"2023/05/01 00:02:00\",\"querytime\":70.0,\"count\":1}";
    String threshold = "70";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testSlowQueryDateAreSameTimestamp() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000000";
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:00\",\"endtime\":\"2023/05/01 00:00:00\",\"querytime\":70.0,\"count\":0}";
    String threshold = "70";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  //閾値を超えるスロークエリがない場合
  @Test
  public void testSlowQueryDataIsNotFound() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000200";
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:00\",\"endtime\":\"2023/05/01 00:02:00\",\"querytime\":1000.0,\"count\":0}";
    String threshold = "1000";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  //閾値の設定がない場合
  @Test
  public void testSlowQueryNoThreshold() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000200";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isBadRequest())
        .andReturn();

  }

  @Test
  public void testSlowQueryEndTimeIsOutOfRange() throws Exception {

    //Arrange
    String startTimeStr = "20230528235710";
    String endTimeStr = "20240501000200";
    String expectResponse = "{\"starttime\":\"2023/05/28 23:57:10\",\"endtime\":\"2024/05/01 00:02:00\",\"querytime\":70.0,\"count\":3}";
    String threshold = "70";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
        .param("starttime", startTimeStr)
        .param("endtime", endTimeStr)
        .param("querytime", threshold))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testSlowQueryStartTimeIsOutOfRange() throws Exception {

    //Arrange
    String startTimeStr = "20200528235710";
    String endTimeStr = "20230501000000";
    String expectResponse = "{\"starttime\":\"2020/05/28 23:57:10\",\"endtime\":\"2023/05/01 00:00:00\",\"querytime\":5,\"count\":1}";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();
  }

  @Test
  public void testSlowQueryNoData() throws Exception {

    //Arrange
    String startTimeStr = "20230501000001";
    String endTimeStr = "20230501000002";
    //String expectResponse = "{\"starttime\":\"2020/05/28 23:57:10\",\"endtime\":\"2023/05/01 00:00:00\",\"querytime\":5,\"count\":1}";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().is5xxServerError());
  }

  @Test
  public void testSlowQueryBothTimesAreBeforeRange() throws Exception {

    // Arrange
    String startTimeStr = "19900501235950";
    String endTimeStr = "19900501000010";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testSlowQueryBothTimesAreAfterRange() throws Exception {

    // Arrange
    String startTimeStr = "20400501235950";
    String endTimeStr = "20400501000010";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testSlowQueryTimesAreInverted() throws Exception {

    // Arrange
    String startTimeStr = "20230501001210";
    String endTimeStr = "20230501001200";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testSlowQueryTimeIsInvalid() throws Exception {

    // Arrange
    String startTimeStr = "";
    String endTimeStr = "20230501001200";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testSlowQueryThresholdIsInvalid() throws Exception {

    // Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000200";
    String threshold = "A";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isBadRequest());
  }

  //ここからキャッシュヒット率のテスト

  @Test
  public void testCacheHitRateDataIsCorrectlyRetrieved() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000200";
    String dbname = "explorer";
    Double hitCount = 189D - 8D; //hitCount.max - hitCount.min
    Double readCount = 218D- 9D; //readCount.max - readCount.min
    Double hitRate = hitCount / (readCount + hitCount);
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:00\",\"endtime\":\"2023/05/01 00:02:00\",\"dbname\":\"explorer\",\"hitrate\":"+ hitRate + "}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/hit-rate")
        .param("starttime", startTimeStr)
        .param("endtime", endTimeStr)
        .param("dbname", dbname))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testCacheHitRateDateAreSameTimestamp() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000000";
    String dbname = "explorer";
    Double hitCount = 8D; //hitCount.max - hitCount.min
    Double readCount = 9D; //readCount.max - readCount.min
    Double hitRate = hitCount / (readCount + hitCount);
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:00\",\"endtime\":\"2023/05/01 00:00:00\",\"dbname\":\"explorer\",\"hitrate\":"+ hitRate + "}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/hit-rate")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("dbname", dbname))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testCacheHitRateDataIsNotFound() throws Exception {

    //Arrange
    String startTimeStr = "20230501000001";
    String endTimeStr = "20230501000002";
//    double hitCount = 38 - 8; //hitCount.max - hitCount.min
//    double readCount = 42- 9; //readCount.max - readCount.min
    Double hitRate = -1.0;
    String dbname = "explorer";
    String expectResponse = "{\"starttime\":\"2023/05/01 00:00:01\",\"endtime\":\"2023/05/01 00:00:02\",\"dbname\":\"explorer\",\"hitrate\":"+ hitRate + "}";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/hit-rate")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("dbname", dbname))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  //DB名の設定がない場合
  @Test
  public void testCacheHitRateNoDBName() throws Exception {

    //Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000200";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/hit-rate")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr))
        .andExpect(status().isBadRequest())
        .andReturn();

  }

  @Test
  public void testCacheHitRateEndTimeIsOutOfRange() throws Exception {

    //Arrange
    String startTimeStr = "20230528235710";
    String endTimeStr = "20240501000200";
    String expectResponse = "{\"starttime\":\"2023/05/28 23:57:10\",\"endtime\":\"2024/05/01 00:02:00\",\"querytime\":70.0,\"count\":3}";
    String threshold = "70";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();

  }

  @Test
  public void testCacheHitRateStartTimeIsOutOfRange() throws Exception {

    //Arrange
    String startTimeStr = "20200528235710";
    String endTimeStr = "20230501000000";
    String expectResponse = "{\"starttime\":\"2020/05/28 23:57:10\",\"endtime\":\"2023/05/01 00:00:00\",\"querytime\":5,\"count\":1}";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isOk())
        .andExpect(content().json(expectResponse))
        .andReturn();
  }

  @Test
  public void testCacheHitRateNoData() throws Exception {

    //Arrange
    String startTimeStr = "20230501000001";
    String endTimeStr = "20230501000002";
    //String expectResponse = "{\"starttime\":\"2020/05/28 23:57:10\",\"endtime\":\"2023/05/01 00:00:00\",\"querytime\":5,\"count\":1}";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().is5xxServerError());
  }

  @Test
  public void testCacheHitRateBothTimesAreBeforeRange() throws Exception {

    // Arrange
    String startTimeStr = "19900501235950";
    String endTimeStr = "19900501000010";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testCacheHitRateBothTimesAreAfterRange() throws Exception {

    // Arrange
    String startTimeStr = "20400501235950";
    String endTimeStr = "20400501000010";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testCacheHitRateTimesAreInverted() throws Exception {

    // Arrange
    String startTimeStr = "20230501001210";
    String endTimeStr = "20230501001200";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().is5xxServerError())
        .andReturn();

  }

  @Test
  public void testCacheHitRateTimeIsInvalid() throws Exception {

    // Arrange
    String startTimeStr = "";
    String endTimeStr = "20230501001200";
    String threshold = "5";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCacheHitRateThresholdIsInvalid() throws Exception {

    // Arrange
    String startTimeStr = "20230501000000";
    String endTimeStr = "20230501000200";
    String threshold = "A";

    // Act
    mockMvc.perform(get("/database-explorer/api/visualization/slow-query-counts")
            .param("starttime", startTimeStr)
            .param("endtime", endTimeStr)
            .param("querytime", threshold))
        .andExpect(status().isBadRequest());
  }


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