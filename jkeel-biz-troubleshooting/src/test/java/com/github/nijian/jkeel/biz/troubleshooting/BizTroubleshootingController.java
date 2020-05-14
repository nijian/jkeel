package com.github.nijian.jkeel.biz.troubleshooting;

import com.github.nijian.jkeel.commons.JsonAppender;
import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.User;
import com.github.nijian.jkeel.spring.SpringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

        return response.toString();
    }

    @RequestMapping(value = "/queryContracts", consumes = {"application/JSON"}, produces = {"application/JSON"}, method = RequestMethod.POST)
    public String queryContracts(@RequestHeader("jkeel-org-id") String orgId, @RequestBody Query query) {
        JsonAppender response = new JsonAppender();

        User user = new User(orgId);
        ServiceContext<SpringManager> ctx = new ServiceContext<>(manager, user,
                "queryContracts", query);
        response.appendBy(ctx);

        return response.toString();
    }

}