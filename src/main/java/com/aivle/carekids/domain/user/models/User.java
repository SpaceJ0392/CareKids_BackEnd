package com.aivle.carekids.domain.user.models;

import com.aivle.carekids.domain.common.models.BaseCreatedAt;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyUsers;
import com.aivle.carekids.domain.notice.models.NoticeUsers;
import com.aivle.carekids.domain.playInfo.models.PlayInfoUsers;
import com.aivle.carekids.domain.question.models.QuestionUsers;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"usersId", "usersEmail", "usersPassword", "usersNickname"})
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class User extends BaseCreatedAt implements UserDetails {
    // 사용자 정보 엔티티
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(length = 30, nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column(length = 20, nullable = false, unique = true)
    private String userNickname;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Kids> kids = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Liked> liked = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<PlayInfoUsers> playInfoUsers = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<QuestionUsers> questionUsers = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<NoticeUsers> noticeUsers = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<KidsPolicyUsers> kidsPolicyUsers = new ArrayList<>();

    @Builder
    public User(String usersEmail, String usersPassword, String usersNickname, Role userRole) {
        this.userEmail = usersEmail;
        this.userPassword = usersPassword;
        this.userNickname = usersNickname;
        this.userRole = userRole;
    }

    // * 사용자 정의 메소드 * //
    public void setRegionInfo(Region region){
        this.region = region;
        region.getUsers().add(this);
    }

    // TODO - 중계 테이블 관련 데이터 추가 메소드

    // * 오버라이딩 메소드 *//
    @Override
    public String getPassword() { return userPassword; }

    @Override
    public String getUsername() { return userEmail; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(userRole.getRole()));
        return roles;
    }
}
