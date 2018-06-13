package in.anil.webapp.service;

import in.anil.model.User;

import java.util.List;

/**
 *
 * Created by ah00554631 on 6/6/2018.
 */
public interface UserService {
    User save(User user);
    List<User> findAll();
    void delete(long id);
}
