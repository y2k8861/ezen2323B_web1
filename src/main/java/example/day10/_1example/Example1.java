package example.day10._1example;

public class Example1 {
    public static void main(String[] args) {
        // 1. 계산기 객체 1개를 생성
        Calulator calulator = new Calulator();

        // 2.
        User1Thread user1Thread = new User1Thread();
        user1Thread.setCalulator(calulator);
        user1Thread.start(); // 작업스레드, run 실행. 계산기 100저장

        // 3.
        User2Thread user2Thread = new User2Thread();
        user2Thread.setCalulator(calulator);
        user2Thread.start();
    }
}
