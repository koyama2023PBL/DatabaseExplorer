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

  //レスポンス200かつ正常にデータが取得されているパターン
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
  public void testCpuUsageBothTimesAreBeforeRange() throws Exception {

    // Arrange
    String startTimeStr = "19900501235950";
    String endTimeStr = "19900501000010";
//    Double maxUsage = 100D;
//    Double idle1 = 51.23;
//    Double idle2 = 50.56;
//    String expectResponse = "{\"starttime\":\"1990/05/01 23:59:50\",\"endtime\":\"1990/05/01 00:00:10\"," +
//        "\"data\":[]}";

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
//    Double maxUsage = 100D;
//    Double idle1 = 51.23;
//    Double idle2 = 50.56;
//    String expectResponse = "{\"starttime\":\"2025/05/01 23:59:50\",\"endtime\":\"2025/05/01 00:00:10\"," +
//        "\"data\":[]}";

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
//    Double maxUsage = 100D;
//    Double idle1 = 52.12;
//    Double idle2 = 29.96;
//    String expectResponse = "{\"starttime\":\"2023/05/01 00:12:10\",\"endtime\":\"2023/05/01 00:12:00\"," +
//        "\"data\":[]}";

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