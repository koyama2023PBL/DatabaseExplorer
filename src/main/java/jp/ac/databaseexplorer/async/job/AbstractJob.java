package jp.ac.databaseexplorer.async.job;

import jp.ac.databaseexplorer.common.exception.ApplicationException;

/**
 * 非同期データ収集処理の基底クラス
 */
public abstract class AbstractJob {

  /**
   * データ収集処理を実行
   */
  public abstract void execute() throws ApplicationException;
}
