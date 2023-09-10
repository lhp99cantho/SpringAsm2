package com.example.asg2.Controller;

import com.example.asg2.Entity.Member;
import com.example.asg2.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Autowired
    protected MemberService memberService;

    //Register member
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Member member) {
        boolean rs = memberService.createMember(member);
        Map<String, String> res = new HashMap<>();
        if (rs) {
            res.put("message","Registered successfully.");
        } else {
            res.put("message","Registering failed.");
        }
        return ResponseEntity.ok(res);
    }

    //Update member
    @PutMapping("/members")
    protected ResponseEntity<Map<String, String>> updateMember(@RequestBody Member member) {
        boolean rs = memberService.updateMember(member);
        Map<String, String> res = new HashMap<>();
        if (rs) {
            res.put("message","Updated successfully.");
        } else {
            res.put("message","Update failed.");
        }
        return ResponseEntity.ok(res);
    }
}
