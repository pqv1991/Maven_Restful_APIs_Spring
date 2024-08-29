package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.domain.Permission;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.service.permission.PermissionService;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    @ApiMessage("FETCH CREATE PERMISSION")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission)  {

        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.handleCreatePermission(permission));
    }

    @PutMapping("/permissions")
    @ApiMessage("FETCH UPDATE PERMISSION")
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission){
        return ResponseEntity.ok().body(permissionService.handleUpdatePermission(permission));
    }

    @GetMapping("/permissions/{id}")
    @ApiMessage("FETCH GET PERMISSION BY ID")
    public ResponseEntity<Permission> getPermissionById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(permissionService.fetchPermissonById(id));
    }

    @GetMapping("/permissions")
    @ApiMessage("FETCH ALL PERMISSION")
    public ResponseEntity<ResultPaginationDTO> getAllPermissions(@Filter Specification<Permission> spec, Pageable pageable) {
        return ResponseEntity.ok().body(permissionService.fetchAllPermissions(spec,pageable));
    }

    @DeleteMapping("/permissions/{id}")
    @ApiMessage("DELETE PERMISSION")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") long id) {
        permissionService.handleDeletePermissionById(id);

        return ResponseEntity.ok().body(null);
    }

}
