package personal.roon.cookie.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RequestMapping("/cart")
@Controller
public class CartController {
    private static final int COOKIE_AGE = 60 * 60 * 24;
    private static final String COOKIE_NAME = "cart_list";
    private static final String COOKIE_VISIBLE_PATH = "/cart";

    private static final String REQUEST_COUNT_COOKIE = "visit_count";

    @Autowired
    private CookieUtil cookieUtil;

    @GetMapping
    public String cartPage(@CookieValue(name = COOKIE_NAME, defaultValue = "", required = false) String cartList, HttpServletResponse servletResponse) {
        Cookie cookie = cookieUtil.makeCookie(COOKIE_NAME, cartList, COOKIE_VISIBLE_PATH, COOKIE_AGE);
        servletResponse.addCookie(cookie);
        return "cart";
    }

    private static Set<String> cartSet = new HashSet<>();

    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProduct(@RequestBody CartDto cartDto,
                           HttpServletRequest servletRequest,
                           @CookieValue(name = COOKIE_NAME, defaultValue = "", required = false) String cartList,
                           @CookieValue(name = REQUEST_COUNT_COOKIE, defaultValue = "0", required = false) String requestCount,
                           HttpServletResponse servletResponse) {
        String uuid = cartDto.getUuid();
        boolean isAdded = cartDto.isAdded();
        log.info(cartDto);
        log.info("session id = " + servletRequest.getRequestedSessionId());


        if (isAdded) {
            cartSet.add(uuid);
        } else {
            cartSet.remove(uuid);
        }

        String strCartList = cartSet.stream().collect(Collectors.joining("_"));

        log.info(strCartList + " " + cartSet.size());

        Cookie cookie = cookieUtil.makeCookie(COOKIE_NAME, strCartList, COOKIE_VISIBLE_PATH, COOKIE_AGE);
        Cookie requestCountCookie = cookieUtil.makeCookie(REQUEST_COUNT_COOKIE, String.valueOf(Integer.parseInt(requestCount) + 1), COOKIE_VISIBLE_PATH, COOKIE_AGE);

        servletResponse.addCookie(cookie);
        servletResponse.addCookie(requestCountCookie);

        servletResponse.setHeader("Set-Cookie", "test-cookie=test-value");

    }
}
