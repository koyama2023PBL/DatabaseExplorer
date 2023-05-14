package jp.ac.databaseexplorer.api.controller;

import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiResponse;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageData;
import jp.ac.databaseexplorer.api.service.visualization.CpuUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
   * Services
   */
  private final CpuUsageService cpuUsageService;

  /**
   * CPU使用率を取得するAPI
   * @param startTime 取得期間の開始時間
   * @param endTime 取得期間の終了時間
   * @return HttpResponse
   */
  @GetMapping
  @RequestMapping(value = "/cpu-usage", method = RequestMethod.GET)
  public ResponseEntity<?> cpuUsage( @RequestParam("starttime")String startTime,@RequestParam("endtime")String endTime) {
    try {
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

      // サービスはエラーが発生したらNULLを返す想定
      if (response == null) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return ResponseEntity.ok(response);
      }
    catch (ParseException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch(Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  @RequestMapping(value = "/cpu")
  public ResponseEntity<CpuUsageApiResponse> cpuResource() {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
