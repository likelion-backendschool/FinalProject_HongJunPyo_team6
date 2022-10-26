package com.ll.exam.finalPrj_2Week.app.cart.controller;

import com.ll.exam.finalPrj_2Week.app.cart.entity.CartItem;
import com.ll.exam.finalPrj_2Week.app.cart.service.CartService;
import com.ll.exam.finalPrj_2Week.app.member.entity.Member;
import com.ll.exam.finalPrj_2Week.app.product.entity.Product;
import com.ll.exam.finalPrj_2Week.app.product.service.ProductService;
import com.ll.exam.finalPrj_2Week.app.security.dto.MemberContext;
import com.ll.exam.finalPrj_2Week.util.Ut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/items")
    @PreAuthorize("isAuthenticated()")
    public String showItems(@AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member buyer = memberContext.getMember();

        List<CartItem> items = cartService.getItemsByBuyer(buyer);


        model.addAttribute("items", items);

        return "cart/items";
    }

    @GetMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addItems(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model){
        Member buyer = memberContext.getMember();
        Optional<Product> product = productService.findById(id);
        cartService.addItem(buyer, product.get());
        return "redirect:/product/list?msg=" + Ut.url.encode("%d건의 품목을 추가되었습니다.".formatted(1));
    }

    @PostMapping("/removeItems")
    @PreAuthorize("isAuthenticated()")
    public String removeItems(@AuthenticationPrincipal MemberContext memberContext, String ids) {
        Member buyer = memberContext.getMember();

        String[] idsArr = ids.split(",");

        Arrays.stream(idsArr)
                .mapToLong(Long::parseLong)
                .forEach(id -> {
                    CartItem cartItem = cartService.findItemById(id).orElse(null);

                    if (cartService.actorCanDelete(buyer, cartItem)) {
                        cartService.removeItem(cartItem);
                    }
                });

        return "redirect:/cart/items?msg=" + Ut.url.encode("%d건의 품목을 삭제하였습니다.".formatted(idsArr.length));
    }
}
