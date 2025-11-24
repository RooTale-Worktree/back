package com.Rootale.member.entity;

import com.Rootale.member.enums.ProviderType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AuthenticationProvider")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationProvider {

    @Id
    @Column(name = "providerKey", length = 255)
    private String providerKey;   // 구글 sub, 카카오 id 같은 외부 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "providerType", nullable = false, length = 20)
    private ProviderType providerType;
}
