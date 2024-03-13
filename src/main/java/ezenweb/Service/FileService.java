package ezenweb.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Service    // 해당 클래스를 스프링 컨테이너에 등록
public class FileService {
    // Controller : 중계자 역할(HTTP매핑, HTTP요청/응답, 데이터 유효성검사) 등등
    // Service : Controller <-- Service(비지니스 로직) --> Dao. Controller <--> Service(비지니스 로직)

    // 어디에(PATH) 누구를(파일객체)
    // * 업로드할 경로 설정(내장 톰캣 경로)
    String uploadPath = "C:\\Users\\504\\Desktop\\ezen2323B_web1\\build\\resources\\main\\static\\img\\upload\\";

    // 1. 업로드 메소드
    public String fileUpload(MultipartFile multipartFile){
        System.out.println(multipartFile);                         // 첨부파일이 들어있는 객체 주소
        System.out.println(multipartFile.getSize());               // 첨부파일 용량 : 3440 바이트
        System.out.println(multipartFile.getContentType());        // image/png : 첨부파일의 확장자
        System.out.println(multipartFile.getOriginalFilename());   // logo.png  : 첨부파일의 이름(확장자 포함)
        System.out.println(multipartFile.getName());               // ing : form input name



        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);

        String filename = uuid + "_" + multipartFile.getOriginalFilename().replaceAll("_","-");


        File file = new File(uploadPath + filename);
        System.out.println("file = " + file);   // file = C:\java
        System.out.println("file.exists() = " + file.exists());
        // 2. [무엇을] 첨부파일 객체
        try {
            multipartFile.transferTo(file);
        } catch (Exception e){
            System.out.println(e);
            return  null;
        }
        return filename;    // 반환 : 어떤 이름으로 업로드 했는지
    }

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    // 2. 다운로드 메소드
    public void fileDownload(String bfile){
        System.out.println("BoardService.getBoardFileDownload");
        System.out.println("bfile = " + bfile);

        // 1. 다운로드 할 파일의 경로와 파일명 연결
        String downloadPath = uploadPath+bfile;
        System.out.println(downloadPath);

        // 2. 해당 파일을 객체로 가져오가 [File 클래스]
        File file = new File(downloadPath);
        System.out.println("file = " + file);

        if(file.exists()) {
            System.out.println("첨부파일 있다.");
            try {

                // HTTP로 응답시 응답방법(다운로드 모양)에 대한 정보를 추가.
                    // 기본값은 URL은 한글을 지원 안한다.
                    // URL에 한글 지원 하기 위해서는 URLEncoder.encode(url정보,utf-8)
                response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(bfile.split("_")[1],"utf-8"));

                // HTTP가 파일 전송하는 방법 파일 전송 : 파일을 바이트 전송
                // 1. 해당파일을 바이트로 불러온다.
                    // 1-1 입력스트림(바이트가 다니는 통록) 객체 생성
                BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
                    // 1-2 바이트 배열(고정길이) vs 리스트(가변길이)
                        // 1. 파일의 사이즈 크기/용량
                    int fileSize = (int)file.length();
                        // 2. 해당 파일의 사이즈 만큼 바이트 배열 선언
                    byte[] bytes = new byte[fileSize]; // 배열의 길이는 int 형

                    // 1-2 입력(불러오기)
                        // 바이트 하나씩 읽어오면서 바이트배열 저장 => 바이트 배열 저장
                fin.read(bytes);    // - 하나씩 바이트를 읽어와서 해당 바이트 배열에 저장해주는 함수.

                    // 1-3 (확인용)읽어온 파일의 바이트가 들어있다.
                System.out.println("bytes = " + bytes);

                // 2. 불러온 바이트를 HTTP response 이용한 바이트로 응답한다.
                    // 2-1 HTTP 응답 스트림 객체 생성
                BufferedOutputStream fout = new BufferedOutputStream(response.getOutputStream());
                    // 2-2 응답스트림.write(내보내기 할 바이트 배열) : 내보내기 할 바이트 배열 준비 상태이면 내보내기
                fout.write(bytes);

                // ------ 버퍼 초기화(안전하게)
                fin.close();    // 스트림 닫기
                fout.flush();   // 스트림 닫기

            } catch (Exception e){
                System.out.println("e = " + e);
            }
        } else {
            System.out.println("첨부파일 없다.");
        }
        return ;
    }

    // 3. 파일 삭제 [게시물 삭제시 만약에 첨부파일이 있으면 첨부파일도 삭제, 게시물 수적시 첨부파일을 변경하면 기존 첨부파일 삭제]
    public boolean fileDelete(String bfile){

        // 1. 경로와 파일을 합쳐서 파일 위치 찾기.
        String filePath = uploadPath+bfile;

        // 2. File 클래스의 메소드 활용
            // .exists() : 해당파일의 존재 여부
            // .length() : 해당 파일의 크기/용량(바이트단위)
            // .delete() : 해당 파일 삭제
        File file = new File(filePath);

        if(file.exists()){  // 만약 파일이 존재 하면 삭제
            file.delete();
            return true;
        }
        return false;
    }
}

// 서버에 업로드 했을때 설계
// 1. 여러 클라이언트[다수]가 동일한 파일명으로 서버[1명]에게 업로드 했을때 [식별깨짐]
// 식별이름 : 1. 날짜조합 + DB식별번호  2.UUID(난수 생성)
// 2. 클라이언트 화면 표시
// 업로드 경로 : 아파치 톰켓
        /*
            클라이언트 ------------> 톰캣(서버) <----------- build ------ 개발자
                          요청                             컴파일        코드
                     <-------------
        */
// 파일 이름 조합하기 : 새로운 식별이름과 실제 파일 이름
// 식별키와 실제이름 구분 : 왜? 나중에 쪼개서 구분할려고[다운로드시 식별키 빼고]
// 혹시나 파일이름이 구분문자가 있을경우 기준이 깨짐
// .replaceAll() : 문자열 치환/교체

// 1. 첨부파일 업로드 하기. [업로드란 : 클라이언트의 바이트(대용량/파일)를 복사해서 서버로 복사]
// 1. [어디에]첨부파일을 저장할 경로 셋팅
// File 클래스 : 파일 관련된 메소드 제공
// new File(파일경로);

/*

*/
