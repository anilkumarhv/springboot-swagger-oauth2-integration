package in.anil.webapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Created by ah00554631 on 6/5/2018.
 */
@RestController
@RequestMapping("/home")
@Api(value="homes", description="Operations pertaining to Homes")
public class HomeRestController {
    private static final Logger logger= LoggerFactory.getLogger(HomeRestController.class);

    @ApiOperation(value = "View a list of available homes",position = 0)
    @RequestMapping(value = "/gethomes",method = RequestMethod.GET,produces = "application/json")
    public String getHomes(){
        logger.info("homesList");
        return "Get Homes list Success";
    }

    @ApiOperation(value = "View a list of available homes by id",position = 1)
    @RequestMapping(value = "/gethomes/{id}",method = RequestMethod.GET,produces = "application/json")
    public String getHomesById(@PathVariable(name = "id")  String id){
        logger.info("homes By Id"+id);
        return "Get Homes Success";
    }

    @ApiOperation(value = "add homes",position = 2)
    @RequestMapping(value = "/posthomes/{id}",method = RequestMethod.POST,produces = "application/json")
    public String postHomesById(@PathVariable(name = "id")  String id){
        logger.info("homes By Id"+id);
        return "Post Success";
    }

    @ApiOperation(value = "delete homes",position = 3)
    @RequestMapping(value = "/deletehomes/{id}",method = RequestMethod.DELETE,produces = "application/json")
    public String deleteHomesById(@PathVariable(name = "id")  String id){
        logger.info("homes By Id"+id);
        return "Delete Success";
    }

    @ApiOperation(value = "update available homes",position = 4)
    @RequestMapping(value = "/puthomes/{id}",method = RequestMethod.PUT,produces = "application/json")
    public String updateHomesById(@PathVariable(name = "id")  String id){
        logger.info("homes By Id"+id);
        return "Update Success";
    }
}
