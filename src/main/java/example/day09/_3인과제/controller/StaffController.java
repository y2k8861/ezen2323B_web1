package example.day09._3인과제.controller;

import example.day09._3인과제.model.dao.StaffDao;
import example.day09._3인과제.model.dto.ScoreDto;
import example.day09._3인과제.model.dto.StaffDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StaffController {
    @Autowired
    StaffDao staffDao;

    @PostMapping("/staff/create")
    @ResponseBody
    public boolean create(StaffDto staffDto){
        System.out.println("1. ★ StaffController ★.create");
        System.out.println("1. ★ [TEST] staffDto ★ = " + staffDto);
        boolean result = staffDao.create(staffDto);
        System.out.println("1. ★ result ★ = " + result);
        return result;
    }

    @GetMapping("/staff/read")
    @ResponseBody
    public List<StaffDto> read(){
        System.out.println("2. ★ StaffController ★.read");
        List<StaffDto> result = staffDao.read();
        System.out.println("2. ★ LIST result ★ = " + result);
        return result;
    }

    @PostMapping("/staff/update")
    @ResponseBody
    public boolean update(StaffDto staffDto){
        System.out.println("3. ★ StaffController ★.update");
        boolean result = staffDao.update(staffDto);
        System.out.println("3. ★ result ★ = " + result);
        return result;
    }

    @GetMapping("/staff/delete/{sno}")
    @ResponseBody
    public boolean delete(@PathVariable("sno") int sno){
        System.out.println("4. ★ StaffController ★.delete");
        boolean result = staffDao.delete(sno);
        System.out.println("4. ★ result ★ = " + result);
        return result;
    }

    @PostMapping("/score/create")
    @ResponseBody
    public boolean scoreCreate(ScoreDto scoreDto){
        System.out.println("5. ★ StaffController.scoreCreate ★");
        System.out.println("5. ★ scoreDto ★ = " + scoreDto);
        boolean result = staffDao.scoreCreate(scoreDto);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("/score/read/{sno}")
    @ResponseBody
    public List<ScoreDto> scoreRead(@PathVariable("sno") int sno){
        System.out.println("6. ★ StaffController.scoreRead ★");
        System.out.println("6. ★ sno = ★" + sno);
        List<ScoreDto> result = staffDao.scoreRead(sno);
        System.out.println("result = " + result);
        return result;
    }

}

