package com.github.nijian.jkeel.biz.troubleshooting;

import com.github.nijian.jkeel.commons.JsonReactor;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.User;
import com.github.nijian.jkeel.query.entity.EntityIdentifier;
import com.github.nijian.jkeel.query.entity.QueryRequest;
import com.github.nijian.jkeel.spring.SpringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@ComponentScan(basePackages = "com.github.nijian.jkeel.spring")
@Component
public class BizTroubleshootingController {

    @Autowired
    SpringManager manager;

    private static Logger logger = LoggerFactory.getLogger(BizTroubleshootingController.class);

    @Transactional
    @RequestMapping(value = "/initQueryContracts", consumes = {"application/JSON"}, produces = {"application/JSON"}, method = RequestMethod.POST)
    public String initQueryContracts(@RequestHeader("jkeel-org-id") String orgId, @RequestBody EntityIdentifier entityIdentifier, Authentication authentication) {
        logger.info("Initializing queryRequest contracts");
        JsonReactor reactor = new JsonReactor();

//        String userName = authentication.getName();
        User user = new User(orgId);

        ServiceContext ctx = ServiceContext.newInstance(manager, user, "initQueryContracts");
        reactor.appendContext(ctx);

        return reactor.responseTo(entityIdentifier);
    }

    @Transactional
    @RequestMapping(value = "/queryContracts", consumes = {"application/JSON"}, produces = {"application/JSON"}, method = RequestMethod.POST)
    public String queryContracts(@RequestHeader("jkeel-org-id") String orgId, @RequestBody QueryRequest queryRequest, Authentication authentication) {
        JsonReactor reactor = new JsonReactor();

        User user = new User(orgId);

        ServiceContext ctx = ServiceContext.newInstance(manager, user, "queryContracts");
        reactor.appendContext(ctx);

        return reactor.responseTo(queryRequest);
    }

    @Transactional
    @RequestMapping(value = "/loadContract", consumes = {"application/JSON"}, produces = {"application/JSON"}, method = RequestMethod.POST)
    public String loadContract(@RequestHeader("jkeel-org-id") String orgId, @RequestBody EntityIdentifier entityIdentifier, Authentication authentication) {
        JsonReactor reactor = new JsonReactor();

        User user = new User(orgId);

        ServiceContext ctx = ServiceContext.newInstance(manager, user, "load contract information");
        reactor.appendContext(ctx);

        return reactor.responseTo(entityIdentifier);
    }

}