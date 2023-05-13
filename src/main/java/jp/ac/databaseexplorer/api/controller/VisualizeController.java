package jp.ac.databaseexplorer.api.controller;

import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiResponse;
import jp.ac.databaseexplorer.async.main.AsyncDataCollectionService;
import jp.ac.databaseexplorer.api.service.visualization.CpuUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Visualization層のAPIリクエストを受け付けるためAPIコントローラクラス.
 */
@RestController
@RequestMapping(value = "/api/research")
@RequiredArgsConstructor
public class VisualizeController {

  /**
   * コンストラクタインジェクション.
   */
  private final AsyncDataCollectionService asyncDataCollectionService;
  private final CpuUsageService cpuUsageService;

  /**
   * アプリケーションヘルスチェック.
   * 内容は後続スプリントで改良予定.
   *
   * @return HttpResponse
   */
  @GetMapping
  @RequestMapping(value = "/healthCheck")
  public ResponseEntity<?> healthCheck() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  @RequestMapping(value = "/cpu")
  public ResponseEntity<CpuUsageApiResponse> cpuResource(@Validated CpuUsageApiRequest request) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  @RequestMapping(value = "/csvtest")
  public void cpuCsvTest() {
    System.out.println(cpuUsageService.testCSV());
  }
}
