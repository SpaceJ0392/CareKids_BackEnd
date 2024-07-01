package com.aivle.carekids.domain.user.models;

import com.aivle.carekids.domain.common.models.BaseCreatedAt;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicy;
import com.aivle.carekids.domain.notice.models.Notice;
import com.aivle.carekids.domain.playInfo.models.PlayInfo;
import com.aivle.carekids.domain.question.models.Question;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"usersId", "usersEmail", "usersPassword", "usersNickname"})
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
@Table(name = "users", indexes = {@Index(name = "idx_email", columnList = "users_email")})
public class Users extends BaseCreatedAt {
    // 사용자 정보 엔티티
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long usersId;

    @Column(length = 30, nullable = false, unique = true)
    private String usersEmail;

    private String usersPassword;

    @Column(length = 20, nullable = false, unique = true)
    private String usersNickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType usersSocialType;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Kids> kids = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Liked> liked = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<PlayInfo> playInfoUsers = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Question> questionUsers = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<KidsPolicy> kidsPolicys = new ArrayList<>();

    @Builder
    public Users(String usersEmail, String usersPassword, String usersNickname, Role role, SocialType usersSocialType) {
        this.usersEmail = usersEmail;
        this.usersPassword = usersPassword;
        this.usersNickname = usersNickname;
        this.role = role;
        this.usersSocialType = usersSocialType;
    }

    // * 사용자 정의 메소드 * //
    public void setRegionInfo(Region region){
        this.region = region;
        region.getUsers().add(this);
    }

    public static Users createNewUser(SignUpRequestDto signUpData){

        SocialType socialType = Optional.ofNullable(signUpData.getUsersSocialType())
                .filter(s -> !s.isBlank()).map(SocialType::valueOf).orElse(null);

        return Users.builder()
                .usersEmail(signUpData.getUsersEmail())
                .usersNickname(signUpData.getUsersNickname())
                .usersPassword(signUpData.getUsersPassword())
                .role(signUpData.getRole()) // default - USER
                .usersSocialType(socialType)
                .build();
    }
    // TODO - 중계 테이블 관련 데이터 추가 메소드

}
