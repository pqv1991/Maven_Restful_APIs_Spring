package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.exception.message.UserMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.dto.convertToDTO.ConvertToUserDTO;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.dto.user.ResCreateUserDTO;
import _1.vietpq.job_hunter.dto.user.ResUpdateUserDTO;
import _1.vietpq.job_hunter.dto.user.ResUserDTO;
import _1.vietpq.job_hunter.exception.InValidException;
import _1.vietpq.job_hunter.service.user.UserService;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final   UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public ResponseEntity<ResCreateUserDTO> createUser(@RequestBody User user) throws InValidException {
        User newUser = userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ConvertToUserDTO.convertResCreateUserDTO(newUser));
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") long id) throws InValidException {
        User user = userService.fetchUser(id);
        if(user == null) {
            throw  new InValidException(UserMessage.NOT_FOUND);
        }
        return ResponseEntity.ok().body(ConvertToUserDTO.convertResUserDTO(user));
    }
    
    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> getAllUsers(@Filter Specification<User> specification, Pageable pageable){
        return ResponseEntity.ok().body(userService.fetchAllUsers(specification, pageable));
    }


   @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) throws InValidException {
        User user = userService.fetchUser(id);
        if(user == null) {
            throw  new InValidException(UserMessage.NOT_FOUND);
        }
        userService.handleDeleteUser(id);
        return ResponseEntity.ok().body(null);
    }

   @PutMapping("/users")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws InValidException {
        User userData = userService.fetchUser(user.getId());
        if(userData == null) {
            throw  new InValidException(UserMessage.NOT_FOUND);
        }
        User userUpdate = userService.handleUpdateUser(user);
        return ResponseEntity.ok().body(ConvertToUserDTO.convertResUpdateUserDTO(userUpdate));
    }
   

    
}
