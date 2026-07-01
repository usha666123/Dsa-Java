import java.util.*;
public class Streamsdemo {
    

    static class Student{
        int id;
        String name;
        double cgpa;
        public Student(int id, String name, double cgpa) {
            this.id = id;
            this.name = name;
            this.cgpa = cgpa;
        }
         public double getCgpa() { return cgpa; }
        @Override
        public String toString() {
            return "Student[ID=" + id + ", Name=" + name + ", CGPA=" + cgpa + "]";
        }
    }


    public static void main(String[] args) {
        List<Student> students=new ArrayList<>();
        students.add(new Student(101, "Liam", 3.85));
        students.add(new Student(102, "Olivia", 3.92));
        students.add(new Student(103, "Noah", 3.45));
        students.add(new Student(104, "Emma", 3.78));
        students.add(new Student(105, "Oliver", 2.91));
        students.add(new Student(106, "Ava", 4.00));
        students.add(new Student(107, "Elijah", 3.65));
        students.add(new Student(108, "Charlotte", 3.12));
        students.add(new Student(109, "William", 3.50));
        students.add(new Student(110, "Sophia", 3.88));
        students.add(new Student(111, "James", 2.75));
        students.add(new Student(112, "Amelia", 3.95));
        students.add(new Student(113, "Benjamin", 3.33));
        students.add(new Student(114, "Isabella", 3.69));
        students.stream()
            .filter(s -> s.getCgpa() > 8.5)
            .forEach(System.out::println);

            students.stream()
            .sorted(Comparator.comparingDouble(Student::getCgpa))
            .forEach(System.out::println);

           double avg = students.stream()
            .mapToDouble(Student::getCgpa)
            .average()
            .orElse(0.0);
            

    }
}
