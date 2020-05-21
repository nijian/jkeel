package com.github.nijian.jkeel.biz.troubleshooting;

import com.github.nijian.jkeel.commons.JsonReactor;
import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.User;
import com.github.nijian.jkeel.spring.SpringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
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
     * @param query
     * @return
     */


    @Transactional
    @RequestMapping(value = "/initQueryContracts", consumes = {"application/JSON"}, produces = {"application/JSON"}, method = RequestMethod.POST)
    public String initQueryContracts(@RequestHeader("jkeel-org-id") String orgId, @RequestBody Query query, Authentication authentication) {

        JsonReactor reactor = new JsonReactor();

//        String userName = authentication.getName();
        User user = new User(orgId);
        ServiceContext ctx = ServiceContext.newInstance(manager, user, "initQueryContracts");
        reactor.appendContext(ctx);

        ctx = ServiceContext.newInstance(manager, user, "queryContracts");
        reactor.appendContext(ctx);

        return reactor.responseTo(query);
    }

    @Transactional
    @RequestMapping(value = "/queryContracts", consumes = {"application/JSON"}, produces = {"application/JSON"}, method = RequestMethod.POST)
    public String queryContracts(@RequestHeader("jkeel-org-id") String orgId, @RequestBody Query query, Authentication authentication) {
        JsonReactor reactor = new JsonReactor();

        User user = new User(orgId);
        ServiceContext ctx = ServiceContext.newInstance(manager, user, "queryContracts");
        reactor.appendContext(ctx);

        return reactor.responseTo(query);
    }

}