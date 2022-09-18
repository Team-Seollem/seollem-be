package com.seollem.server.member.entity;

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
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    private boolean activated;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberAuthority> memberAuthorities = new ArrayList<>();



//    private MemberRole role;
//    public enum MemberRole{
//
//        USER("회원입니다"),
//        ADMIN("관리자입니다");
//
//        @Getter
//        private String role;
//
//        MemberRole(String role) {
//            this.role = role;
//        }
//    }
}
