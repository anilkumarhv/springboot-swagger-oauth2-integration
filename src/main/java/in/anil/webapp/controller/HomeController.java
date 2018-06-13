package in.anil.webapp.controller;

import in.anil.webapp.service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Created by ah00554631 on 6/4/2018.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    HomeService homeService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getHome() {
        logger.debug("====home====");
        System.out.println(homeService.getMessage());

        return "home";
    }
}
