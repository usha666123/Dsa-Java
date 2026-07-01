@FunctionalInterface
interface Calculator {
    public int operation(int a,int b);
}

public class FuncIntfDemo {

    public static int Oper(int a,int b,Calculator c){
        return c.operation(a,b);
    }
public static void main(String args[]){
    Calculator add= (a,b)-> a+b;
    Calculator sub=(a,b)->a-b;
    Calculator mul=(a,b)->a*b;
    System.out.println(Oper(10,5,add));
     System.out.println(Oper(10,5,sub));



}
}