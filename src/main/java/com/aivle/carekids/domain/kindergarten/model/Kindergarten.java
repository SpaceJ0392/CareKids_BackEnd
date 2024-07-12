package com.aivle.carekids.domain.kindergarten.model;

import com.aivle.carekids.admin.kindergarten.dto.KindergartenAdminDto;
import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.question.dto.QuestionDetailDto;
import com.aivle.carekids.domain.question.models.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Kindergarten extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kindergartenId;

    private String kindergartenName;

    private String kindergartenAddress;

    private String kindergartenNewaddress;

    private String kindergartenPhone;

    @Column(precision = 10, scale = 6)
    private BigDecimal x;

    @Column(precision = 10, scale = 6)
    private BigDecimal y;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kindergarten")
    private List<KindergartenOperateTime> kindergartenOperateTimes = new ArrayList<>();

    @Builder
    public Kindergarten(Long kindergartenId, String kindergartenName, String kindergartenAddress, String kindergartenNewaddress, String kindergartenPhone, Region region, List<KindergartenOperateTime> kindergartenOperateTimes) {
        this.kindergartenId = kindergartenId;
        this.kindergartenName = kindergartenName;
        this.kindergartenAddress = kindergartenAddress;
        this.kindergartenNewaddress = kindergartenNewaddress;
        this.kindergartenPhone = kindergartenPhone;
        this.region = region;
        this.kindergartenOperateTimes = kindergartenOperateTimes;
    }

    public static Kindergarten createKindergarten(KindergartenAdminDto kindergartenAdminDto){
        return Kindergarten.builder()
                .kindergartenId(kindergartenAdminDto.getKindergartenId())
                .kindergartenName(kindergartenAdminDto.getKindergartenName())
                .kindergartenAddress(kindergartenAdminDto.getKindergartenAddress())
                .kindergartenNewaddress(kindergartenAdminDto.getKindergartenNewaddress())
                .kindergartenPhone(kindergartenAdminDto.getKindergartenPhone())
                .build();
    }

    public void updateQuestion(QuestionDetailDto questionDetailDto){
        this.questionTitle = questionDetailDto.getQuestionTitle();
        this.questionText = questionDetailDto.getQuestionText();
        this.secret = questionDetailDto.getSecret();
    }

}
