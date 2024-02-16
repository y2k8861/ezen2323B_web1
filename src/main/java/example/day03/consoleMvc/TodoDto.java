package example.day03.consoleMvc;

import lombok.*;

// 데이터 이동 객체 : 여러 데이터를 하나의 타입으로 묶어주는 역할. DB
// 한개           : TodoDto       vs Map<String,String>
// 여러개 TodoDto  : List<TodoDto> vs List<Map<String,String>>
// member+todo   : todoMemberDto  vs Map<String,String>
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TodoDto {
    // 1. 필드(dto로 사용할 경우 db필드와 일치하고 추가적으로 추가가능)
    private int id;
    private String content;
    private String deadline;
    private Boolean state;
}
