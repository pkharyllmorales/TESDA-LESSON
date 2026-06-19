class Student {
    private String firstName;
    private String lastName;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void printFullName() {
        System.out.println(this.firstName + " " + this.lastName);
    }
}

public class Chap4Task10 {
    public static void main(String[] args) {
        Student[] students = new Student[] {
            new Student("Princess Kharyll", "Morales"),
            new Student("Cecile", "Liscano"),
            new Student("Samuel", "Morales"),
        };

        for (Student s : students) {
            s.printFullName();
        }
    }
}