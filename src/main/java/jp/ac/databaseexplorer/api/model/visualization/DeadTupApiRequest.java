package jp.ac.databaseexplorer.api.model.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * デッドタプル数と割合のリクエストメッセージ.
 */
@Data
@AllArgsConstructor
public class DeadTupApiRequest {

  @NonNull
  private Date startTime;

  @NonNull
  private Date endTime;
}
