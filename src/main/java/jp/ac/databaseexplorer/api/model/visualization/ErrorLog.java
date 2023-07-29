package jp.ac.databaseexplorer.api.model.visualization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorLog {


  @NonNull
  @JsonProperty("timestamp")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
  private Date timestamp;

  @NonNull
  @JsonProperty("logMessage")
  private String logMessage;
}
