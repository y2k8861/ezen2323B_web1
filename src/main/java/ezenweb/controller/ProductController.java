package ezenweb.controller;

import ezenweb.Service.ProductService;
import ezenweb.model.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    MemberController memberController;

    @Autowired
    private ProductService productService;

    @Autowired
    HttpServletRequest request;

    // 1. 등록 서비스 요청
    @PostMapping("/register.do")
    @ResponseBody
    public boolean postProductRegister(ProductDto productDto){
        System.out.println("ProductController.postProductRegister");

        // 1. 등록자 세션 처리
        Object object = request.getSession().getAttribute("loginDto");
        if(object == null) return false;
        productDto.setMno(memberController.doGetLoginInfo((String) object).getNo());


        return productService.postProductRegister(productDto);
    }

    // 2. 제품 출력(지도에 출력할) 요청
    @GetMapping("/list.do")
    @ResponseBody
    public List<ProductDto> getProductList(){
        System.out.println("ProductController.getProductList");
        return productService.getProductList();
    }

    // 3. 해당 제품 찜하기 등록  - 언제실행 : 로그인했고 찜하기 버튼 클릭시, 매개변수 : pno, 리턴 : boolean
    @PostMapping("/plike.do")
    @ResponseBody
    public boolean doPlikeWrite(@RequestParam int pno){
        // 1. 등록자 세션 처리
        Object object = request.getSession().getAttribute("loginDto");
        if(object == null) return false;
        long mno = memberController.doGetLoginInfo((String) object).getNo();
        return productService.doPlikeWrite(pno, mno);
    }

    // 4. 해당 제품 찜하기 상태 출력 - 언제실행 : 로그인했고 찜하기 버튼 출력시, 매개변수 : pno, 리턴 : boolean
    @GetMapping("/plike.do")
    @ResponseBody
    public boolean doPlikeView(@RequestParam int pno){
        Object object = request.getSession().getAttribute("loginDto");
        if(object == null) return false;
        long mno = memberController.doGetLoginInfo((String) object).getNo();
        return productService.doPlikeView(pno, mno);
    }

    // 5. 해당 제품 찜하기 취소/삭제 - 언제실행 : 로그인했고 찜하기 버튼 클릭시, 매개변수 : pno, 리턴 : boolean
    @DeleteMapping("/plike.do")
    @ResponseBody
    public boolean doPlikeDelete(@RequestParam int pno){
        Object object = request.getSession().getAttribute("loginDto");
        if(object == null) return false;
        long mno = memberController.doGetLoginInfo((String) object).getNo();
        return productService.doPlikeDelete(pno, mno);
    }


    // 1. 등록 페이지 요청
    @GetMapping("/register")
    public String getProductCreatePage(){
        return "/ezenweb/product/register";
    }

    // 2. 제품 지도 페이지 요청
    @GetMapping("/list")
    public String getProductAllViewPage(){
        return "/ezenweb/product/list";
    }

}
