package ezenweb.Service;

import ezenweb.model.dao.BoardDao;
import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.BoardPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public BoardPageDto doGetBoardViewList(int page){
        System.out.println("BoardService.doGetBoardViewList");

        // 1. 페이지당 게시물을 출력할 개수
        int pageBoardSize = 1;

        // 2. 페이지당 게시물을 출력할 시작 레코드 번호
        int startRow = (page-1)*pageBoardSize;
        // 3. 총 페이지 구하기
            // 1. 전체 게시물 수
        int totalBoardSize = (boardDao.getBoardSize()-1)/pageBoardSize +1;
            // 2. 총 페이지 수 계산
        List<BoardDto> list =  boardDao.doGetBoardViewList(pageBoardSize,startRow);

        // 5. 페이징 버튼 개수
            // 1. 페이지 버튼 최대 개수
        int btnSize = 5;    // 5개씩
            // 2. 페이지 버튼시작번호
        int startBtn = ((page-1)/btnSize*btnSize)+1;
            // 3. 페이지 끝 번호
        int endtBtn = startBtn+btnSize-1;
            // 만약에 페이지 버튼의 끝번호가 총페이지 보다는 커질수 없다.
        if(endtBtn >=totalBoardSize) endtBtn = totalBoardSize;
        BoardPageDto boardPageDto = new BoardPageDto(page,totalBoardSize,startBtn,endtBtn,list);
        return boardPageDto;
    }

    // 3. 개별 글 출력 호출
    public BoardDto doGetBoardView(int bno){
        System.out.println("BoardService.doGetBoardView");
        System.out.println("bno = " + bno);
        return boardDao.doGetBoardView(bno);
    }

    // 4. 글 수정 처리

    // 5. 글 삭제 처리
}
