package com.Rootale.member.entity;

import com.Rootale.member.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "users"
        //uniqueConstraints = @UniqueConstraint(name = "uk_users_username", columnNames = "username")
)
public class User implements UserDetails {

    // 1) 기본 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long usersId;

    @Column(nullable=false, length = 255)
    private String email;

    // 2) 로그인 및 권한 정보
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(nullable = false, length= 255)
    private String nickname;

    @Column(name = "avartarURL", length = 255)
    private String avatarURL;

    @Column(length = 50, nullable=false)
    private String subscriptionTier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Column(nullable = false)
    private boolean isActive;

    // 3) 연관관계 설정
    @OneToMany(mappedBy="user", cascade= CascadeType.ALL, orphanRemoval= true)
    private List<Topic> topics= new ArrayList<>();

    @OneToMany(mappedBy="user", cascade= CascadeType.ALL, orphanRemoval= true)
    private List<Feedback> feedbacks= new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FcmToken> fcmTokens = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserSettings userSettings;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OAuthAccount> oauthAccounts = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LocalCredential localCredential;

    // ===== UserDetails 구현부 =====

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> "ROLE_USER");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        // OAuth 사용자는 LocalCredential이 없을 수 있음
        if (this.localCredential != null) {
            return this.localCredential.getPasswordHash();
        }
        return null; // OAuth 전용 사용자는 비밀번호 없음
    }

    // ===== 헬퍼 메서드 =====

    public void addOAuthAccount(OAuthAccount account) {
        this.oauthAccounts.add(account);
        account.setUser(this);
    }

    public void setLocalCredential(LocalCredential credential) {
        this.localCredential = credential;
        credential.setUser(this);
    }
}
