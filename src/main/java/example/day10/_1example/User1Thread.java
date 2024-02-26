package example.day10._1example;

public class User1Thread extends Thread{
    // extends Thread : 작업스레드 생성하기 위해

    // 1. 필드, 유저1객체가 가지고 있는 계산기
    private Calulator calulator;

    public User1Thread(){
        // setName : Thread 클래스로 부터 상속받은 함수()
        setName("User1Thread");
    }

    public void setCalulator(Calulator calulator){
        this.calulator = calulator;
    }

    @Override
    public void run() {
        calulator.setMemory(100);
    }
}
