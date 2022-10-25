package com.ll.exam.finalPrj_2Week.app.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.finalPrj_2Week.app.cart.service.CartService;
import com.ll.exam.finalPrj_2Week.app.member.entity.Member;
import com.ll.exam.finalPrj_2Week.app.member.service.MemberService;
import com.ll.exam.finalPrj_2Week.app.order.entity.Order;
import com.ll.exam.finalPrj_2Week.app.order.exception.ActorCanNotSeeOrderException;
import com.ll.exam.finalPrj_2Week.app.order.service.OrderService;
import com.ll.exam.finalPrj_2Week.app.security.dto.MemberContext;
import com.ll.exam.finalPrj_2Week.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private ObjectMapper objectMapper;
    private MemberService memberService;
    private CartService cartService;

    @PostMapping("/makeOrder")
    @PreAuthorize("isAuthenticated()")
    public String makeOrder(@AuthenticationPrincipal MemberContext memberContext) {
        Member member = memberContext.getMember();
        Order order = orderService.createFromCart(member);
        String redirect = "redirect:/order/%d".formatted(order.getId()) + "?msg=" + Ut.url.encode("%d번 주문이 생성되었습니다.".formatted(order.getId()));

        return redirect;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model) {
        Order order = orderService.findForPrintById(id).get();

        Member actor = memberContext.getMember();

        long restCash = memberService.getRestCash(actor);

        if (orderService.actorCanSee(actor, order) == false) {
            throw new ActorCanNotSeeOrderException();
        }

        model.addAttribute("order", order);
        model.addAttribute("actorRestCash", restCash);

        return "order/detail";
    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showOrder(@AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member buyer = memberContext.getMember();

        Order order = orderService.getOrderByBuyer(buyer);
        model.addAttribute("orders", order);

        return "order/list";
    }

    @GetMapping("/{id}/cancel")
    @PreAuthorize("isAuthenticated()")
    public String cancelOrder(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model) {
        Order order = orderService.findForPrintById(id).get();

        Member actor = memberContext.getMember();

        if (orderService.actorCanSee(actor, order) == false) {
            throw new ActorCanNotSeeOrderException();
        }
        orderService.cancelOrder(order);
        String msg = "%d번 주문이 삭제되었습니다.".formatted(order.getId());
        msg = Ut.url.encode(msg);
        return "redirect:/product/list?msg=%s".formatted(msg);
    }
}
