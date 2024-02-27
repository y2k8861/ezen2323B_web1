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
        String fileName = fileService.fileUpload(memberDto.getImg());
        if(fileName != null){
            // 2. DB처리
            // Dto에 업로드 성공한 파일명 대입한다.
            memberDto.setUuidFile(fileName);
            return memberDao.doPostSignup(memberDto);
        }
        return false;
    }
}
