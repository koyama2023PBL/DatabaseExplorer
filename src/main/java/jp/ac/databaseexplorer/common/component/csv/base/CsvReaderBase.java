package jp.ac.databaseexplorer.common.component.csv.base;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jp.ac.databaseexplorer.common.exception.SystemException;
import jp.ac.databaseexplorer.storage.base.CsvModelBase;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * ストレージのCSVファイルを読み込むための基底クラス
 */
public abstract class CsvReaderBase<M extends CsvModelBase> {

  /**
   * CSVファイルを読み込む
   */
  public List<M> read() throws SystemException {
    try {
      HeaderColumnNameMappingStrategy<M> strategy = new HeaderColumnNameMappingStrategy<>();
      strategy.setType(modelClass());
      CsvToBean<M> csvToBean = new CsvToBeanBuilder<M>(new FileReader(filePath())).withMappingStrategy(strategy).build();
      return csvToBean.parse();
    } catch (IOException ioe) {
      throw new SystemException("SYS-00001", "CSV取得処理でIOExceptionが発生しました", ioe);
    } catch (Exception e) {
      throw new SystemException("SYS-00007", "CSV取得処理で予期せぬエラーが発生しました", e);
    }
  }

  /**
   * CSVファイルのパスを返す
   */
  protected abstract String filePath();

  /**
   * モデルクラスのクラスオブジェクトを返す
   */
  protected abstract Class<M> modelClass();
}
