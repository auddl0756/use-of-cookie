package personal.roon.cookie.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
    public void addProduct(@RequestBody CartDto cartDto, @CookieValue(name = COOKIE_NAME, defaultValue = "", required = false) String cartList, HttpServletResponse servletResponse) {
        String uuid = cartDto.getUuid();
        boolean isAdded = cartDto.isAdded();
        log.info(cartDto);

        if(isAdded){
            cartSet.add(uuid);
        }else{
            cartSet.remove(uuid);
        }

        String strCartList = cartSet.stream().collect(Collectors.joining("_"));

        log.info(strCartList+" "+cartSet.size());

        Cookie cookie = cookieUtil.makeCookie(COOKIE_NAME, strCartList, COOKIE_VISIBLE_PATH, COOKIE_AGE);
        servletResponse.addCookie(cookie);
    }
}
