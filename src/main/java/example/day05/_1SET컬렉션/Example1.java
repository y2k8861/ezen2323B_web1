package example.day05._1SET컬렉션;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Example1 {
    public static void main(String[] args) {

        /*
        set 컬렉션

            - 저장 순서/인덱스 유지되지 않는다.
            - 중복 저장할 수 없다, null하나만 가능하다
                - 단일 데이터 문제
                - 여러개 데이터로 구성된 객체들의 중복검사 기준.
                    hashCode(),equals() 오바라이딩
            - set 인터페이스
                1. 구현클래스
                    HashSet
                2. 사용방법/메소드
                    .add(객체명)     : 주어진 객체를 set 컬렉션에 저장
                    .size()         : set 컬렉션 내 총 사이즈 반환
                    .iterator()    : 반복자 객체 리턴
                        Iterator<E> 객체명 = set.interator()
                            객체명.hashNext()  : 다음에 가져올 객체가 존재하면 true / 없으면 false
                            객체명.next()      : 하나의 객체를 가져온다.
                            객체명.remove()    : 가져온 객체를 삭제한다.
                    .contains
            - 선언
                E : 컬렉션에 저장할 객체의 타입
                set<E> 컬렉션명 = new 구현클래스<>();

            - 순회
                1. iterator() 이용한 방법
                    Iterator<String> rs = set.iterator();
                    while (rs.hasNext()){
                        System.out.println("rs.next() = " + rs.next());
                    }
                2. 향상된 for문
                    for(String s : set){
                        System.out.println("s = " + s);
                    }
                3. forEach
                    set.forEach(s -> System.out.println("s = " + s));
         */
        // 1. set컬렉션 선언
        Set<String> set = new HashSet<>();
        // 2. set컬렉션에 객체 저장
        set.add("Java");
        set.add("JDBC");
        set.add("JSP");
        set.add("Java");    // 중복발생 : 중복 객체 이므로 저장되지 않음
        System.out.println("set = " + set);
        // 3. set 컬렉션의 총 객체 수
        int size  = set.size();
        System.out.println("size = " + size);

        // 4. 순회
            // 1. 향상된 for문
        for(String s : set){
            System.out.println("s = " + s);
        }
            // 2. 
        Iterator<String> rs = set.iterator();
        while (rs.hasNext()){
            System.out.println("rs.next() = " + rs.next());
        }
            // 3.
        set.forEach(s -> System.out.println("s = " + s));
        
        // ----------------------------------------------------- //
        Set<Member> set2 = new HashSet<>();
        set2.add(new Member("홍길동",30));
        set2.add(new Member("홍길동",30));
        System.out.println("set2 = " + set2);
        System.out.println("set2.toString() = " + set2.toString());
        System.out.println("set2.hashCode() = " + set2.hashCode());
    }
}
