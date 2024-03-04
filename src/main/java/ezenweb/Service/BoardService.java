package ezenweb.Service;

import ezenweb.model.dao.BoardDao;
import ezenweb.model.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class BoardService {

    @Autowired
    private BoardDao boardDao;
    @Autowired
    private FileService fileService;
    // 1. 글쓰기 처리
    public long doPostBoardWrite(BoardDto boardDto){
        System.out.println("BoardService.doPostBoardWrite");
        System.out.println("boardDto = " + boardDto);

        // 1. 파일처리
        String fileName = null;
        if (!boardDto.getUploadfile().isEmpty()){
            // 2. DB처리
            // Dto에 업로드 성공한 파일명 대입한다.
            fileName = fileService.fileUpload(boardDto.getUploadfile());
            if(fileName == null){return -1;}
        }
        boardDto.setBfile(fileName);
        Long result = boardDao.doPostBoardWrite(boardDto);

        return result;
    }

    // 2. 전체 글 출력 호출

    // 3. 개별 글 출력 호출
    public BoardDto doGetBoardView(int bno){
        System.out.println("BoardService.doGetBoardView");
        System.out.println("bno = " + bno);
        return boardDao.doGetBoardView(bno);
    }

    // 4. 글 수정 처리

    // 5. 글 삭제 처리
}
