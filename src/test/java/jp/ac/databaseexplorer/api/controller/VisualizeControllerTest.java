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
  public void testCpuUsageReturnsCorrectResponse() throws Exception {

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