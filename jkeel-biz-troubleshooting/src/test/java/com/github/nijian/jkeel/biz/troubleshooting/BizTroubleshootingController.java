package com.github.nijian.jkeel.biz.troubleshooting;

import com.github.nijian.jkeel.concept.Context;
import com.github.nijian.jkeel.concept.ContextAware;
import com.github.nijian.jkeel.concept.Role;
import com.github.nijian.jkeel.concept.User;
import com.github.nijian.jkeel.concept.json.JsonAppender;
import com.github.nijian.jkeel.spring.SpringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@ComponentScan(basePackages = "com.github.nijian.jkeel.spring")
public class BizTroubleshootingController implements ContextAware {

    @Autowired
    SpringManager manager;

    private static Logger logger = LoggerFactory.getLogger(BizTroubleshootingController.class);

    /**
     * 从性能的角度考虑，应该尽量减少前后端的交互。
     *
     * @param request
     * @return
     */
    @GetMapping("/initQueryContracts")
    public String initQueryContracts(@RequestParam(value = "name", defaultValue = "World") String request) {

        User user = new User() {
            @Override
            public Collection<Role> getRoles() {
                return null;
            }
        };

        Context<SpringManager> ctx = new Context(manager, user);
        JsonAppender<SpringManager, Context<SpringManager>> response = new JsonAppender(ctx);
        response.appendBy(request + "@CODE", request);
        return response.toString();

    }

}