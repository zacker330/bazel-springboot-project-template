package codes.showme.server.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping("index")
    public String index(){
        return "index";
    }
}
