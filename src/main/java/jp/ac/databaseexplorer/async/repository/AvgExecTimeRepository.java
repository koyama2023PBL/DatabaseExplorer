package jp.ac.databaseexplorer.async.repository;

import jp.ac.databaseexplorer.storage.visualization.AvgExecTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvgExecTimeRepository extends JpaRepository<AvgExecTime, Short> {
  @Query(value = "SELECT " +
      "CASE " +
      "WHEN query ILIKE '%INSERT%' THEN 2 " +
      "WHEN query ILIKE '%UPDATE%' THEN 3 " +
      "WHEN query ILIKE '%DELETE%' THEN 4 " +
      "WHEN query ILIKE '%SELECT%' THEN 1 " +
      "END AS kind, " +
      "SUM(calls) AS calls, " +
      "SUM(total_exec_time) AS total_exec_time " +
      "FROM " +
      "pg_stat_statements " +
      "WHERE " +
      "query NOT LIKE '%pg_stat_statements%' " +
      "GROUP BY " +
      "kind ;", nativeQuery = true)
  List<AvgExecTime> getAvgExecTime();
}
