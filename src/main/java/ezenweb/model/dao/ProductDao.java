package ezenweb.model.dao;

import ezenweb.model.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDao extends Dao{

    // 1. 등록 서비스 요청
    public boolean postProductRegister(ProductDto productDto){
        System.out.println("ProductDao.postProductRegister");
        System.out.println("productDto = " + productDto);
        try {
            // 1. 제품등록
            String sql = "insert into product(pname,pprice,pcontent,plat,plng,mno) values(?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,productDto.getPname());
            ps.setInt(2,productDto.getPprice());
            ps.setString(3,productDto.getPcontent());
            ps.setString(4,productDto.getPlat());
            ps.setString(5,productDto.getPlng());
            ps.setLong(6,productDto.getMno());
            int count = ps.executeUpdate();
            if(count == 1) {
                // 2. 이미지 등록
                rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int pno = rs.getInt(1);
                    // 등록할 개수 만큼 SQL 실행
                    try {
                        String subSql = "insert into productimg(pimg,pno) values";
                        for(int i = 0; i<productDto.getPImg().size(); i++){
                            String pimg = productDto.getPImg().get(i);
                            subSql += " (\""+pimg+"\","+pno+")";
                            if(i != productDto.getPImg().size()-1) subSql += ",";
                        }
                        System.out.println("subSql = " + subSql);
                        ps = conn.prepareStatement(subSql);
                        int count2 = ps.executeUpdate();
                        if(count2 > 0) return true;
                    } catch (Exception e){
                        System.out.println("e = " + e);
                    }
                }
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 2. 제품 출력(지도에 출력할) 요청
    public List<ProductDto> getProductList(){
        System.out.println("ProductDao.getProductList");
        List<ProductDto> list = new ArrayList<>();
        try {
            String sql = "select * from product p inner join member m on p.mno = m.no";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ProductDto productDto = ProductDto.builder()
                        .pno(rs.getInt("pno"))
                        .pname(rs.getString("pname"))
                        .pprice(rs.getInt("pprice"))
                        .pstate(rs.getByte("pstate"))
                        .plat(rs.getString("plat"))
                        .plng(rs.getString("plng"))
                        .pcontent(rs.getString("pcontent"))
                        .mid(rs.getString("id"))
                        .pdate(rs.getString("pdate"))
                        .build();
                List<String> imgList = new ArrayList<>();
                    String subSql = "select * from productimg where pno = " + productDto.getPno();
                    ps = conn.prepareStatement(subSql);
                    ResultSet rs2 = ps.executeQuery();
                    while (rs2.next()){
                        imgList.add(rs2.getString("pimg"));
                    }
                productDto.setPImg(imgList);
                list.add(productDto);
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return list;
    }

    // 3. 해당 제품 찜하기 등록
    public boolean doPlikeWrite(int pno, long mno){
        System.out.println("pno = " + pno);
        System.out.println("mno = " + mno);
        try{
            String sql = "insert into plike values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setLong(1,mno);
            ps.setInt(2,pno);
            int count = ps.executeUpdate();
            if(count==1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 4. 해당 제품 찜하기 상태 출력
    public boolean doPlikeView( int pno, long mno){
        System.out.println("pno = " + pno);
        System.out.println("mno = " + mno);
        try{
            String sql = "select * from plike where mno = ? and pno = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1,mno);
            ps.setInt(2,pno);
            rs = ps.executeQuery();
            if(rs.next()) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 5. 해당 제품 찜하기 취소/삭제
    public boolean doPlikeDelete( int pno, long mno){
        System.out.println("pno = " + pno);
        System.out.println("mno = " + mno);
        try{
            String sql = "delete from plike where mno = ? and pno = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1,mno);
            ps.setInt(2,pno);
            int count = ps.executeUpdate();
            if(count==1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

}
