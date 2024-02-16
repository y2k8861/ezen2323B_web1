package example.day07._1Tree컬렉션;

public class Person implements Comparable<Person> {
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /*@Override
    public int compareTo(Person o) {
        // * 같은면 0, 주어진 객체보다 적으면 -1, 주어진 객체보다 크면 1
        // 1. age 정렬
        if(this.age < o.age) return - 1;
        else if(this.age == o.age) return 0;
        else return 1;
    }*/

    @Override
    public int compareTo(Person o) {
        // * 같은면 0, 주어진 객체보다 적으면 -1, 주어진 객체보다 크면 1
        // 2. name 이름정렬 (String 클래스이므로 정렬기준 이미 구현)
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
