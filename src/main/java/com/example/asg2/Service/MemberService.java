package com.example.asg2.Service;

import com.example.asg2.Entity.Member;
import com.example.asg2.Repository.ContentRepository;
import com.example.asg2.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public interface MemberService {
    boolean createMember(Member member);
    boolean updateMember(Member member);
    void deleteMember(int id);

    Member getMemberByUsername(String username);
    Member getMemberById(int id);

}
