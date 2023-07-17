package com.sprinklr.InternProjectBot.Controller;

import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/microsoft")
public class MicrosoftController {
    @Autowired
    @Qualifier("dataAccessLayerImplMongoDBAtlas")
    private DataAccessLayer dataAccessLayer;
    @GetMapping("/user/unsubscribe/{id}")
    public String unsubscribe(@PathVariable String id){
        dataAccessLayer.unsubscribeUserId(id);
        return "Succesfully unsubscribed";
    }
    @GetMapping("/user/subscribe/{id}")
    public String subscribe(@PathVariable String id){
        dataAccessLayer.subscribeUserId(id);
        return "Succesfully subscribed";
    }

}
