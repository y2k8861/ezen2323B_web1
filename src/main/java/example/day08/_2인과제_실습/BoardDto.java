package example.day08._2인과제_실습;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardDto {
    private int no;
    private String content;
    private String writer;
    private String pw;
}
