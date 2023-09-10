package com.example.asg2.Controller;

import com.example.asg2.Entity.Member;
import com.example.asg2.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class IndexController {
    @Autowired
    protected MemberService memberService;
    @GetMapping("/")
    public String index() {
        return "Index";
    }
    @GetMapping("/login")
    public String login() {
        return "Login";
    }
    @GetMapping("/register")
    public String register() {
        return "Register";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        Member member = memberService.getMemberByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute(member);
        return "EditProfile";
    }

}
