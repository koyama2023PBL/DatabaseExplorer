package jp.ac.databaseexplorer.async.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessStatusRepository  {

  private final JdbcTemplate jdbcTemplate;

  public ProcessStatusRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public int selectOne() {
    String sql = "SELECT 1";
    return jdbcTemplate.queryForObject(sql, Integer.class);
  }
}
