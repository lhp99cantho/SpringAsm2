package com.example.asg2.Controller;


import com.example.asg2.Entity.Content;
import com.example.asg2.Service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @GetMapping("/view")
    public String getContent(Model model, @RequestParam(name = "search", required = false, defaultValue = "0") String search, Authentication authentication) {
        List<Content> list = new ArrayList<>();
        if(!search.equals("0")) {
            list = contentService.getContentByTitle(search);
        } else {
            list = contentService.getAllContent();
        }
        model.addAttribute("list", list);
        return "ViewContent";
    }

    @GetMapping("/add")
    public String addContent(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "AddContent";
    }

    @GetMapping("/edit/{idContent}")
    public String editContent(@PathVariable int idContent, Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        Content content = contentService.getContentById(idContent, username);
        model.addAttribute("username", username);
        if (content != null) {
            model.addAttribute(content);
            return "EditContent";
        } else {
            return "4nf";
        }
    }

    //Create content
    @PostMapping
    public ResponseEntity<HashMap<String, String>> createContent(@RequestBody Content content, Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        boolean rs = contentService.createContent(content, username);
        String message = rs ? "Successfully created." : "Error creating content.";
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("message", message);
        return ResponseEntity.ok(res);
    }

    //Update content
    @PutMapping
    public ResponseEntity<HashMap<String, String>> updateContent(@RequestBody Content content) {
        boolean rs = contentService.updateContent(content);
        String message = rs ? "Successfully updated." : "Error update content.";
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("message", message);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, String>> deleteContent(@RequestBody int id) {
        contentService.deleteContent(id);
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("message", "Deleted content.");
        return ResponseEntity.ok(res);
    }
}
