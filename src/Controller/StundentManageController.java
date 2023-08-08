package Controller;

import IO.FileIO;
import Model.Student;
import Validation.CheckInputValue;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class StundentManageController implements IFunction<Student>, FileIO<Student> {

    Student s1 = new Student("HE0001", "Nam", 19, true, "HaNoi", 7.5);
    Student s2 = new Student("HE0002", "Anh", 19, true, "HaNoi", 8);
    Student s3 = new Student("HE0003", "Nghia", 19, true, "HaNoi", 3.5);
    Student s4 = new Student("HE0004", "Trung", 19, true, "HaNoi", 6.5);
    Student s5 = new Student("HE0005", "Tai", 19, true, "HaNoi", 6.5);
    Scanner scanner = new Scanner(System.in);
    List<Student> students = new ArrayList<>();

    public void addStudent(){
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
    }

    @Override
    public void insertItem() {
        String code = CheckInputValue.checkString("Nhập mã sinh viên:");
        Student deleteStudent = null;
        for (Student student : students) {
            if (code.equals(student.getMaSinhVien())) {
                deleteStudent = student;
            }
        }
        if (deleteStudent != null) {
            System.out.println("Sinh viên đã tồn tại");
        } else {
            String name = CheckInputValue.checkString("Nhập họ tên sinh viên:");
            int age = CheckInputValue.checkInt("Nhập tuổi sinh viên từ 18 đến 27", 17, 28);
            System.out.println("Nhập vào giới tính sinh viên true(name)/false(nữ):");
            boolean gender = scanner.nextBoolean();
            String address = CheckInputValue.checkString("Nhập địa chỉ sinh viên:");
            Double avgPoint = CheckInputValue.checkDouble("Nhập điểm trung bình của sinh viên:", 0, 11);
            Student student = new Student(code, name, age, gender, address, avgPoint);
            students.add(student);
        }
    }

    @Override
    public void removeItem() {
        String choice = CheckInputValue.checkString("Nhập mã sinh viên muốn xóa:");
        Student deleteStudent = null;
        for (Student student : students) {
            if (choice.equals(student.getMaSinhVien())) {
                deleteStudent = student;
            }
        }
        if (deleteStudent != null) {
            students.remove(deleteStudent);
        } else {
            System.out.println("Không tìm thấy sinh viên!");
        }
    }

    @Override
    public void updateItem() {
        String choice = CheckInputValue.checkString("Nhập mã sinh viên muốn sửa:");
        Student updateStudent = null;
        for (Student student : students) {
            if (choice.equals(student.getMaSinhVien())) {
                updateStudent = student;
            }
        }
        if (updateStudent != null) {
            updateStudent.setMaSinhVien(CheckInputValue.checkString("Nhập mã sinh viên cần sửa:"));
            updateStudent.setHoTen(CheckInputValue.checkString("Nhập họ tên cần sửa:"));
            updateStudent.setTuoi(CheckInputValue.checkInt("Nhập tuổi cần sửa:", 17, 28));
            System.out.println("Nhập giới tính cần sửa:");
            updateStudent.setGioiTinh(scanner.nextBoolean());
            updateStudent.setDiaChi(CheckInputValue.checkString("Nhập địa chỉ mới:"));
            updateStudent.setDiemTrungBinh(CheckInputValue.checkDouble("Nhập điểm mới:", -1, 11));
        } else {
            System.out.println("Không tìm thấy sinh viên!");
        }
    }

    @Override
    public void displayItem(List<Student> itemList) {
        System.out.println("Danh sách các sinh viên hiện có:");
        for(Student student : itemList){
            System.out.println(student);
        }
    }

    @Override
    public String findItemID() {
        return null;
    }

    @Override
    public void write(List<Student> students, String path) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(students);
            System.out.println("Lưu file thanh công!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Student> read(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            students = (List<Student>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return students;
    }

    public void menu() {
        do {
            System.out.println("---- CHƯƠNG TRÌNH QUẢN LÝ SINH VIÊN ----");
            System.out.println("Chọn chức năng theo số(để tiếp tục)");
            System.out.println("1. Xem danh sách sinh viên");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Sắp xếp");
            System.out.println("6. Đọc từ file");
            System.out.println("7. Ghi từ file");
            System.out.println("8. Thoát");
            int choice = CheckInputValue.checkInt("Chọn chức năng:", 0, 9);
            switch (choice) {
                case 1:
                    displayItem(students);
                    break;
                case 2:
                    insertItem();
                    break;
                case 3:
                    updateItem();
                    break;
                case 4:
                    removeItem();
                    break;
                case 5:
                    menuSort();
                    break;
                case 6:
                    read("src/IO/Student");
                    break;
                case 7:
                    write(students, "src/IO/Student");
                    break;
                case 8:
                    System.exit(0);
            }
        } while (true);
    }
    public void menuSort(){
        int choice;
        do{
            System.out.println("Sắp sếp sinh viên theo điểm trung bình");
            System.out.println("1. Sắp xếp điểm trung bình tăng dần");
            System.out.println("2. Sắp xếp điểm trung bình giảm dần");
            System.out.println("3. Thoát");
            choice = scanner.nextInt();
            switch (choice){
                case 1:
                    Collections.sort(students, new Comparator<Student>() {
                        @Override
                        public int compare(Student o1, Student o2) {
                            return o1.getDiemTrungBinh() > o2.getDiemTrungBinh()?  1 : -1;
                        }
                    });
                    for (Student student : students) {
                        System.out.println(student.toString());
                    }
                    break;
                case 2:
                    Collections.sort(students, new Comparator<Student>() {
                        @Override
                        public int compare(Student o1, Student o2) {
                            return o1.getDiemTrungBinh() < o2.getDiemTrungBinh()?  1 : -1;
                        }
                    });
                    for (Student student : students) {
                        System.out.println(student.toString());
                    }
                    break;
            }
        }while(choice >=1 && choice <= 2);
    }


}
