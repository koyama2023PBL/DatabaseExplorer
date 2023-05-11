package jp.ac.databaseexplorer.storage.visualization;

import jp.ac.databaseexplorer.storage.CsvModelBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sarコマンドで取得したデータを保持するモデルクラス
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Sar extends CsvModelBase {
}
