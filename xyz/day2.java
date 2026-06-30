// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.*;
class Student{
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
        return "ID: " + id + ", Name: " + name + ", CGPA: " + cgpa;
    }
}
class Main {
    public static void main(String[] args) {
       
       System.out.println("ArrayList duplicates allowed maintains insertion order");
       List<Student> l=new ArrayList<>();
       l.add(new Student(1,"usha",9.1));
       l.add(new Student(2,"hari",8.5));
       l.add(new Student(3,"renu",9.6));
       for(int i=0;i<l.size();i++){
           System.out.println(l.get(i));
       }
       System.out.println("HashSet no duplicates  random");
       Set<Student> s=new HashSet<>();
       s.add(new Student(1,"usha",9.1));
       s.add(new Student(2,"hari",8.5));
       s.add(new Student(3,"renu",9.6));
       s.add(new Student(1,"usha",9.1));
       for(Student st:s){
           System.out.println(st);
       }
System.out.println("TreeSet no duplicates sorted");
       Set<Student> t=new TreeSet<>(Comparator.comparingDouble((Student st )-> st.cgpa).reversed());
         t.add(new Student(1,"usha",9.1));
       t.add(new Student(2,"hari",8.5));
       t.add(new Student(3,"renu",9.6));
       t.add(new Student(1,"usha",9.1));
       for(Student st:t){
           System.out.println(st);
       }
System.out.println("HashMap no duplicates  random order");
       HashMap<Integer,Student> h=new HashMap<>();
       h.put(1,new Student(1,"usha",9.1));
       h.put(2,new Student(2,"hari",8.5));
       h.put(3,new Student(3,"renu",9.6));
       for (Map.Entry<Integer, Student> entry : h.entrySet()) {
            System.out.println("ID (Key): " + entry.getKey() + " -> " + entry.getValue());
        }
       
       }
}