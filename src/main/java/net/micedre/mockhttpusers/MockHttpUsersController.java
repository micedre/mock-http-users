package net.micedre.mockhttpusers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MockHttpUsersController {

    @Autowired
    UserRegistry userRegistry;

    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<MockUserModel> create(MockUserModel user){
        userRegistry.addUser(user);
        return new ResponseEntity<MockUserModel>(user, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public MockUserModel getById(@PathVariable("id") String id) {
        return userRegistry.getUserById(id);
    }

    @GetMapping("/users")
    @ResponseBody
    public List<MockUserModel> getByUsername(@RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "email", required = false) String email) {
        if (username != null) {
            return Arrays.asList(userRegistry.getUserByUsername(username));
        }

        if (email != null) {
            return Arrays.asList(userRegistry.getUserByEmail(email));
        }

        return userRegistry.getUsers();

    }

    @PostMapping(path = "/users/validate-credential", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<String> validate(@RequestBody MultiValueMap<String, String> formData) {
        if (userRegistry.getUserByUsername(formData.getFirst("username")).getPassword()
                .equals(formData.getFirst("password"))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/users/{id}/update-credential", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable("id") String id,
            @RequestBody MultiValueMap<String, String> formData) {
        if (userRegistry.updateCredential(id, formData.getFirst("oldPassword"), formData.getFirst("newPassword"))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}