package co.develhope.crudTest.unittest1.services;

import co.develhope.crudTest.unittest1.entities.User;
import co.develhope.crudTest.unittest1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user){
        userRepository.save(user);
        return user;
    }

    public User getOne(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new Exception("User not found");
        } else {
            return user.get();
        }
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User update(Long id, String name, String surname, Integer age, boolean isActive){
        User user;
        if (userRepository.existsById(id)){
            user = userRepository.getById(id);
            user.setName(name);
            user.setSurname(surname);
            user.setAge(age);
            user.setActive(isActive);
           return userRepository.save(user);
        } user = new User();

        return user;
    }

    public String deleteOne(Long id) throws Exception {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        } else {
            throw new Exception("User not found");
        }
        return "The user whit id: " + id + " has been eliminated";
    }

    public String deleteAll(){
        userRepository.deleteAll();
        return  "The users has been eliminated";
    }
}
