package com.ll.exam.ebook.app.post.controller;

import com.ll.exam.ebook.app.post.entity.Post;
import com.ll.exam.ebook.app.post.exception.DataNotFoundException;
import com.ll.exam.ebook.app.post.form.PostForm;
import com.ll.exam.ebook.app.post.form.PostModifyForm;
import com.ll.exam.ebook.app.post.service.PostService;
import com.ll.exam.ebook.app.security.dto.MemberContext;
import com.ll.exam.ebook.app.util.Ut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;

//    @GetMapping("/list")
//    public String showList(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "search_subject")String type) {
//        Page<Post> paging = postService.getList(page);
//        model.addAttribute("paging", paging);
//        model.addAttribute("type",type);
//
//        return "post/list";
//    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showList(Model model) {
        List<Post> posts = postService.findAllPost();
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable long id,@AuthenticationPrincipal MemberContext memberContext) {
        Post post = postService.getPost(id);
        String username = memberContext.getUsername();
        if (post.getAuthor().getUsername().equals(username)) {
            model.addAttribute("access","true");
        }
        model.addAttribute("post", post);

        return "post/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String articleCreate(PostForm postForm, Principal principal) {

        if (principal==null) {
            return "access_error";
        }
        return "post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/write";
        }

        Post post = postService.write(memberContext.getId(), postForm.getSubject(), postForm.getContent());

        log.debug("post : " + post);

        String msg = "%d번 글이 작성되었습니다.".formatted(post.getId());
        msg = Ut.url.encode(msg);

        return "redirect:/post/detail/%d?msg=%s".formatted(post.getId(), msg);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(Model model, @PathVariable(name = "id") Long id, Principal principal) {

        Post post = this.postService.getPost(id);
        PostModifyForm postmodifyForm=new PostModifyForm();
        if (post == null) {
            throw new DataNotFoundException("%d번 게시글은 존재하지 않습니다.");
        }

        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        postmodifyForm.setSubject(post.getSubject());
        postmodifyForm.setContent(post.getContent());
        postmodifyForm.setModifyDate(post.getModifyDate());
        model.addAttribute("form",postmodifyForm);

        return "post/modify";
    }

    @PostMapping("/modify/{id}")
    public String postModify(@PathVariable(name = "id") Long id, @Valid PostModifyForm postmodifyForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "post/modify";
        }
        if (principal == null) {
            return "access_error";
        }
        Post post = this.postService.getPost(id);

        if (post == null) {
            throw new DataNotFoundException("%d번 게시글은 존재하지 않습니다.");
        }

        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        postService.modify(post.getId(), postmodifyForm.getSubject(), postmodifyForm.getContent());

        return String.format("redirect:/post/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String articleDelete(Principal principal, @PathVariable(name = "id") Long id) {
        Post post = postService.getPost(id);

        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        boolean result = postService.delete(id);
        if (result){
            return "redirect:/post/list";
        } else {
            // 삭제실패관련 페이지나 메시지 리턴
            return null;
        }
    }
}
