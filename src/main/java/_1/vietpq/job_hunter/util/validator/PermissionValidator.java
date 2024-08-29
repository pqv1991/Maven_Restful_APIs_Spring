package _1.vietpq.job_hunter.util.validator;

import _1.vietpq.job_hunter.domain.Permission;
import _1.vietpq.job_hunter.exception.NotNullException;
import _1.vietpq.job_hunter.exception.message.CompanyMessage;
import _1.vietpq.job_hunter.exception.message.PermissionMessage;

public class PermissionValidator {
    public static  void notNullName(String name) throws NotNullException {
        if(name == null || name.trim().isEmpty())
            throw  new NotNullException(PermissionMessage.NAME_REQUIRED);
    }
    public static  void notNullMethod(String method) throws NotNullException {
        if(method == null || method.trim().isEmpty())
            throw  new NotNullException(PermissionMessage.METHOD_REQUIRED);
    }
    public static  void notNullApiPath(String apiPAth) throws NotNullException {
        if(apiPAth == null || apiPAth.trim().isEmpty())
            throw  new NotNullException(PermissionMessage.APIPATH_REQUIRED);
    }
    public static  void notNullModule(String module) throws NotNullException {
        if(module == null || module.trim().isEmpty())
            throw  new NotNullException(PermissionMessage.MODULE_REQUIRED);
    }

    public static void validatorPermisson(Permission permission){
        notNullName(permission.getName());
        notNullMethod(permission.getMethod());
        notNullApiPath(permission.getApiPath());
        notNullModule(permission.getModule());
    }
}
