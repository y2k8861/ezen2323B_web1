package example.day08._2인과제_실습;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class BoardController {
    @Autowired
    BoardDao boardDao;
    // 1. 저장
    @PostMapping("/board/create")
    @ResponseBody
    public boolean create(BoardDto boardDto){
        System.out.println("BoardController.create");
        System.out.println("boardDto = " + boardDto);
        boolean result = boardDao.create(boardDto);
        return result;
    }

    // 2. 전체 호출
    @GetMapping("/board/read")
    @ResponseBody
    public ArrayList<BoardDto> read(){
        System.out.println("BoardController.read");
        ArrayList<BoardDto> result = boardDao.read();
        return result;
    }

    // 3. 수정
    @PostMapping("/board/update")
    @ResponseBody
    public boolean update(BoardDto boardDto){
        System.out.println("BoardController.update");
        System.out.println("boardDto = " + boardDto);
        boolean result = boardDao.doPw(boardDto.getNo(),boardDto.getPw());
        if(result){
            result = boardDao.update(boardDto);
        }
        return result;
    }

    // 4. 삭제
    @GetMapping("/board/delete/{no}/{pw}")
    @ResponseBody
    public boolean delete(@PathVariable int no,@PathVariable String pw){
        System.out.println("BoardController.delete");
        System.out.println("no = " + no);
        boolean result = boardDao.doPw(no,pw);
        if(result){
            result = boardDao.delete(no);
        }
        return result;
    }

    @PostMapping("board/doPw")
    @ResponseBody
    public boolean doPw(BoardDto boardDto){
        System.out.println("BoardController.doPw");
        System.out.println("boardDto = " + boardDto);
        boolean result = boardDao.doPw(boardDto.getNo(),boardDto.getPw());
        return result;
    }
}
