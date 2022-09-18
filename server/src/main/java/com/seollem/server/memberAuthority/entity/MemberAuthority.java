package com.seollem.server.memberAuthority.entity;

import com.seollem.server.authroity.entity.Authority;
import com.seollem.server.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member_authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberAuthorityId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "authority_name")
    private Authority authority;
}
