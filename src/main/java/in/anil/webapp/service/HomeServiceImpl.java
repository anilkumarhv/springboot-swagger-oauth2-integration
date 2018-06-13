package in.anil.webapp.service;

import org.springframework.stereotype.Service;

/**
 *
 * Created by ah00554631 on 6/4/2018.
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Override
    public String getMessage() {
        return "Welcome to Spring Developer page....";
    }
}
