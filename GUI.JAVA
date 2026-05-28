import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI {

    private AllocationSystem system = new AllocationSystem();
    private DefaultTableModel tableModel;

    public GUI() {

        system.loadFromFile();

        JFrame frame = new JFrame("Smart Classroom Allocation");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6, 1));

        JButton addBtn = new JButton("Add");
        JButton allocateBtn = new JButton("Allocate");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");

        sidebar.add(addBtn);
        sidebar.add(allocateBtn);
        sidebar.add(saveBtn);
        sidebar.add(loadBtn);

        JPanel input = new JPanel(new GridLayout(2, 4));

        JTextField course = new JTextField();
        JTextField students = new JTextField();
        JTextField day = new JTextField();
        JTextField slot = new JTextField();

        input.add(new JLabel("Course"));
        input.add(new JLabel("Students"));
        input.add(new JLabel("Day"));
        input.add(new JLabel("Slot"));

        input.add(course);
        input.add(students);
        input.add(day);
        input.add(slot);

        tableModel = new DefaultTableModel(
                new String[]{"Course", "Students", "Day", "Slot", "Room"}, 0);

        JTable table = new JTable(tableModel);

        JScrollPane scroll = new JScrollPane(table);

        system.addRoom(1, 30);
        system.addRoom(2, 50);
        system.addRoom(3, 100);

        refresh();

        addBtn.addActionListener(e -> {
            try {
                system.addCourse(
                        course.getText(),
                        Integer.parseInt(students.getText()),
                        day.getText(),
                        slot.getText()
                );
                refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input");
            }
        });

        allocateBtn.addActionListener(e -> {
            system.allocate();
            refresh();
        });

        saveBtn.addActionListener(e -> system.saveToFile());

        loadBtn.addActionListener(e -> {
            system.loadFromFile();
            refresh();
        });

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(input, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void refresh() {
        tableModel.setRowCount(0);

        for (Course c : system.getCourses()) {
            tableModel.addRow(new Object[]{
                    c.name,
                    c.students,
                    c.timeSlot.day,
                    c.timeSlot.slot,
                    (c.assignedRoom == -1 ? "Not Assigned" : c.assignedRoom)
            });
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}
