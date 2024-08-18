package _1.vietpq.job_hunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.repository.UserRepository;
@Service
public class UserService {
    private  final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> fetchAllUsers(){
        List<User>  listUsers = userRepository.findAll();
        return listUsers;
  }
     public User fetchUserById(long id){
       Optional<User> user = userRepository.findById(id);
        return user.get();
    }  
    public void handleDeleteUser(long id){
        userRepository.deleteById(id);
    } 

    public User updateUser(User user){
        User userUpdate = fetchUserById(user.getId());
        if(user != null){
            userUpdate.setEmail(user.getEmail());
            userUpdate.setUsername(user.getUsername());
            userUpdate.setPassword(user.getPassword());
            userUpdate = userRepository.save(userUpdate);
        }
        return userUpdate;
   }
   public User handleGetUserByUsername(String username){
    return  this.userRepository.findByEmail(username);
   }
    
}
