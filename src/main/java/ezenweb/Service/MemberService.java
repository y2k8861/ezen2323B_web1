package ezenweb.Service;

import ezenweb.model.dao.MemberDao;
import ezenweb.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private FileService fileService;    // 외부 서비스
    @Autowired
    private MemberDao memberDao;

    // 1. 회원가입 서비스
    public boolean doPostSignUp(MemberDto memberDto){
        // 1. 파일처리
        String fileName = "default.jpg";
        if (!memberDto.getImg().isEmpty()){
            // 2. DB처리
            // Dto에 업로드 성공한 파일명 대입한다.
            fileName = fileService.fileUpload(memberDto.getImg());
            if(fileName == null){return false;}
        }
        memberDto.setUuidFile(fileName);
        return memberDao.doPostSignup(memberDto);
    }

    // 2. 로그인 서비스

    // 3. 회원정보 요청서비스
    public MemberDto doGetLoginInfo(String id){
        return memberDao.doGetLoginInfo(id);
    }

    // 2-4 아이디 중복 체크 요청
    public boolean doGetFindIdCheck(String id){
        System.out.println("MemberController.doGetFindIdCheck");
        System.out.println("id = " + id);
        return memberDao.doGetFindIdCheck(id);
    }
}
