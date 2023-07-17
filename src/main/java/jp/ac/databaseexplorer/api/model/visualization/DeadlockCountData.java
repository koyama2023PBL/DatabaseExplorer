package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * データベースから取得したデッドロック発生回数を格納するモデルクラス
 */
@Data
@AllArgsConstructor
public class DeadlockCountData {

  @NonNull
  @JsonProperty("timestamp")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date timestamp;

  @NonNull
  @JsonProperty("dbName")
  private String dbName;

  @NonNull
  @JsonProperty("deadlocks")
  private Long deadlocks;
}
