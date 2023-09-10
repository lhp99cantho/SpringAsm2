package com.example.asg2.Service.Impl;

import com.example.asg2.Entity.Member;
import com.example.asg2.Repository.MemberRepository;
import com.example.asg2.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public boolean createMember(Member member) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            if (member != null) {
                String hashedPassword = passwordEncoder.encode(member.getPassword());
                member.setPassword(hashedPassword);
                memberRepository.save(member);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMember(Member member) {
        int id = member.getMemberId();
        Optional<Member> result = memberRepository.findById(id);
        if (result.isPresent()) {
            Member dbMember = result.get();
            dbMember.setFirstName(member.getFirstName());
            dbMember.setLastName(member.getLastName());
            dbMember.setDescription(member.getDescription());
            dbMember.setPhone(member.getPhone());
            memberRepository.save(dbMember);
            return true;
        }
        return false;
    }

    @Override
    public void deleteMember(int id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Member getMemberById(int id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null);
    }

    @Override
    public Member getMemberByUsername(String username) {
        return memberRepository.findMemberByUsername(username);
    }
}
