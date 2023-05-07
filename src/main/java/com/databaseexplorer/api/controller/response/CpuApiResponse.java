package com.databaseexplorer.api.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;


/**
 * CPUリソース情報のレスポンスメッセージ.
 */
@Data
@AllArgsConstructor
public class CpuApiResponse {

  @NonNull
  private Date fromDate;

  @NonNull
  private Date toDate;

  @NonNull
  private Object cpuData;
  //CPUデータはここに配列として格納する
  //Storageクラスにて作成するOR-Mapperと同様の定義にする予定
  //例：1col-タイムスタンプ、2col-CPU使用率

}
