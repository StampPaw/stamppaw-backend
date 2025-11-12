package org.example.stamppaw_backend.market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stamppaw_backend.market.dto.request.CartCreateRequest;
import org.example.stamppaw_backend.market.dto.request.CartUpdateRequest;
import org.example.stamppaw_backend.market.dto.response.CartResponse;
import org.example.stamppaw_backend.market.entity.Cart;
import org.example.stamppaw_backend.market.service.CartService;
import org.example.stamppaw_backend.user.service.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(cartService.getUserCart(userDetails.getUser().getId()));
    }

    @PostMapping
    public ResponseEntity<CartResponse> createCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CartCreateRequest request
    ) {
        Cart cart = cartService.createCartWithItems(userDetails.getUser().getId(), request);
        return ResponseEntity.ok(CartResponse.fromEntity(cart));
    }

    @PatchMapping("/item/quantity")
    public ResponseEntity<Void> updateCartItemQuantity(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CartUpdateRequest request
    ) {
        cartService.updateItemQuantity(userDetails.getUser().getId(), request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long cartItemId
    ) {
        cartService.removeItem(userDetails.getUser().getId(), cartItemId);
        return ResponseEntity.noContent().build();
    }
}
