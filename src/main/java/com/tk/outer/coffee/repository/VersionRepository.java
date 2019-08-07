package com.tk.outer.coffee.repository;

import com.tk.outer.coffee.domain.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface VersionRepository extends JpaRepository<Version, Integer>, JpaSpecificationExecutor {

  @Query(value = "SELECT MAX(id) AS id FROM app_version", nativeQuery = true)
  Integer getMaxId();
}
