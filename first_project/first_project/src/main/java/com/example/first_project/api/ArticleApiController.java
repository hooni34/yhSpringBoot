package com.example.first_project.api;

import com.example.first_project.dto.ArticleForm;
import com.example.first_project.entity.Article;
import com.example.first_project.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;

    // POST 구현: 데이터 생성
    @PostMapping("/api/articles") // 1. URL 요청 접수
    public Article create(@RequestBody ArticleForm dto){ // 2. 메서드 정의
        Article article = dto.toEntity();
        return articleRepository.save(article); // 웹페이지에서는 컨트롤러의 메서드에 매개변수로 dto를 받아오기만 하면 됐지만,
        // REST API에서 데이터를 생성할 때는 JSON데이터를 받아와야 함
        // 즉, 단순히 매개변수로 dto를 쓴다고 받아올 수 있는 것이 아님.
        // => @RequestBody 어노테이션 사용 : body에 실어 보내는 데이터를 받아올 수 있음
    }

    // PATCH 구현
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        // 1. DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info ("id: {}, article: {}", id, article.toString());
        // 2. target 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리
        if(target == null || id != article.getId()){
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 4. 업데이트 및 정상 응답(200) 하기
        target.patch(article); // (일부 데이터 수정 시를 위해) 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // 수정 내용 DB에 최종 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> show(@PathVariable Long id){

        Article target = articleRepository.findById(id).orElse(null);

        if(target == null){
            log.info("잘못된 요청! id: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(target);
    }
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){

        Article target = articleRepository.findById(id).orElse(null);

        if(target == null){
            log.info("잘못된 요청! id: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        articleRepository.delete(target);

        return ResponseEntity.status(HttpStatus.OK).body(target);
    }
}