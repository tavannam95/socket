package com.example.socketdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/socket")
public class MessageController {

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @RequestMapping("/send")
    public void send(@RequestParam String content){
//        simpMessagingTemplate.convertAndSendToUser("namtv","/topic/messages", content);
        Set<SimpUser> listSimpUser = simpUserRegistry.getUsers();
        Map<Object, Object> data = new HashMap<>();
//      Set message data
        data.put("data", content);
        for (SimpUser simpUser : listSimpUser) {
//            simpMessagingTemplate.convertAndSendToUser(simpUser.getName(), "/topic/messages", data);
            simpMessagingTemplate.convertAndSendToUser("namtv", "/topic/messages", data);
        }
    }

}
