package net.micedre.mockhttpusers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MockHttpUsersController {

    @Autowired
    UserRegistry userRegistry;

    @GetMapping("/users")
    @ResponseBody
    public List<MockUserModel> search() {
        return userRegistry.getUsers();
    }

    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<MockUserModel> create(MockUserModel user){
        userRegistry.addUser(user);
        return new ResponseEntity<MockUserModel>(user, HttpStatus.CREATED);
    }

    @GetMapping("/users/{username}")
    @ResponseBody
    public MockUserModel get(@PathVariable("username") String username){
        return userRegistry.getUserByUsername(username);
    }

    @PostMapping("/users/{username}/validate-credential")
    @ResponseBody
    public ResponseEntity<String> validate(@PathVariable("username") String username, String password){
        if(userRegistry.getUserByUsername("user1").getPassword().equals(password)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}