package jp.ac.databaseexplorer.async.repository;

import jp.ac.databaseexplorer.async.job.visualization.VisualizeJobBase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * データ可視化ジョブのリポジトリです。
 */
@Repository
public class VisualizeRepository extends AbstractJobRepository<VisualizeJobBase> {

  /**
   * コンストラクタ
   */
  public VisualizeRepository(List<VisualizeJobBase> jobList) {
    super(jobList);
  }
}
