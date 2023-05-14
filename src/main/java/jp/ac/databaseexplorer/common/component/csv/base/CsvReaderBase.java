package jp.ac.databaseexplorer.common.component.csv.base;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jp.ac.databaseexplorer.storage.base.CsvModelBase;

import java.io.FileReader;
import java.util.List;

/**
 * ストレージのCSVファイルを読み込むための基底クラス
 */
public abstract class CsvReaderBase<M extends CsvModelBase> {

  /**
   * CSVファイルを読み込む
   */
  public List<M> read() throws Exception {
    try {
      HeaderColumnNameMappingStrategy<M> strategy = new HeaderColumnNameMappingStrategy<>();
      strategy.setType(modelClass());
      CsvToBean<M> csvToBean = new CsvToBeanBuilder<M>(new FileReader(filePath())).withMappingStrategy(strategy).build();
      return csvToBean.parse();
    } catch (Exception e) {
      throw e;  // TODO: 例外処理
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
