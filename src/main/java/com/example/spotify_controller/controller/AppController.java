package com.example.spotify_controller.controller;

import com.example.spotify_controller.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {
    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    // ----------------------------- Test Endpoints -----------------------------
    @GetMapping("/ping")
    @ResponseBody
    public String ping() {
        return "pong";
    }

    @PostMapping("/ping")
    @ResponseBody
    public String postPing() {
        return "pong, but post";
    }

    @PutMapping("/ping")
    @ResponseBody
    public String putPing() {
        return "pong, but put";
    }

    @GetMapping("/echo")
    @ResponseBody
    public String echo(@RequestParam String str) {
        return str;
    }

    @GetMapping("/auth")
    @ResponseBody
    public String auth() {
        String authURI = appService.getAuthUri();
        return authURI;
    }

    // -------------------------- Functional Endpoints --------------------------
    @PutMapping("/init-access-token")
    @ResponseBody
    public String initAuth(@RequestParam String accessToken) {
        appService.initAccessToken(accessToken);
        return "Initialized Auth Code";
    }

    @GetMapping("/get-access-token")
    @ResponseBody
    @Deprecated
    public String saveAuth() {
        appService.getAccessToken();
        return "Saved Auth";
    }

    @GetMapping("/callback")
    @ResponseBody
    public String spotifyCallback(@RequestParam String code) {
        appService.initCode(code);
        appService.getAccessToken();
        return "I gotchu fam";
    }

    @GetMapping("/refresh-access-token")
    @ResponseBody
    public String refreshAuth() {
        appService.refreshCode();
        return "Refreshed Auth";
    }

    @GetMapping("/play")
    @ResponseBody
    public String togglePlay() {
        appService.togglePlay();
        return "Toggled Play";
    }

    @GetMapping("/pause")
    @ResponseBody
    public String togglePause() {
        appService.togglePause();
        return "Toggled Pause";
    }

    @GetMapping("/next-track")
    @ResponseBody
    public String nextTrack() {
        appService.nextTrack();
        return "Next Track";
    }

    @GetMapping("/previous-track")
    @ResponseBody
    public String previousTrack() {
        appService.previousTrack();
        return "Previous Track";
    }
}
