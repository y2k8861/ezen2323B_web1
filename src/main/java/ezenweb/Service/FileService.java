package ezenweb.Service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    // 2. 다운로드 메소드
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
