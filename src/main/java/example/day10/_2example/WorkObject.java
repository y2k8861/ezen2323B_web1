package example.day10._2example;

public class WorkObject {

    // 1. 함수
    public synchronized void methodA(){
        // 1. 현재 스레드 객체 호출 : .currentThread();
        // 2. 스레드의 이름 호출 : .getName();
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());

        notify(); // 다른스레드를 실행 대기 상태로
        try {
            wait(); // 현제 스레드를 일시 정지 상태로
        } catch (InterruptedException e){
            System.out.println("e = " + e);
        }
    }

    // 2.
    public synchronized void methodB(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());

        notify(); // 다른스레드를 실행 대기 상태로
        try {
            wait(); // 현제 스레드를 일시 정지 상태로
        } catch (InterruptedException e){
            System.out.println("e = " + e);
        }
    }
}
