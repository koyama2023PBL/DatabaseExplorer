package jp.ac.databaseexplorer.api.model.information;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * DB情報を取得するレスポンスメッセージ.
 */
@Data
@AllArgsConstructor
public class DbInfomationApiResponse {

  @NonNull
  private String version;

}
