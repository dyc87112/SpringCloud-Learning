package com.didispace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 翟永超
 * @create 2017/4/15.
 * @blog http://blog.didispace.com
 */
@Slf4j
@RestController
public class UserController {

    private static Map<Long, String> users = new HashMap<>();

    static {
        users.put(1L, "aaa");
        users.put(2L, "bbb");
        users.put(3L, "ccc");
        users.put(4L, "ddd");
        users.put(5L, "eee");
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable Long id) {
        log.info("findById : " + id);
        return users.get(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<String> findByIds(@RequestParam String ids) {
        log.info("findByIds : " + ids);
        List<String> result = new ArrayList<>();
        for(String id : ids.split(",")) {
            if(users.get(Long.valueOf(id)) != null)
                result.add(users.get(Long.valueOf(id)));
        }

        log.info("findByIds : " + result);
        return result;
    }

}
