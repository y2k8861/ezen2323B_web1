package ezenweb.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardPageDto {
    // 부가정보 //
    // 1. 현제 페이지 :
    private int page;
    // 2. 총 게시물 수
    private int totalPage;
    private int startBtn;
    private int endtBtn;

    // 3.---------------
    // 실제 내용 //
    private List<BoardDto> list;
}
