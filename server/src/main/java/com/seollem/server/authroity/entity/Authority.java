package com.seollem.server.authroity.entity;

import com.seollem.server.memberAuthority.entity.MemberAuthority;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;

    @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL)
    private List<MemberAuthority> memberAuthorities = new ArrayList<>();


}
