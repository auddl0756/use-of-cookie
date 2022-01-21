package personal.roon.cookie.web;

import javax.servlet.http.Cookie;

public class CookieUtil {
    public Cookie makeCookie(String name, String value, String path, int age){
        Cookie ret = new Cookie(name,value);
        ret.setPath(path);
        ret.setMaxAge(age);
        return ret;
    }
}
