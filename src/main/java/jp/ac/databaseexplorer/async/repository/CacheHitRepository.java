package jp.ac.databaseexplorer.async.repository;

import jp.ac.databaseexplorer.storage.visualization.CacheHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CacheHitRepository extends JpaRepository<CacheHit, String> {
  @Query("SELECT e FROM CacheHit e where e.dbName != ''")
    List<CacheHit> findAllWithTime();
}
