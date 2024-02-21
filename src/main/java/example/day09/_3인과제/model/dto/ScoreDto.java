package example.day09._3인과제.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScoreDto {
    private int sno;
    private int scno;
    private String sccontent;
    private int scscore;
    private String scdate;
}