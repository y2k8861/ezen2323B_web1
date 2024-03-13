package ezenweb.controller;

import ezenweb.Service.BoardService;
import ezenweb.Service.FileService;
import ezenweb.Service.MemberService;
import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.BoardPageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")    // 공통 URL
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;
    @Autowired
    FileService fileService;

    // 1. 글쓰기 처리                // board/write.do      POST         DTO
    @PostMapping("/write.do")
    @ResponseBody
    public long doPostBoardWrite(BoardDto boardDto){
        System.out.println("BoardController.doPostBoard");
        System.out.println("boardDto = " + boardDto);

        // 1. 현재 로그인된 세션(톰캣서버(자바프로그램) 메모리(JVM) 저장소) 호출
        Object object = request.getSession().getAttribute("loginDto");
        if(object == null)return -2;

        // 2. 형변환
        String mid = (String)object;

        Long mno = memberService.doGetLoginInfo(mid).getNo();
        System.out.println("mno = " + mno);
        boardDto.setMno(mno);

        return boardService.doPostBoardWrite(boardDto);
    }

    // 2. 전체 글 출력 호출          // board/do            GET           x,페이징처리,검색
    @GetMapping("/do")  // (쿼리스트링)매개변수 : 현재 페이지
    @ResponseBody
    public BoardPageDto doGetBoardViewList(
            @RequestParam int page,
            @RequestParam int pageBoardSize,
            @RequestParam int bcno,
            @RequestParam String key,
            @RequestParam String keyword
    ){
        System.out.println("BoardController.doGetBoardViewList");
        System.out.println("page = " + page);
        System.out.println("pageBoardSize = " + pageBoardSize);
        System.out.println("bcno = " + bcno);
        System.out.println("key = " + key);
        System.out.println("keyword = " + keyword);
        return boardService.doGetBoardViewList(page,pageBoardSize,bcno,key,keyword);
    }

    // 3. 개별 글 출력 호출          // board/view.do       GET           게시물 번호
    @GetMapping("/view.do")
    @ResponseBody
    public BoardDto doGetBoardView(@RequestParam int bno){
        System.out.println("BoardController.doGetBoardView");
        System.out.println("bno = " + bno);
        return boardService.doGetBoardView(bno);
    }

    // 4. 글 수정 처리              // board/update.do      PUT          DTO
    @PutMapping("/update.do")
    @ResponseBody
    public boolean doUpdateBoard(BoardDto boardDto){
        System.out.println("BoardController.doUpdateBoard");
        System.out.println("boardDto = " + boardDto);
        System.out.println("이미지"+boardDto.getUploadfile());

        // 유효성 검사
            // 현재 로그인된 아이디.(세션)
        Object object = request.getSession().getAttribute("loginDto");
        if(object != null) {
            String mid = (String)object;
            boolean result = boardService.boardWriteAuth(boardDto.getBno(),mid);
            if(result) {
                return boardService.doUpdateBoard(boardDto);
            }
        }
            // 현재 수정할 게시물의 작성자 아이디.

        return false;
    }

    // 5. 글 삭제 처리              // board/delete.do      DELETE       게시물번호
    @DeleteMapping("/delete.do")
    @ResponseBody
    public boolean doDeleteBoard(@RequestParam int bno){
        System.out.println("BoardController.doDeleteBoard");
        // 유효성 검사
        // 현재 로그인된 아이디.(세션)
        Object object = request.getSession().getAttribute("loginDto");
        if(object != null) {
            String mid = (String)object;
            boolean result = boardService.boardWriteAuth(bno,mid);
            if(result) {
                return boardService.doDeleteBoard(bno);
            }
        }
        // 현재 수정할 게시물의 작성자 아이디.

        return false;
    }

    // 6 다운로드 처리 (함수 만들때 고민할점. 1, 매개변수 : 파일명 2. 반환 3. 사용처)
    @GetMapping("/file/download")
    @ResponseBody
    public void getBoardFileDownload(@RequestParam String bfile){
        System.out.println("BoardController.getBoardFileDownload");
        fileService.fileDownload(bfile);
        return ;
    }

    // 7. 댓글 작성 (brcontent,brindex,mno,bno)
    @PostMapping("/reply/write.do")
    @ResponseBody
    public boolean postReplyWrite(@RequestParam Map<String,String> map){
        System.out.println("BoardController.postReplyWrite");
        System.out.println("map = " + map);
        Object object = request.getSession().getAttribute("loginDto");
        String mid = (String)object;
        long mno = memberService.doGetLoginInfo(mid).getNo();
        map.put("mno",mno+"");
        return boardService.postReplyWrite(map);
    }

    // 8. 댓글 출력 (brno,brcontent,brindex,mno)
    @GetMapping("/reply/do")
    @ResponseBody
    public List<Map<String,Object>> getReplyDo(int bno){
        System.out.println("BoardController.getReplyDo");
        System.out.println("bno = " + bno);
        return boardService.getReplyDo(bno);
    }

    // ======================================================== //

    // 1. 글쓰기 페이지 이동         // board/write          GET
    @GetMapping("/write")
    public String getBoardWrite(){
        return "/ezenweb/board/write";
    }
    // 2. 게시판 페이지 이동         // board                GET
    @GetMapping("/")
    public String getBoard(){
        return "/ezenweb/board/board";
    }

    // 3. 게시판 상세페이지 이동     // board/view            GET
    @GetMapping("/view")
    public String getBoardView(){
        return "/ezenweb/board/view";
    }

    // 4. 글수정 페이지 이동        // board/update          GET
    @GetMapping("/update")
    public String getBoardUpdate(){
        return "/ezenweb/board/update";
    }


    // 5. 조회수
    @GetMapping("/viewPlus")
    @ResponseBody
    public boolean onBoardPlus(@RequestParam int bno){
        return boardService.onBoardPlus(bno);
    }
}
