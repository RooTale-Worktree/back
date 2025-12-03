package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Node("Universe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Universe {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    @Property("universe_id")
    private String universeId;

    @Property("name") //name
    private String name;

    @Property("story")
    private String story;

    @Property("canon")
    private String canon;

    @Property("description")
    private String description;

    @Property("detailed_description")
    private String detailedDescription;

    @Property("estimated_play_time")
    private Integer estimatedPlayTime;

    @Property("representative_image")
    private String representativeImage;

    @Property("setting")
    private String setting;  // 세계관 설정 전체 텍스트

    @Property("protagonist_name")
    private String protagonistName;  // 주인공 이름 (한글)

    @Property("protagonist_desc")
    private String protagonistDesc;  // 주인공 상세 설정 (성격, 사상 등)

    @Property("synopsis")
    private String synopsis;  // 초기 대규모 시놉시스 (2000자 이상)

    @Property("twisted_synopsis")
    private String twistedSynopsis;  // 변주된 대규모 시놉시스 (2000자 이상)

    @Property("created_at")
    private LocalDateTime createdAt;

    @Property("updated_at")
    private LocalDateTime updatedAt;

//    @Relationship(type = "HAS_START", direction = Relationship.Direction.OUTGOING)
//    private Scene startScene;
//
//    @Relationship(type = "HAS_CHARACTER", direction = Relationship.Direction.OUTGOING)
//    @Builder.Default
//    private List<Character> characters = new ArrayList<>();

}