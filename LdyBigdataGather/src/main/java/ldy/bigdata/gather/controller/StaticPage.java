package ldy.bigdata.gather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticPage {
    static final Logger log = LoggerFactory.getLogger(StaticPage.class);

    @RequestMapping(value = {"/index.html"}, method = RequestMethod.GET)
    public String index() {
        log.info("in index");
        return "index";
    }

    @GetMapping("/second")
    public ModelAndView second(Model model) {
        log.info("in second");
        model.addAttribute("name", "李兆龙");
        return new ModelAndView("second", "second", model);
    }

    @GetMapping("/gatherTableInfo")
    public ModelAndView gatherTableInfo(Model model) {
        log.info("in gatherTableInfo");
        return new ModelAndView("tableInfo", "second", model);
    }
}
