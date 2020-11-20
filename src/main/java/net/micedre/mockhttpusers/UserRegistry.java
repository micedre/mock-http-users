package net.micedre.mockhttpusers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;


@Component
public class UserRegistry {
    private static Map<String, MockUserModel> users = new HashMap<>();

    static{
        MockUserModel user1 = new MockUserModel("id1");
        user1.setEmail(("user1@test.fr"));
        user1.setUsername("user1");
        user1.setPassword("user");
        user1.setFirstName("Héhé");
        user1.setLastName("Haha");
        user1.setMood("Happy!!");
        MockUserModel user2 = new MockUserModel("id2");
        user2.setEmail(("user2@test.fr"));
        user2.setUsername("user2");
        users.put("user1", user1 );
        users.put("user2", user2 );

    }
    public List<MockUserModel> getUsers() {
        return users.entrySet()
                    .stream()
                .map(Entry::getValue)
                    .collect(Collectors.toList());
    }

	public void addUser(MockUserModel user) {
        users.put(user.getUsername(), user);
    }

	public MockUserModel getUserByUsername(String username) {
        Optional<MockUserModel> user = users.entrySet().stream()
                .filter(e -> e.getValue().getUsername().equalsIgnoreCase(username)).map(Map.Entry::getValue)
                .findFirst();
        return user.orElse(null);
    }

    public MockUserModel getUserById(String id) {
        return users.get(id);
    }

    public MockUserModel getUserByEmail(String email) {
        Optional<MockUserModel> user = users.entrySet().stream()
                .filter(e -> e.getValue().getEmail().equalsIgnoreCase(email)).map(Map.Entry::getValue).findFirst();
        return user.orElse(null);
    }

    public boolean updateCredential(String id, String oldPassword, String newPassword) {
        MockUserModel user = users.get(id);

        if (oldPassword != null && !oldPassword.equals(user.getPassword())) {
            return false;
        }

        user.setPassword(newPassword);
        return true;
    }
}
