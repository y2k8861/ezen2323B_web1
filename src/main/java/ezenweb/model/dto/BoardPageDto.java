package ezenweb.model.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BoardPageDto {
    // 부가정보 //
    // 1. 현제 페이지 :
    private int page;
    // 2. 총 게시물 수
    private int totalPage;
    // 3. 페이지 버튼의 시작 번호
    private int startBtn;
    // 4. 페이지 버튼의 끝 번호
    private int endtBtn;
    // 5. 총 게시물수
    private int totalBoardSize;

    // 실제 내용 //
    private List<Object> list;
}
