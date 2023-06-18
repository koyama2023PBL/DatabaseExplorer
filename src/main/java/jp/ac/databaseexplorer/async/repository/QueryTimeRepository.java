package jp.ac.databaseexplorer.async.repository;

import jp.ac.databaseexplorer.storage.visualization.QueryTime;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryTimeRepository extends JpaRepository<QueryTime, Integer> {
  @Query(value = "SELECT pid, EXTRACT(epoch FROM now())::::double precision " +
      "- EXTRACT(epoch FROM query_start)::::double precision AS execution_time FROM pg_stat_activity " +
      "WHERE state = 'active' and query NOT ILIKE '%pg_stat_activity%' and query_start IS NOT NULL", nativeQuery = true)
  List<QueryTime> getQueryTime();
}
