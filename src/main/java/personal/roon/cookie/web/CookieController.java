package personal.roon.cookie.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequestMapping("/cookie")
@Controller
public class CookieController {
    private static final int COOKIE_AGE = 60 * 60 * 24;

    @GetMapping
    public String cookiePage() {
        return "cookie";
    }

    @PostMapping
    public ModelAndView getCookie(@CookieValue(name = "visited_count", defaultValue = "0") int cookieValue, HttpServletResponse servletResponse) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cookie");

        Cookie cookie = new Cookie("visited_count", String.valueOf(cookieValue +1));
        cookie.setPath("/cookie");
        cookie.setMaxAge(COOKIE_AGE);

        servletResponse.addCookie(cookie);
        log.info(cookie.getName() + " " + cookie.getValue() + " " + cookie.getMaxAge());

        modelAndView.addObject("cookie", cookie);   //쿠키를 model에 담는거 왜 안 되지?
        return modelAndView;
    }
}
