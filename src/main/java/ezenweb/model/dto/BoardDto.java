package ezenweb.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
}

/*
    글쓰기용
     - 입력받기 : btitle,bcontent,uploadfile,bcno
     - 서버처리 : bno-자동,bview-기본값0,bdate-기본값현재날짜,mno-로그인(*세션)
*/
