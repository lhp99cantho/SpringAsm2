package com.example.asg2.Repository;

import com.example.asg2.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findMemberByUsername(String name);
}
