package com.example.first_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.processing.Generated;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity // 1. 엔티티 선언, JPA에서 제공, 어노테이션이 붙은 클래스를 기반으로 DB에 테이블 생성 (테이블 이름은 클래스 이름과 동일)
@Getter
public class Article {
    @Id // 3. 엔티티의 대푯값 지정(-> Article 엔티티 중에 제목과 내용이 같은 것이 있더라도 구분 가능)
    @GeneratedValue // 3. 대푯값 자동 생성(숫자가 자동으로 매겨짐)
    private Long id;
    @Column // 2. 두 필드를 DB에서 인식할 수 있도록 어노테이션 붙임, DB테이블의 각 열과 연결됨
    private String title; // 2. title, content 필드 선언
    @Column
    private String content;
}
