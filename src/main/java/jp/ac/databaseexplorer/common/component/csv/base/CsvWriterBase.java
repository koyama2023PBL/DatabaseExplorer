package jp.ac.databaseexplorer.common.component.csv.base;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import jp.ac.databaseexplorer.common.exception.SystemException;
import jp.ac.databaseexplorer.storage.base.CsvModelBase;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public abstract class CsvWriterBase {

  /**
   * CSVファイルのパスを返す
   */
  protected abstract String filePath();

  /**
   * CSVファイルのヘッダーを返す
   */
  protected abstract String getHeader();

  public <T> void write(T bean) {
    try(FileWriter fileWriter = new FileWriter(filePath(), Charset.forName("UTF-8"), true))
    {
      Path path = Paths.get(filePath());

      if(!Files.exists(path) || Files.size(path) == (long)0)
      {
        fileWriter.write(getHeader());
        // 改行を追加する
        fileWriter.write(System.lineSeparator());
      }

      // Creating StatefulBeanToCsv object
      StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(fileWriter).withQuotechar('\u0000').build();
      beanToCsv.write(bean);
    }
    catch (IOException ioe) {
      throw new SystemException("SYS-00018", "CSV出力処理でIOExceptionが発生しました", ioe);
    } catch (Exception e) {
      throw new SystemException("SYS-00019", "CSV出力処理で予期せぬエラーが発生しました", e);
    }
  }

}
