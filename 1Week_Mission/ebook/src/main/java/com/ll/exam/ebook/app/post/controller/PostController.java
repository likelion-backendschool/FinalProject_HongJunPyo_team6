package com.ll.exam.ebook.app.post.controller;

import com.ll.exam.ebook.app.post.entity.Post;
import com.ll.exam.ebook.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String showList(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "search_subject")String type) {
        Page<Post> paging = postService.getList(page);
        model.addAttribute("paging", paging);
        model.addAttribute("type",type);

        return "post/list";
    }
}
