package com.timsanalytics.cicd.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/v1/diagnostics")
public class DiagnosticsController {

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getTest() {
        String message = "Success! Response";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            message += " from host: " + ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getName() {
        return "This is the 'cicd' app. (1)";
    }

}

