package _1.vietpq.job_hunter.repository;

import _1.vietpq.job_hunter.domain.Permission;
import _1.vietpq.job_hunter.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission>  {
    boolean existsByMethodAndApiPathAndModule(String method, String apiPath, String module);
    List<Permission> findByIdIn(List<Long> id);


}
