package example.day08;

import java.awt.*;

public class Example1 {
    /*
        운영체제는 실행중인 프로그램을 프로세스로 관리
            - 프로그램 1개당 프로세스 존재
            - 멀티 태스킹 : 두가지 이상을 동시에 처리
                - 프로그렘 1개당 멀티 프로세스 존재 할 수 있다.
                - 프로세스 1개당 멀티 스레드 존재 할 수 있다.
            - 멀티 스레드 : 하나의 프로세스가  두 가지 이상의 작업을 처리
        스레드 : 코드의 실행 흐름
            - 스래드 마다 스택 할당
            - 스레드 끼리 자원 공유 안됨
            - 하나의 스레드가 예외 발생시 전체 스레드가 예외 발생
            -
        멀티스레드 : 여러개의 모드 실행 흐름
            - 생성 : main 스레드가 추가 작업 스레드 생성

        cpu 코어 1개당
            --------------------------------------------
            멀티 스레드
            |
            --------------------------------------------

        JVM
        메소드 영역              스택영역                힙영역
        - 클래스정보             - 스레드 마다 하나         - 인스턴스 할당
        - static               - 함수실행{}지역변수할당
    */

    // 1. 메인스레드(메인함수) 선언
    public static void main(String[] args) {
        // * java.aws : java.ui
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for(int i = 0; i<5; i++){
            toolkit.beep(); // 띵 소리 발생시키는 함수.
            // 띵소리가 for문이 5번 회전하는 것보다 느리기 때문에
            // 메인스레드 잠시 멈추기
                // Thread.sleep(밀리초) : 밀리초(1/1000초) 만큼 일시정지
                    // * 예외처리 : 혹시나 일시 정지 도중에 다른 쪽 스레드가 예외 발생시킬수 있을수 있으니까.
            try {
                Thread.sleep(500);
            } catch (Exception e){
                System.out.println(e);
            }
        }
        for(int i = 0; i<5; i++){
            System.out.println("띵");
            try {
                Thread.sleep(500);
            } catch (Exception e){
                System.out.println(e);
            }
        }

        // ======== 멀티 스레드 일때 ======== //
        // 1. Runnable 인테페이스 구현객체 필요
            // 1. 구현 클래스
            // 2. 익명 구현체 : 인터페이스가 new 이용한 직접 추상메소드 재정의
                // new 인터페이스명(){}
        // 2. 구현객체를 Thread 생성자에 대입.
        Thread thread = new Thread(new Runnable() {
            // 작업스레드 구현
            @Override
            public void run() {
                Toolkit toolkit1 = Toolkit.getDefaultToolkit();
                for (int i=0; i<5; i++){
                    toolkit1.beep();
                    try {
                        Thread.sleep(500);
                    } catch (Exception e){}
                }
            }
            // ======== 작업스레드 구현 끝 ======== //
        });
        thread.start(); // 작업스레드 실행
        for(int i = 0; i<5; i++){
            System.out.println("띵");
            try {
                Thread.sleep(500);
            } catch (Exception e){
                System.out.println(e);
            }
        }
        // ============================== 멀티 스레드2 일떄 ============================== //
        // 1. Runnable 객체
        Runnable runnable = new 작업스레드();
        // 2. Thread 객체
        Thread thread1 = new Thread(runnable);

        thread1.start();
        for(int i = 0; i<5; i++){
            System.out.println("띵");
            try {
                Thread.sleep(500);
            } catch (Exception e){
                System.out.println(e);
            }
        }
        // ============================== 멀티 스레드2 일떄 End ============================== //

        // ============================== 멀티 스레드3 일떄 ============================== //
        작업스레드2 작업스레드2 = new 작업스레드2();
        작업스레드2.start();
        // vs
        // 익명 자식객체
        Thread thread2 = new Thread(){
            @Override
            public void run() {
                super.run();
            }
        };
    }
}

/*
    [인터페이스]
    구현객체(구현체)란 : 추상메소드를 구현한 클래스의 객체
    - 구현객체
        1. 구현클래스 정의 : 클래스 implements 인터페이스 {추상메소드 재정의}
        2. 인터페이스명 병수명 = new 구현클래스();

    - 익명 구현객체
        1. 인터페이스명 변수명 = new 인터페이스명(){추상메소드 재정의}

    + Runnable 인터페이스
        1. run 매소드 재정의한다. (생성된 작업 스레드가 처리할 행위/메소드/함수/코드)
        2. Thread 클래스 생성해서 start();
            Runnable runnable = new runnable(){재정의};
            Thread thread = new Thread(runnable);
            thread.start();

===================================================================================

    [상속]
    자식객체 : 부모클래스로부터 (필드/메소드) 상속/물려받은 클래스의 객체
    - 자식객체
        1. 자식클래스 정의 : 클래스 extends 부모클래스(재정의)
        2. 부모/자식클래스 변수명 = new 자식클래스();

    - 익명 자식객체
        1. 부모클래스 변수명 = new 부모클래스(){재정의}

    + Thread 클래스
        1. run 메소드 재정의한다.(생성된 작업 스레드가 처리할 행위/메소드/함수/코드)
        2. Thread 클래스 생성해서 start();
            Thread thread = new 자식클래스();
            thread.start();
*/