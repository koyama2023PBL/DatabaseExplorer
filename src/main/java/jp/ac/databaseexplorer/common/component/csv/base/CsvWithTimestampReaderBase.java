package jp.ac.databaseexplorer.common.component.csv.base;

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
  public List<M> read(Date startTime, Date endTime) throws Exception {
    try {
      return super.read().stream()
          .filter(model -> model.getTimestamp().after(startTime)
              && model.getTimestamp().before(endTime))
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw e;  // TODO: 例外処理
    }
  }
}
