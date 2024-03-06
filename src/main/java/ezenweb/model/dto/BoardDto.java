package ezenweb.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder    // 생성자 단점을 보완한 라이브러리 함수 제공
public class BoardDto {
    private long bno;           // 번호
    private String btitle;      // 제목
    private String bcontent;    // 내용
    private String bfile;       // 첨부파일[첨부파일 출력용 이름]
    private long bview;         // 조회수
    private String bdate;       // 작성일
    private long mno;           // 작성자 번호
    private long bcno;          // 카테고리

    private MultipartFile uploadfile;   // 실제 첨부파일. [DB처리X,서버에 저장]

    String mid;
    String mimg;
    String bcname;
}

/*
    - 용도에 따라 다양한 DTO가 존재 할 수 있다.
    - 하나의 DTO에 서로 다른 용도로 사용.

    1. 글쓰기용
        - 입력받기 : btitle,bcontent,uploadfile,bcno
        - 서버처리 : bno-자동,bview-기본값0,bdate-기본값현재날짜,mno-로그인(*세션)

     2. 개별 출력용
        - 출력 : bno btitle bcontent bfile bview bdate mno bdate mno bcno

    3. 전체 출력용
        - 출력 : bno btitle bcontent bfile bview bdate mno bdate mno bcno mid mimg

   - 생성자 단점/규칙 : 매개변수의 순서, 개수 => 유연성 떨어짐
   - 빌더 패턴 : @Builder

*/
