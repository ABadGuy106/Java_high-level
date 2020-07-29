package net.abadguy.string;

public class StringTest {
    public static void main(String[] args) {
        //通过字面量定义的方式：此时的s1和s2的数据javaEE声明在方法区中的字符串常量池中
        String s1="javaEE";
        String s2="javaEE";
        //通过new+构造器的方式：此时的s3和s4保存的地址值，是数据在堆空间开辟空间以后对应的地址值
        String s3=new String("javaEE");
        String s4=new String("javaEE");

        System.out.println(s1==s2);//true
        System.out.println(s1==s3);//false
        System.out.println(s1==s4);//false
        System.out.println(s3==s2);//false
        System.out.println(s3==s4);//false

        Person p1 = new Person("tom", 12);
        Person p2 = new Person("tom", 12);

        System.out.println(p1.getName()==p2.getName());//true
    }
}

class Person{
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}