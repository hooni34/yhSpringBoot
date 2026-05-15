package com.example.first_project;

import com.example.first_project.dto.ArticleForm;
import com.example.first_project.entity.Article;
import com.example.first_project.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired // 스프링부트가 미리 생성해 놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return  "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //System.out.println(form.toString());
        log.info (form.toString());
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info (article.toString());
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info (saved.toString());

        return "";
    }
    @GetMapping("/articles/{id}") // 데이터 조회 요청 접수
    // 컨트롤러에서 URL 변수를 사용할 때는 중괄호 하나만 씀
    public String show(@PathVariable Long id, Model model){ // 매개변수 id로 받아오기
        log.info ("id= " + id); // id 잘 받았는지 확인하는 로그 찍기
        // 1. id를 조회해 DB에서 해당 데이터 가져오기 : DB에서 데이터를 가져오는 주체는 레포지토리임
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // findById() : JPA의 CrudRepository가 제공하는 메서드로, 특정 엔티티의 id값을 기준으로 데이터를 찾아 Optional타입으로 반환함
        // .orElse(null) : id 값으로 데이터를 찾을 때 해당 id값이 없으면 null을 반환
        // 2. 가져온 데이터를 모델에 등록하기 (MVC패턴에 따라 조회한 데이터를 뷰페이지에서 사용하기 위해)
        model.addAttribute("article", articleEntity); // article이라는 이름으로 ArticleEntity 객체를 등록
        // 3. 조회한 데이터를 사용자에게 보여 주기 위한 뷰 페이지를 만들고 반환하기
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        List<Article> articleEntityList = articleRepository.findAll();
        model.addAttribute("articleList", articleEntityList);
        return "articles/index";
    }
}
