package Strings.StringSort;

import java.util.Scanner;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 16:24 2020/11/5
 */
public class IndexCountSort {

    private IndexCountSort(){}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = Integer.valueOf(scanner.nextLine());
        Person[] array = new Person[sum];
        for (int i = 0; i < sum; i++) {
            String s = scanner.nextLine();
            if(s == null || s.isEmpty())throw new IllegalArgumentException("姓名不能为空!");
            String arg1 = s.substring(0,s.indexOf(" "));
            int arg2 = Integer.valueOf(s.substring(s.indexOf(' ')+1));
            if(arg2 <= 0)throw new IllegalArgumentException("组号不能小于等于0!");
            Person person = new Person(arg1,arg2);
            array[i] = person;
        }

        int[] count = new int[sum+1];
        //转化成频率
        for (int i = 0; i < sum; i++) {
            count[array[i].getGroup()+1]++;
        }
        //将频率转换成索引
        for (int i = 1; i < sum; i++) {
            count[i] += count[i-1];
        }
        //将元素分类
        Person[] aux = new Person[sum];
        for (int i = 0; i < sum; i++) {
            aux[count[array[i].getGroup()]++] = array[i];
        }

        //写回
        array = aux;

        for (int i = 0; i < sum; i++) {
            System.out.println(array[i].getName()+"'s group is : "+array[i].getGroup());
        }
    }

    private static class Person{

        private String name;
        private int group;

        public Person(String name,int group){
            this.name = name;
            this.group = group;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }
    }
}
