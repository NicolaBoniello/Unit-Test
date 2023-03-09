package co.develhope.crudTest.unittest1.controllers;

import co.develhope.crudTest.unittest1.entities.User;
import co.develhope.crudTest.unittest1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public User create(@RequestBody User user){
        userService.create(user);
        return user;
    }

    @GetMapping("")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id){
        try {
            return userService.getOne(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestParam String name, @RequestParam String surname, @RequestParam Integer age,
                       @RequestParam boolean isActive){
        return userService.update(id, name, surname, age, isActive);
    }

    @DeleteMapping("")
    public String deleteAll(){
        userService.deleteAll();
        return  "All user has been eliminated";
    }

    @DeleteMapping("/{id}")
    public String deleteOne(@PathVariable Long id){
        try {
             userService.deleteOne(id);
             return "The user have been eliminated";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
