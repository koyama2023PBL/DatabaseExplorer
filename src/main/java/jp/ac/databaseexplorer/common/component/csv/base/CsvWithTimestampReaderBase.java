package jp.ac.databaseexplorer.common.component.csv.base;

import jp.ac.databaseexplorer.common.exception.SystemException;
import jp.ac.databaseexplorer.storage.base.CsvWithTimestampModelBase;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 時間情報を持つCSVファイルを読み込むための基底クラス
 */
public abstract class CsvWithTimestampReaderBase<M extends CsvWithTimestampModelBase> extends CsvReaderBase<M> {

  /**
   * CSVファイルを読み込んで、指定された時間範囲のデータを返す
   */
  public List<M> read(Date startTime, Date endTime) throws SystemException {
    try {
      return super.read().stream()
          .filter(model -> (model.getTimestamp().getTime() >= startTime.getTime())
              && (model.getTimestamp().getTime() <= endTime.getTime()))
          .collect(Collectors.toList());
    } catch (SystemException se) {
      throw new SystemException("SYS-00002", "CSV取得処理でエラーが発生しました", se);
    } catch (Exception e) {
      throw new SystemException("SYS-00003", "時間範囲取得処理で予期せぬエラーが発生しました", e);
    }
  }
}
