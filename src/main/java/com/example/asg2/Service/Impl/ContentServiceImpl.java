package com.example.asg2.Service.Impl;

import com.example.asg2.Entity.Content;
import com.example.asg2.Entity.Member;
import com.example.asg2.Repository.ContentRepository;
import com.example.asg2.Repository.MemberRepository;
import com.example.asg2.Service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public boolean createContent(Content content, String username) {
        Member member = memberRepository.findMemberByUsername(username);
        if (member != null) {
            content.setMember(member);
            contentRepository.save(content);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateContent(Content content) {
        int id = content.getContentId();
        Optional<Content> result = contentRepository.findById(id);
        if (result.isPresent()) {
            Content dbContent = result.get();
            dbContent.setTitle(content.getTitle());
            dbContent.setBrief(content.getBrief());
            dbContent.setContent(content.getContent());
            contentRepository.save(dbContent);
            return true;
        }
        return false;
    }

    @Override
    public void deleteContent(int id) {
        Optional<Content> content = contentRepository.findById(id);
        if (content.isPresent()) {
            Content dbContent = content.get();
            contentRepository.delete(dbContent);
        }
    }

    @Override
    @PreAuthorize("#username == authentication.principal.getMember().getUsername()")
    public Content getContentById(int id, String username) {
        Content dbContent = null;
        Member member = memberRepository.findMemberByUsername(username);
        Optional<Content> result = contentRepository.getContentByContentIdAndMember(id, member);
        if (result.isPresent()) {
            dbContent = result.get();
            return dbContent;
        }
        return dbContent;
    }

    @Override
    public List<Content> getAllContent() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        Member member = memberRepository.findMemberByUsername(username);
        if (member != null ) {
            return member.getListOfContent();
        } else {
            return null;
        }
    }

    @Override
    public List<Content> getContentByTitle(String title) {
        List<Content> list = contentRepository.getAllByTitle(title);
        return list;
    }
}
