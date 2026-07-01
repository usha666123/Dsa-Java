public class Box<T>{
    T t;
public void add(T t){
this.t=t;
}

T get(){
return t;
}

public void display(){
System.out.println(this.t);
}

static class Student{
    int id;
    String name;
    double cgpa;
    public Student(int id, String name, double cgpa) {
            this.id = id;
            this.name = name;
            this.cgpa = cgpa;
        }
        @Override
        public String toString() {
            return "Student[ID=" + id + ", Name=" + name + ", CGPA=" + cgpa + "]";
        }
    }

public static void main(String args[]){
    Box<Integer> b1=new Box<>();
    b1.add(12);
    int x=b1.get();
    b1.display();
    Box<String> b2=new Box<>();
    b2.add("hello");
    String y=b2.get();
    b2.display();
    Box<Student> b3=new Box<>();
    b3.add(new Student(1,"usha",9.1));
   // Student z=b3.get();
    b3.display();

}

}