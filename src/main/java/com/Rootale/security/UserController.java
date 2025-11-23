package com.Rootale.security;

import com.Rootale.security.OAuthDto.*;
import com.Rootale.member.entity.CustomUser;
import com.Rootale.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Authentication", description = "사용자 인증 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "소셜 로그인", description = "카카오/네이버/구글의 access token을 받아 서버 JWT를 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 access token")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> socialLogin(@Valid @RequestBody SocialLoginRequest request) {
        log.info("🔵 POST /user/login - provider: {}, email: {}", request.provider(), request.email());

        try {
            LoginResponse response = userService.socialLogin(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("❌ Bad request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            log.error("❌ Login failed: {}", e.getMessage());
            if (e.getMessage().contains("유효하지 않은 access token")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "로그아웃", description = "사용자를 로그아웃하고 FCM 토큰을 비활성화합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@AuthenticationPrincipal CustomUser currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("🔴 POST /user/logout - userId: {}", currentUser.getUserId());
        LogoutResponse response = userService.logout(currentUser.getUserId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "현재 사용자 정보 조회", description = "JWT로 인증된 현재 사용자의 정보를 조회합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUser currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("👤 GET /user/me - userId: {}", currentUser.getUserId());
        UserResponse response = userService.getCurrentUser(currentUser.getUserId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 정보 수정", description = "현재 사용자의 닉네임 또는 아바타를 수정합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal CustomUser currentUser,
            @Valid @RequestBody UpdateUserRequest request) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("✏️ PUT /user/me - userId: {}", currentUser.getUserId());
        UserResponse response = userService.updateUser(currentUser.getUserId(), request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "토큰 갱신", description = "Refresh Token으로 새로운 Access Token과 Refresh Token을 발급받습니다.")
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("🔄 POST /user/refresh");
        try {
            LoginResponse response = userService.refreshAccessToken(request.refresh_token());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("❌ Token refresh failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "회원 탈퇴", description = "현재 사용자를 탈퇴 처리합니다. (Soft Delete)",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @DeleteMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@AuthenticationPrincipal CustomUser currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("🗑️ DELETE /user/withdraw - userId: {}", currentUser.getUserId());
        WithdrawResponse response = userService.withdraw(currentUser.getUserId());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("❌ Validation error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "BAD_REQUEST", "message", e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        log.error("❌ Runtime error: {}", e.getMessage(), e);
        if (e.getMessage() != null && e.getMessage().contains("유효하지 않은")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "UNAUTHORIZED", "message", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "INTERNAL_SERVER_ERROR", "message", "서버 처리 중 오류가 발생했습니다."));
    }
}