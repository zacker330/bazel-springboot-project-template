package codes.showme.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping("index")
    public String index(){
        return "index";
    }
}
