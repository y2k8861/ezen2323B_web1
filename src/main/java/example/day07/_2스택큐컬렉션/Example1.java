package example.day07._2스택큐컬렉션;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Example1 {
    public static void main(String[] args) {
     
        // 1. 스택 컬렉션 생성 [Vector 상속받음]
        Stack<Integer> coinBox = new Stack<>();
        
        // 2. 동전 넣기 = 스택 삽입 = push()
        coinBox.push(100);
        coinBox.push(50);
        coinBox.push(500);
        coinBox.push(10);

        System.out.println("coinBox = " + coinBox);

        // 3. 동전 빼기 = pop()
        coinBox.pop(); System.out.println("coinBox = " + coinBox);
        coinBox.pop(); System.out.println("coinBox = " + coinBox);
        coinBox.pop(); System.out.println("coinBox = " + coinBox);

        Queue<String> massageQueue = new LinkedList<>();
        massageQueue.offer("안녕 홍길동");
        massageQueue.offer("안녕 신용권");
        massageQueue.offer("안녕 감자바");
        System.out.println("massageQueue = " + massageQueue);

        massageQueue.poll(); System.out.println("massageQueue = " + massageQueue);
        massageQueue.poll(); System.out.println("massageQueue = " + massageQueue);
                
        
    }
}
