package jp.ac.databaseexplorer.storage.base;

import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import static jp.ac.databaseexplorer.common.constants.ConversionFormat.STRING_TO_DATE;

/**
 * 時刻情報を持つCSVファイルの基底モデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class CsvWithTimestampModelBase extends CsvModelBase {

  @CsvDate(STRING_TO_DATE)
  protected Date timestamp;
}
