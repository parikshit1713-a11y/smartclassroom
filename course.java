public class Course {
    String name;
    int students;
    TimeSlot timeSlot;
    int assignedRoom = -1;

    Course(String name, int students, TimeSlot t) {
        this.name = name;
        this.students = students;
        this.timeSlot = t;
    }
}
