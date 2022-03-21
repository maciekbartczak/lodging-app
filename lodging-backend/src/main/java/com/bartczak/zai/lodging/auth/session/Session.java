package com.bartczak.zai.lodging.auth.session;

import com.bartczak.zai.lodging.common.BaseEntity;
import com.bartczak.zai.lodging.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session extends BaseEntity {
    @Column(unique = true)
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
