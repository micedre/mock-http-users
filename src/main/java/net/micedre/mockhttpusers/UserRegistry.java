package net.micedre.mockhttpusers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;


@Component
public class UserRegistry {
    private static Map<String, MockUserModel> users = new HashMap<>();

    static{
        MockUserModel user1 = new MockUserModel();
        user1.setEmail(("user1@test.fr"));
        user1.setUsername("user1");
        MockUserModel user2 = new MockUserModel();
        user2.setEmail(("user2@test.fr"));
        user2.setUsername("user2");
        users.put("user1", user1 );
        users.put("user2", user2 );

    }
    public List<MockUserModel> getUsers() {
        return users.entrySet()
                    .stream()
                    .map(e -> e.getValue())
                    .collect(Collectors.toList());
    }
	public void addUser(MockUserModel user) {
        users.put(user.getUsername(), user);
	}
	public MockUserModel getUserByUsername(String username) {
		return users.get(username);
	}

}
