package com.example.asg2.Service;

import com.example.asg2.Entity.Content;
import com.example.asg2.Entity.Member;

import java.util.List;

public interface ContentService {
    boolean createContent(Content content, String username);
    boolean updateContent(Content content);
    void deleteContent(int id);
    Content getContentById(int id, String username);
    List<Content> getAllContent();

    List<Content> getContentByTitle(String title);
}
