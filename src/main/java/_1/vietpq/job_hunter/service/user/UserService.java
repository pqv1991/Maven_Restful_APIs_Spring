package _1.vietpq.job_hunter.service.user;

import java.util.List;

import _1.vietpq.job_hunter.exception.InValidException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;

public interface UserService {
    User handleGetUserByUsername(String username);
    User handleCreateUser(User user) throws InValidException;
    boolean isEmailExist(String email);
    User fetchUser (long id);
    ResultPaginationDTO fetchAllUsers(Specification<User> specification, Pageable pageable);
    void handleDeleteUser(long  id);
    User handleUpdateUser(User user);
    void updateUserToken(String token, String email);
    User fetchUserByRefreshTokenAndEmail(String refreshToken,String Email);

    void handleDeleteAllUsersInCompany(List<User> users);


}
