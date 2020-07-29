package net.abadguy.string;

public class StringTest01 {
    public static void main(String[] args) {
        String s1="javaEE";
        String s2="hadoop";

        String s3="javaEEhadoop";
        String s4="javaEE"+"hadoop";
        String s5=s1+"hadoop";
        String s6="javaEE"+s2;
        String s7=s1+s2;
        //字面量拼接的情况下，地址值是常量池中的地址值
        System.out.println(s3==s4);//true
        //字面量和变量值拼接或者变量值和变量值拼接，地址值是堆中的地址值
        System.out.println(s3==s5);//false
        System.out.println(s3==s6);//false
        System.out.println(s3==s7);//false
        System.out.println(s4==s5);//false
        System.out.println(s5==s6);//false

        String s8=s5.intern();
        System.out.println(s3==s8);//true
    }
}
