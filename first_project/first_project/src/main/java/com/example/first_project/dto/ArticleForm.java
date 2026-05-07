package com.example.first_project.dto;

import com.example.first_project.entity.Article;
import com.example.first_project.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

public class ArticleForm {
    private  String title;
    private String content;

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Article toEntity() {
        // toEntity() 메서드에서는 폼 데이터를 담은 DTO 객체를 엔티티로 반환함 (return new Article();)
        return new Article(null, title, content);  // 따라서 전달값을 Article 클래스의 생성자 형식에 맞게 작성하면 됨
    }

    @Controller
    public class ArticleController {
        private ArticleRepository articleRepository; // 2. articleRepository 객체 선언

        @PostMapping("/articles/create")
        public String createArticle(ArticleForm form){
            System.out.println(form.toString());
            // [1] DTO를 엔티티로 변환
            Article article = form.toEntity();
            // [2] repository로 엔티티를 DB에 저장
            Article saved = articleRepository.save(article); // 1. article 엔티티를 저장해 saved 객체에 반환
            return "";
        }
    }

}
