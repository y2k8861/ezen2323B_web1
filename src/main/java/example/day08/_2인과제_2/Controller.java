package example.day08._2인과제_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    Dao dao;

    //저장
    @PostMapping("/item/input")
    @ResponseBody
    public Boolean input(Dto dto){


        return dao.input(dto);
    }//m end

    //전체호출
    @GetMapping("/item")
    @ResponseBody
    public ArrayList<Dto> pageView(){
        System.out.println("Controller.pageView");
        ArrayList<Dto> list = dao.pageView();

        return list;
    }

    //삭제
    @PostMapping("/item/delete")
    @ResponseBody
    public boolean itemDelete(Dto dto){
        return dao.itemDelete(dto);
    };

    //수정
    @PostMapping("/item/itemUpdate")
    @ResponseBody
    public boolean itemUpdate(Dto dto){

        return dao.itemUpdate(dto);
    }

}//c end
