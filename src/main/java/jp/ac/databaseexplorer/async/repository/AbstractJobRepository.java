package jp.ac.databaseexplorer.async.repository;

import jp.ac.databaseexplorer.async.job.AbstractJob;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 非同期データ収集処理のリポジトリの基底クラス
 */
@RequiredArgsConstructor
public abstract class AbstractJobRepository<J extends AbstractJob> {

  /**
   * データ収集ジョブのリスト
   */
  protected final List<J> jobList;

  /**
   * ジョブを順に実行する
   */
  public void start() throws ApplicationException {
    for (J job : jobList) {
      job.execute();
    }
  }
}
