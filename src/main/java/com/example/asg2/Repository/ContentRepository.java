package com.example.asg2.Repository;

import com.example.asg2.Entity.Content;
import com.example.asg2.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Integer> {
    List<Content> getAllByTitle(String title);
    Optional<Content> getContentByContentIdAndMember(int id, Member member);
}
