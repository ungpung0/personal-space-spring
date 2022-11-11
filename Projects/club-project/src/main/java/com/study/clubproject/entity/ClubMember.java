package com.study.clubproject.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClubMember extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;

    private boolean formSocial;

    @ElementCollection(fetch = FetchType.LAZY) // @ElementCollection: 이 객체가 컬렉션 객체라는 것을 JPA에게 알려준다.
    @Builder.Default // @Builder.Default : 빌더 패턴으로 인스턴스를 생성할 때 특정 값으로 초기화.
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    public void addMemberRole(ClubMemberRole clubMemberRole) {
        roleSet.add(clubMemberRole);
    }

}
