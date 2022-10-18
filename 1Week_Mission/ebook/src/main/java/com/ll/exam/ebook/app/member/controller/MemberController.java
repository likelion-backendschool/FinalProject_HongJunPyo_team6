package com.ll.exam.ebook.app.member.controller;

import com.ll.exam.ebook.app.member.entity.Member;
import com.ll.exam.ebook.app.member.form.JoinForm;
import com.ll.exam.ebook.app.member.form.ModifyForm;
import com.ll.exam.ebook.app.member.service.MemberService;
import com.ll.exam.ebook.app.security.dto.MemberContext;
import com.ll.exam.ebook.app.util.Ut;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/member/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {
        memberService.join(joinForm.getUsername(), joinForm.getPassword(), joinForm.getEmail(), joinForm.getNickname());
        return "redirect:/member/login?msg=" + Ut.url.encode("회원가입이 완료되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member findMember = memberService.findByUsername(memberContext.getUsername()).orElseThrow(() -> new AccessDeniedException("user does not exist"));
        model.addAttribute("member", findMember);
        return "member/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext,@Validated @ModelAttribute("member") ModifyForm form, BindingResult bindingResult ,RedirectAttributes red) {
        Member member =new Member(form);
        if (bindingResult.hasErrors()) {
            log.error("bin = {} ", bindingResult.getAllErrors());
            return "member/modify";
        }
        memberService.update(member,memberContext.getName());
        red.addFlashAttribute("flag","수정 성공");
        return "redirect:/";
    }


    @PreAuthorize("isAnonymous()")
    @GetMapping("/findUsername")
    public String showFindUsername(Model model) {
        model.addAttribute("form",new findUsernameForm());
        return "member/find_username";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/findUsername")
    public String findUsername(@Validated @ModelAttribute("form") findUsernameForm form, BindingResult bindingResult,RedirectAttributes red) {
        if (bindingResult.hasErrors()) {
            log.error("bin = {} ", bindingResult.getAllErrors());
            return "member/find_username";
        }
        String memberId=memberService.findByEmail(form.getEmail());
        if (memberId==null) {
            bindingResult.rejectValue("email", "email does not exist ","해당 이메일이 존재하지 않습니다");
        }
        if (bindingResult.hasErrors()) {
            log.error("bin = {} ", bindingResult.getAllErrors());
            return "member/find_username";
        }
        red.addFlashAttribute("username", "찾은ID : " + memberId);
        return "redirect:/member/findUsername";
    }

    @Data
    static private class findUsernameForm {
        @NotEmpty
        @Email
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식을 다시확인해주세요")
        private String email;
    }

}