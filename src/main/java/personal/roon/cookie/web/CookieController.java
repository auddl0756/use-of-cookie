package personal.roon.cookie.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private static final String COOKIE_NAME = "visited_count";
    private static final String COOKIE_VISIBLE_PATH = "/cookie";

    @Autowired
    private CookieUtil cookieUtil;

    @GetMapping
    public String cookiePage() {
        return "cookie";
    }

    @PostMapping
    public ModelAndView getCookie(@CookieValue(name = COOKIE_NAME, defaultValue = "0") int cookieValue, HttpServletResponse servletResponse) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cookie");

        Cookie cookie = cookieUtil.makeCookie(COOKIE_NAME, String.valueOf(cookieValue + 1), COOKIE_VISIBLE_PATH, COOKIE_AGE);

        servletResponse.addCookie(cookie);
        log.info(cookie.getName() + " " + cookie.getValue() + " " + cookie.getMaxAge());

        modelAndView.addObject("cookie", cookie);   //쿠키를 model에 담는거 왜 안 되지?
        return modelAndView;
    }

    @DeleteMapping
    public String initCookie(HttpServletResponse servletResponse) {
        Cookie cookie = cookieUtil.makeCookie(COOKIE_NAME, "", COOKIE_VISIBLE_PATH, 0);
        servletResponse.addCookie(cookie);

        log.info(cookie.getName() + " " + cookie.getValue() + " " + cookie.getMaxAge());

        return "cookie";
    }
}
