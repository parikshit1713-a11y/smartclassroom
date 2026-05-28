import java.util.*;
import java.io.*;

public class AllocationSystem {

    private List<Classroom> rooms = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private Map<String, Set<Integer>> scheduleMap = new HashMap<>();

    public void addRoom(int id, int capacity) {
        rooms.add(new Classroom(id, capacity));
    }

    public void addCourse(String name, int students, String day, String slot) {
        courses.add(new Course(name, students, new TimeSlot(day, slot)));
    }

    private boolean isAvailable(int roomId, TimeSlot t) {
        String key = t.day + "_" + t.slot;
        return !scheduleMap.getOrDefault(key, new HashSet<>()).contains(roomId);
    }

    private void occupy(int roomId, TimeSlot t) {
        String key = t.day + "_" + t.slot;
        scheduleMap.putIfAbsent(key, new HashSet<>());
        scheduleMap.get(key).add(roomId);
    }

    public void allocate() {
        scheduleMap.clear();

        for (Course c : courses) {
            c.assignedRoom = -1;

            for (Classroom r : rooms) {
                if (r.capacity >= c.students && isAvailable(r.roomId, c.timeSlot)) {
                    c.assignedRoom = r.roomId;
                    occupy(r.roomId, c.timeSlot);
                    break;
                }
            }
        }
    }

    public List<Course> getCourses() {
        return courses;
    }

    // 🔥 SAVE METHOD
    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter("data.txt");

            for (Course c : courses) {
                fw.write(c.name + "," + c.students + "," +
                        c.timeSlot.day + "," + c.timeSlot.slot + "," +
                        c.assignedRoom + "\n");
            }

            fw.close();
            System.out.println("Saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 LOAD METHOD
    public void loadFromFile() {
        courses.clear();

        try {
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                Course c = new Course(
                        d[0],
                        Integer.parseInt(d[1]),
                        new TimeSlot(d[2], d[3])
                );

                c.assignedRoom = Integer.parseInt(d[4]);
                courses.add(c);
            }

            br.close();

        } catch (Exception e) {
            System.out.println("No file found");
        }
    }
}
