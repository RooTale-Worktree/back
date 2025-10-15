package com.LiveToon.domain.user.entity;

import com.LiveToon.domain.fcm.entity.FCMToken;
import com.LiveToon.domain.user.enums.SubscriptionTierEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usersId;

    //@Column(nullable = false, unique = true) 테스트 편의성을 위해 nullable 임시 허용
    @Column(nullable = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, length = 100)
    private URI avatarUrl;

    @Column(nullable = false)
    private Boolean isEnabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'FREE'")
    @Builder.Default
    private SubscriptionTierEnum subScriptionTierEnum = SubscriptionTierEnum.FREE;

    // User가 삭제되면 관련된 FcmToken도 함께 삭제되도록 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FCMToken> FCMTokens = new ArrayList<>();

    @Builder
    public User(String email, String username, String name, URI avatarUrl) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    // ===== UserDetails 구현 메서드 =====
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한을 간단히 "ROLE_USER"로 고정
        return Collections.singleton(() -> "ROLE_USER");
    }

    @Override
    public boolean isAccountNonExpired() {return true;} // 계정 만료 안 됨

    @Override
    public boolean isAccountNonLocked() {return true;} // 계정 잠김 아님

    @Override
    public boolean isCredentialsNonExpired() {return true;} // 비밀번호 만료 안 됨

    @Override
    public boolean isEnabled() {return true;} // 계정 활성화됨
}
