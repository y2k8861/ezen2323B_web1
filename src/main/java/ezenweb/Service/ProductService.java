package ezenweb.Service;

import ezenweb.model.dao.ProductDao;
import ezenweb.model.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    FileService fileService;

    // 1. 등록 서비스 요청
    public boolean postProductRegister(ProductDto productDto){
        System.out.println("ProductService.postProductRegister");
        productDto.setPImg(new ArrayList<>());
        productDto.getUploadFiles().forEach(uploadFile ->{
            String fileName = fileService.fileUpload(uploadFile);
            System.out.println("fileName = " + fileName);
            if(fileName != null) productDto.getPImg().add(fileName);
        });
        return productDao.postProductRegister(productDto);
    }

    // 2. 제품 출력(지도에 출력할) 요청
    public List<ProductDto> getProductList(){
        System.out.println("ProductService.getProductList");
        return productDao.getProductList();
    }

    // 3. 해당 제품 찜하기 등록
    public boolean doPlikeWrite(int pno, long mno){
        return productDao.doPlikeWrite(pno, mno);
    }

    // 4. 해당 제품 찜하기 상태 출력
    public boolean doPlikeView( int pno, long mno){
        return productDao.doPlikeView(pno, mno);
    }

    // 5. 해당 제품 찜하기 취소/삭제
    public boolean doPlikeDelete( int pno, long mno){
        return productDao.doPlikeDelete(pno, mno);
    }

}
