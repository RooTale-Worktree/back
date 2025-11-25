
package com.Rootale.security;

import com.Rootale.security.OAuthDto.*;
import com.Rootale.member.entity.CustomUser;
import com.Rootale.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.web.client.RestClientException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Authentication", description = "사용자 인증 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "소셜 로그인",
            description = "카카오/네이버/구글의 id token을 받아 서버 JWT를 발급합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (파라미터 오류)"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 ID token"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/login")
    public ResponseEntity<?> socialLogin(@Valid @RequestBody SocialLoginRequest request) {
        log.info("🔵 POST /user/login - provider: {}, email: {}", request.provider(), request.email());

        try {
            LoginResponse response = userService.socialLogin(request);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // 400 Bad Request - 파라미터 검증 실패
            log.error("❌ Bad request: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(
                            "BAD_REQUEST",
                            e.getMessage(),
                            Map.of(
                                    "provider", request.provider()
                            )
                    ));

        } catch (RestClientException e) {
            // 401 Unauthorized - 소셜 플랫폼 토큰 검증 실패
            log.error("❌ Token verification failed: {}", e.getMessage());
            String errorDetail = extractErrorDetail(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse(
                            "INVALID_TOKEN",
                            "소셜 로그인 토큰이 유효하지 않습니다.",
                            Map.of(
                                    "provider", request.provider(),
                                    "reason", errorDetail,
                                    "hint", "앱에서 받은 토큰이 만료되었거나 유효하지 않습니다."
                            )
                    ));

        } catch (RuntimeException e) {
            // 세부 에러 타입 구분
            if (e.getMessage() != null) {
                if (e.getMessage().contains("유효하지 않은")) {
                    // 401 Unauthorized
                    log.error("❌ Invalid token: {}", e.getMessage());
                    String errorDetail = extractErrorDetail(e.getMessage());

                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(createErrorResponse(
                                    "INVALID_TOKEN",
                                    "유효하지 않은 token입니다.",
                                    Map.of(
                                            "provider", request.provider(),
                                            "detail", errorDetail
                                    )
                            ));

                } else if (e.getMessage().contains("이메일 정보가 일치하지 않습니다")) {
                    // 400 Bad Request
                    log.error("❌ Email mismatch: {}", e.getMessage());
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(createErrorResponse(
                                    "EMAIL_MISMATCH",
                                    "요청한 이메일과 토큰의 이메일이 일치하지 않습니다.",
                                    Map.of("provider", request.provider())
                            ));

                } else if (e.getMessage().contains("audience")) {
                    // 401 Unauthorized - Google audience 검증 실패
                    log.error("❌ Google audience verification failed: {}", e.getMessage());
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(createErrorResponse(
                                    "INVALID_CLIENT_ID",
                                    "Google 클라이언트 ID가 유효하지 않습니다.",
                                    Map.of(
                                            "provider", "google",
                                            "hint", "iOS/Android 앱의 클라이언트 ID 설정을 확인하세요."
                                    )
                            ));
                }
            }

            // 500 Internal Server Error - 기타 런타임 에러
            log.error("❌ Login failed with runtime error: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(
                            "INTERNAL_ERROR",
                            "로그인 처리 중 오류가 발생했습니다.",
                            Map.of(
                                    "provider", request.provider()
                            )
                    ));

        } catch (Exception e) {
            // 500 Internal Server Error - 예상치 못한 에러
            log.error("❌ Unexpected error during login: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(
                            "UNEXPECTED_ERROR",
                            "예상치 못한 오류가 발생했습니다.",
                            Map.of(
                                    "provider", request.provider(),
                                    "errorType", e.getClass().getSimpleName()
                            )
                    ));
        }
    }
    /**
     * 에러 메시지에서 핵심 정보만 추출
     */
    private String extractErrorDetail(String fullMessage) {
        if (fullMessage == null) {
            return "Unknown error";
        }

        // 1) "401 Unauthorized" 같은 HTTP 상태 추출
        if (fullMessage.contains("401 Unauthorized")) {
            return "401 Unauthorized - Token expired or invalid";
        }
        if (fullMessage.contains("403 Forbidden")) {
            return "403 Forbidden - Access denied";
        }
        if (fullMessage.contains("400 Bad Request")) {
            return "400 Bad Request - Invalid token format";
        }

        // 2) JSON 에러 메시지에서 error_description 추출
        if (fullMessage.contains("\"error_description\"")) {
            try {
                int start = fullMessage.indexOf("\"error_description\": \"") + 22;
                int end = fullMessage.indexOf("\"", start);
                if (start > 0 && end > start) {
                    return fullMessage.substring(start, end);
                }
            } catch (Exception e) {
                log.debug("Failed to extract error_description: {}", e.getMessage());
            }
        }

        // 3) "error": "invalid_request" 추출
        if (fullMessage.contains("\"error\":")) {
            try {
                int start = fullMessage.indexOf("\"error\": \"") + 10;
                int end = fullMessage.indexOf("\"", start);
                if (start > 0 && end > start) {
                    String errorCode = fullMessage.substring(start, end);
                    return formatErrorCode(errorCode);
                }
            } catch (Exception e) {
                log.debug("Failed to extract error code: {}", e.getMessage());
            }
        }

        // 4) 짧은 메시지면 그대로 반환 (80자 이하)
        if (fullMessage.length() <= 80) {
            return fullMessage;
        }

        // 5) 너무 길면 앞부분만 반환
        return fullMessage.substring(0, 80) + "...";
    }

    /**
     * error code를 사용자 친화적으로 변환
     */
    private String formatErrorCode(String errorCode) {
        return switch (errorCode) {
            case "invalid_request" -> "Invalid request";
            case "invalid_token" -> "Invalid or expired token";
            case "invalid_grant" -> "Invalid authorization grant";
            case "unauthorized_client" -> "Unauthorized client";
            default -> errorCode;
        };
    }

    // ... existing methods (logout, getCurrentUser, etc.) ...

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

    @Operation(summary = "토큰 갱신", description = "Refresh Token으로 새로운 id Token과 Refresh Token을 발급받습니다.")
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

    /**
     * 상세한 에러 응답 생성 헬퍼 메서드
     */
    private Map<String, Object> createErrorResponse(String errorCode, String message, Map<String, Object> details) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", errorCode);
        response.put("message", message);
        response.put("timestamp", Instant.now().toString());

        // ⭐ details가 비어있지 않을 때만 추가
        if (details != null && !details.isEmpty()) {
            response.put("details", details);
        }

        return response;
    }

    /**
     * 전역 Exception Handler
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("❌ Validation error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse(
                        "VALIDATION_ERROR",
                        e.getMessage(),
                        Map.of()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException e) {
        log.error("❌ Runtime error: {}", e.getMessage(), e);
        if (e.getMessage() != null && e.getMessage().contains("유효하지 않은")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse(
                            "UNAUTHORIZED",
                            e.getMessage(),
                            Map.of()
                    ));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse(
                        "INTERNAL_ERROR",
                        "서버 처리 중 오류가 발생했습니다.",
                        Map.of("detail", e.getMessage() != null ? e.getMessage() : "Unknown error")
                ));
    }
}