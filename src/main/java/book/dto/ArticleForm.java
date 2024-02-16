package book.dto;

import lombok.*;

// 입력폼 DTO
    // 관례적으로 모든 객체 필드는 직접 접근 권장하지 않는다. private, 안정성보장, 캡슐화 특징, setter,getter, 생성자 오버로드
@NoArgsConstructor  // 컴파일시 기본 생성자를 자동으로 만들어준다,
@AllArgsConstructor // 컴파일시 모든 필드 생성자를 자동으로 만들어준다.
@ToString           // 컴파일시 toString() 자동으로 만들어준다,
@Getter             // 컴파일시 getter 메소드를 자동으로 만들어준다.
@Setter             // 컴파일시 setter 메소드를 자동으로 만들어준다.
public class ArticleForm {
    // 1. 필드
    private Long id;
    private String title;
    private String content;

    public ArticleForm(String title, String content){
        this.title = title;
        this.content = content;
    }
}
