package _1.vietpq.job_hunter.service.permission;

import _1.vietpq.job_hunter.domain.Permission;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PermissionService {
    Permission handleCreatePermission(Permission permission);
    Permission handleUpdatePermission(Permission permission);
    Permission fetchPermissonById(long id);
    ResultPaginationDTO fetchAllPermissions(Specification<Permission> spec, Pageable pageable);
    void handleDeletePermissionById(long id);

    boolean isPermissionExists(Permission permission);
    boolean isSameName(Permission permission);
}
