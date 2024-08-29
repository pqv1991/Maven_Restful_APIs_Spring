package _1.vietpq.job_hunter.service.role;

import _1.vietpq.job_hunter.domain.Role;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface RoleService {

    Role handleCreateRole(Role role);
    Role handleUpdateRole(Role role);
    Role fetchRoleById(long id);
    ResultPaginationDTO fetchAllRole(Specification<Role> spec, Pageable pageable);
    void handleDeleteRole(long id);

    boolean isNameExists(String name);
}
