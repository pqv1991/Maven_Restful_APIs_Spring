package _1.vietpq.job_hunter.service.role;

import _1.vietpq.job_hunter.domain.Permission;
import _1.vietpq.job_hunter.domain.Role;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.exception.DuplicatedException;
import _1.vietpq.job_hunter.exception.NotFoundException;
import _1.vietpq.job_hunter.exception.message.RoleMessage;
import _1.vietpq.job_hunter.repository.PermissionRepository;
import _1.vietpq.job_hunter.repository.RoleRepository;
import _1.vietpq.job_hunter.util.validator.RoleValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private  final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Role handleCreateRole(Role role) {
        RoleValidator.notNullName(role.getName());
        if(isNameExists(role.getName())){
            throw new DuplicatedException(RoleMessage.NAME_ALREADY_EXIST);
        }
        return  this.roleRepository.save(role);
    }

    @Override
    public Role handleUpdateRole(Role role) {
       if(role.getPermissions()!=null){
           List<Long> listIdPermisson = role.getPermissions().stream().map(x->x.getId()).toList();
           List<Permission> permissionList = this.permissionRepository.findByIdIn(listIdPermisson);
           role.setPermissions(permissionList);
       }
       Role roleDb = fetchRoleById(role.getId());
       roleDb.setName(role.getName());
       roleDb.setDescription(role.getDescription());
       roleDb.setActive(role.isActive());
       roleDb.setPermissions(role.getPermissions());
       return this.roleRepository.save(roleDb);
    }

    @Override
    public Role fetchRoleById(long id) {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        if(roleOptional.isEmpty()){
            throw  new NotFoundException(RoleMessage.NOT_FOUND);
        }
        return roleOptional.get();
    }

    @Override
    public ResultPaginationDTO fetchAllRole(Specification<Role> spec, Pageable pageable) {
        Page<Role> permissionPage = roleRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(permissionPage.getTotalPages());
        mt.setTotal(permissionPage.getTotalElements());
        res.setMeta(mt);
        res.setResult(permissionPage.getContent());
        return  res;

    }

    @Override
    public void handleDeleteRole(long id) {
        Role role = fetchRoleById(id);
        roleRepository.deleteById(id);

    }

    @Override
    public boolean isNameExists(String name) {
       return this.roleRepository.existsByName(name);
    }
}
