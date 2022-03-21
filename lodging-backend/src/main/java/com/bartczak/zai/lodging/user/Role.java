package com.bartczak.zai.lodging.user;

import com.bartczak.zai.lodging.common.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    private String authority;
}
