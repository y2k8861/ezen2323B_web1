package example.day07._1Tree컬렉션;

import java.util.TreeSet;

public class Example3 {
    public static void main(String[] args) {

        // 1. TreeSet 컬렉션 생성
        TreeSet<Person> treeSet = new TreeSet<>();

        // 2. 객체저장
        treeSet.add(new Person("홍길동",45));
        treeSet.add(new Person("김자바",25));
        treeSet.add(new Person("박지원",31));
        System.out.println("treeSet = " + treeSet);

        String str1 = "유재석";
        System.out.println(str1.compareTo("유재석"));
        System.out.println(str1.compareTo("강호동"));
        System.out.println(str1.compareTo("홍길동"));

    }
}
