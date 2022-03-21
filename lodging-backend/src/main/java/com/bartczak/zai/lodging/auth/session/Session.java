package com.bartczak.zai.lodging.auth.session;

import com.bartczak.zai.lodging.common.BaseEntity;
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
    @Column(name = "user_id")
    private Long userId;
}
