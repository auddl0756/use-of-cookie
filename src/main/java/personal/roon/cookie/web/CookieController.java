package personal.roon.cookie.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@RequestMapping("/cookie")
@Controller
public class CookieController {
    @GetMapping
    public String cookiePage() {
        return "cookie";
    }

    @PostMapping
    public ModelAndView getCookie(HttpServletRequest servletRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cookie");
        Cookie[] cookies = servletRequest.getCookies();

        for (Cookie cookie : cookies) {
            log.info(cookie.getName()+" "+cookie.getValue()+" "+cookie.getMaxAge());
        }

        modelAndView.addObject("cookies", cookies);

        return modelAndView;
    }
}
