package com.databaseexplorer.api.controller.main;


import com.databaseexplorer.api.controller.request.CpuApiRequest;
import com.databaseexplorer.api.controller.response.CpuApiResponse;
import com.databaseexplorer.api.service.datacollection.main.AsyncDataCollectionService;
import com.databaseexplorer.api.service.visualization.main.VisualizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * APIリクエストを受け付けるためのAPIコントローラクラス.
 */
@RestController
@RequestMapping(value = "/api/research")
@RequiredArgsConstructor
public class DatabaseExplorerController {

  /**
   * コンストラクタインジェクション.
   */
  private final AsyncDataCollectionService asyncDataCollectionService;
  private final VisualizeService visualizeService;

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
  public ResponseEntity<CpuApiResponse> cpuResource(@Validated CpuApiRequest request) {

    visualizeService.hello();
    return new ResponseEntity<>(HttpStatus.OK);

  }

}
