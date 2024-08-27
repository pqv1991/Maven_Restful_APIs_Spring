package _1.vietpq.job_hunter.service.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import _1.vietpq.job_hunter.dto.convertToDTO.ConvertToUserDTO;
import _1.vietpq.job_hunter.exception.DuplicatedException;
import _1.vietpq.job_hunter.exception.InValidException;
import _1.vietpq.job_hunter.exception.NotNullException;
import _1.vietpq.job_hunter.exception.message.UserMessage;
import _1.vietpq.job_hunter.util.validator.UserValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.dto.user.ResUserDTO;
import _1.vietpq.job_hunter.repository.UserRepository;
import _1.vietpq.job_hunter.service.company.CompanyService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, CompanyService companyService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User handleGetUserByUsername(String username) {
        return userRepository.findByEmail(username);
       
    }

    @Override
    public User handleCreateUser(User user) throws DuplicatedException, InValidException {
        if(isEmailExist(user.getEmail()))
            throw new DuplicatedException(UserMessage.EMAIL_ALREADY_EXIST);
        if(user.getCompany() != null){
            Optional<Company>  comOptional = companyService.fetchCompanyById(user.getCompany().getId());
            user.setCompany(comOptional.orElse(null));
        }

        UserValidator.validatorUser(user);

        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return userRepository.save(user);
    }

    @Override
    public boolean isEmailExist(String email) {
       return userRepository.existsByEmail(email);
    }

    @Override
    public User fetchUser(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
       return null;
    }

    @Override
    public ResultPaginationDTO fetchAllUsers(Specification<User> specification, Pageable pageable) {
        Page<User> pageUser = userRepository.findAll(specification, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber()+1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());
        resultPaginationDTO.setMeta(mt);
        List<ResUserDTO> listDTO = pageUser.getContent().stream().map(ConvertToUserDTO::convertResUserDTO).collect(Collectors.toList());
        resultPaginationDTO.setResult(listDTO);
        return  resultPaginationDTO;
    }

    @Override
    public void handleDeleteUser(long id) {
         userRepository.deleteById(id);
    }

    @Override
    public User handleUpdateUser(User user) {

        if( user == null || user.getId() <= 0){
            throw  new NotNullException(UserMessage.NOT_FOUND);
        }

        User existingUser = fetchUser(user.getId());

        if( existingUser == null)  return  null;
        existingUser.setName(user.getName());
        existingUser.setAge(user.getAge());
        existingUser.setAddress(user.getAddress());
        existingUser.setGender(user.getGender());

        if(user.getCompany() != null){
                Optional<Company>  comOptional = companyService.fetchCompanyById(user.getCompany().getId());
                existingUser.setCompany(comOptional.orElse(null));
            }
        return userRepository.save(existingUser);
    }

    @Override
    public void updateUserToken(String token, String email) {
        User currentUser = handleGetUserByUsername(email);
        if(currentUser != null){
            currentUser.setRefreshToken(token);
            userRepository.save(currentUser);
        }
    }

    @Override
    public User fetchUserByRefreshTokenAndEmail(String refreshToken, String Email) {
        return userRepository.findByRefreshTokenAndEmail(refreshToken, Email);
        
    }

    @Override
    public void handleDeleteAllUsersInCompany(List<User> users) {
       userRepository.deleteAll(users);
    }
    
}
