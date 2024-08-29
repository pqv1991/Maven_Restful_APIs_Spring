package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.domain.Role;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.service.role.RoleService;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    @ApiMessage("FETCH CREATE ROLE")
    public ResponseEntity<Role> createRole (@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.handleCreateRole(role));
    }

    @PutMapping("/roles")
    @ApiMessage("FETCH UPDATE ROLE")
    public ResponseEntity<Role> updateRole (@RequestBody Role role) {
        return ResponseEntity.ok().body(roleService.handleUpdateRole(role));
    }

    @GetMapping("/roles/{id}")
    @ApiMessage("FETCH ROLE BY ID")
    public ResponseEntity<Role> getRoleById (@PathVariable("id") long id) {
        return ResponseEntity.ok().body(roleService.fetchRoleById(id));
    }

    @GetMapping("/roles")
    @ApiMessage("FETCH ALL ROLES")
    public ResponseEntity<ResultPaginationDTO> getAllRoles(@Filter Specification<Role> spec, Pageable pageable) {

        return ResponseEntity.ok().body(roleService.fetchAllRole(spec,pageable));
    }

    @DeleteMapping("/roles/{id}")
    @ApiMessage("DELETE ROLE")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") long id) {

        roleService.handleDeleteRole(id);

        return ResponseEntity.ok().body(null);
    }


}
