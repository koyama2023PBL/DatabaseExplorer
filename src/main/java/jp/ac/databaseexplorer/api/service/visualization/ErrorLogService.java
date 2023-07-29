package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.*;
import jp.ac.databaseexplorer.common.component.os.SshUtil;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorLogService {

  /**
   * Bean
   */
  private final SshUtil ssh;

  @Value("${spring.datasource.postgresql.log}")
  public String LOG_FILE;

  /**
   * エラーログを取得する
   */
  public ErrorLogResponse getErrorLog(@NonNull ErrorLogRequest request) throws ApplicationException {
    try {
      Date startTime = request.getStartTime();
      Date endTime = request.getEndTime();

      // インプット項目チェック
      if (request.getStartTime().after(request.getEndTime())) {
        throw new ApplicationException("", "", new Exception());
      }
      List<ErrorLog> errorLogList = new ArrayList<ErrorLog>();
      String result = ssh.execute("cat " + LOG_FILE);
      String[] lines = result.split("\\n");
      for (String line : lines)
      {
        if(line.contains("ERROR") || line.contains("FATAL") || line.contains("PANIC")){
          String time = line.split("JST")[0];
          try {
            Date dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(time.trim());
            if((dt.after(startTime) && dt.before(endTime)) || dt.equals(startTime) || dt.equals(endTime)){
              errorLogList.add(new ErrorLog(dt, line.replaceFirst(time, "").trim()));
            }
          }
          catch (ParseException pe){
            // 何もしない
          }
        }
      }
      ErrorLogResponse response = new ErrorLogResponse(startTime,endTime, errorLogList);
      return response;
    } catch (SystemException se) {
      throw new ApplicationException("APP-00049", "エラーログ取得処理でシステム例外が発生しました", se);
    } catch (ApplicationException ae) {
      throw new ApplicationException("APP-00050", "エラーログ取得処理で業務例外が発生しました", ae);
    } catch (Exception e) {
      throw new ApplicationException("APP-00051", "エラーログ情報取得処理で予期せぬ例外が発生しました", e);
    }
  }
}
