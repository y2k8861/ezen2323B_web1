package ezenweb.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDto {
    private Long no;             // type = "text"(자동타입변환 -> int)
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String email;
    // private String img;      // type = "text"(String)
    private MultipartFile img;  // type = "file"(MultipartFile) 첨부
    private String uuidFile;    // uuid + file
}
