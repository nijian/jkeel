package com.github.nijian.jkeel.biz.troubleshooting;

import com.github.nijian.jkeel.commons.JsonAppender;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.User;
import com.github.nijian.jkeel.spring.SpringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "com.github.nijian.jkeel.spring")
public class BizTroubleshootingController {

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
    public String initQueryContracts(@RequestHeader("jkeel-org-id") String orgId,
                                     @RequestParam(value = "name", defaultValue = "World") String request,
                                     Authentication authentication) {

        JsonAppender response = new JsonAppender();

//        String userName = authentication.getName();
        User user = new User(orgId);
        ServiceContext<SpringManager> ctx = new ServiceContext<>(manager, user,
                "initQueryContracts", request);
        response.appendBy(ctx);

        ServiceContext<SpringManager> ctx1 = new ServiceContext<>(manager, user,
                "initQueryContracts 22222", request);
        response.appendBy(ctx1);

        return response.toString();
    }

}