package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * CPU使用率のデータを保持するモデルクラス
 */
@Data
@AllArgsConstructor
public class CpuUsageData {
  @NonNull
  private Date timestamp;
  @NonNull
  private Double usage;
}
