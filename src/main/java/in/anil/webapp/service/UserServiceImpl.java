package in.anil.webapp.service;

import in.anil.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Created by ah00554631 on 6/6/2018.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    //	@Autowired
//	private UserDao userDao;
    private static Map<String,User> userMap=new HashMap<>();

    static {
        User user=new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu"); //password
        user.setAge(25);

        User user1=new User();
        user1.setId(2);
        user1.setUsername("anil");
        user1.setPassword("$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu"); //password
        user1.setAge(26);

        User user2=new User();
        user2.setId(3);
        user2.setUsername("anil2");
        user2.setPassword("anil2");
        user2.setAge(27);

        userMap.put(user.getUsername(),user);
        userMap.put(user1.getUsername(),user1);
        userMap.put(user2.getUsername(),user2);
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//		User user = userDao.findByUsername(userId);
        System.out.println(userMap.get(userName));
        User user = userMap.get(userName);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>(userMap.values());
//		userDao.findAll().iterator().forEachRemaining(list::add);
//		list= (List<User>) userMap.values();
        return list;
    }

    @Override
    public void delete(long id) {
//		userDao.delete(id);
        userMap.get(id);
    }

    @Override
    public User save(User user) {
//        return userDao.save(user);
        return userMap.put(user.getUsername(), user);
    }
}
