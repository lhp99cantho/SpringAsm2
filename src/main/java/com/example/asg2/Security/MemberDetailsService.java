package com.example.asg2.Security;

import com.example.asg2.Entity.Member;
import com.example.asg2.Security.MemberSecurity;
import com.example.asg2.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.getMemberByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("Member Not Found!");
        }
        MemberSecurity memberSecurity = new MemberSecurity(member);
        return memberSecurity;
    }
}
