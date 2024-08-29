package _1.vietpq.job_hunter.service.permission;

import _1.vietpq.job_hunter.domain.Permission;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.exception.DuplicatedException;
import _1.vietpq.job_hunter.exception.NotNullException;
import _1.vietpq.job_hunter.exception.message.PermissionMessage;
import _1.vietpq.job_hunter.repository.PermissionRepository;
import _1.vietpq.job_hunter.util.validator.PermissionValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissonServiceImpl implements PermissionService{
    private final PermissionRepository permissionRepository;

    public PermissonServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }


    @Override
    public Permission handleCreatePermission(Permission permission) {
        PermissionValidator.validatorPermisson(permission);
        if(!isSameName(permission)){
            throw new DuplicatedException(PermissionMessage.NAME_ALREADY_EXIST);
        }
        return  this.permissionRepository.save(permission);
    }

    @Override
    public Permission handleUpdatePermission(Permission permission) {
        Permission permissionDB = fetchPermissonById(permission.getId());
        if (!isSameName(permission)){
            throw  new DuplicatedException(PermissionMessage.NAME_ALREADY_EXIST);
        }
        permissionDB.setName(permission.getName());
        permissionDB.setMethod(permission.getMethod());
        permissionDB.setApiPath(permission.getApiPath());
        permissionDB.setModule(permission.getModule());
        return  this.permissionRepository.save(permissionDB);
    }

    @Override
    public Permission fetchPermissonById(long id) {
        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        if(permissionOptional.isEmpty()){
            throw  new NotNullException(PermissionMessage.NOT_FOUND);
        }
       return permissionOptional.get();
    }

    @Override
    public ResultPaginationDTO fetchAllPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> permissionPage = permissionRepository.findAll(spec, pageable);
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
    public void handleDeletePermissionById(long id) {
        Permission permissionDb = fetchPermissonById(id);
        permissionDb.getRoles().forEach(role->role.getPermissions().remove(permissionDb));
        permissionRepository.delete(permissionDb);
    }

    @Override
    public boolean isPermissionExists(Permission permission) {
        return this.permissionRepository.existsByMethodAndApiPathAndModule(permission.getMethod(),permission.getApiPath(),permission.getModule());
    }

    @Override
    public boolean isSameName(Permission permission) {
        Permission permissionDb = fetchPermissonById(permission.getId());
        if(permission != null){
            if (permissionDb.getName().equals(permission.getName())){
                return  true;
            }
        }
        return  false;
    }
}
