package example.day09;

public class WorkThread extends Thread {
    // 필드
    public boolean work = true;
    // 생성자
    public WorkThread(String name){
        setName(name); // 매개변수로 스레드 이름 변경
        // Thread 클래스
        // setName();   // 스래드 이름 변경 함수
        // getName();   // 스레드 이름 호출 함수
        // run();       // 작업 스레드가 실행할 코드 함수.
    }

    // 오버라이딩
    @Override
    public void run() {
        while (true){
            if (work){
                System.out.println(getName());
            } else {
                System.out.println(1);  // 점유 -> 처리 -> 대기
                Thread.yield();         // 점유 -> 양보 -> 대기 : 첨유 상태 되었을때 대기 상태로 돌아간다.
                System.out.println(2);  // 점유 -> 처리 -> 대기
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }
}
