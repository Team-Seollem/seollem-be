package com.seollem.server.member.entity;

import com.seollem.server.audit.Auditable;
import com.seollem.server.book.entity.Book;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;
    @Column(nullable = false, unique = true)
    private String email;
    private String name;
    private String password;
    private String roles;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}
