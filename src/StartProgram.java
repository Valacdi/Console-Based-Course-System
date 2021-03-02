import model.*;
import utils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class StartProgram {

  public static void main(String[] args) {

    boolean userExists = false;
    Administrator currentAdmin = null;
    Student currentStudent = null;
    CourseIS courseIS = RW.loadCourseIS();

    if (courseIS == null) {
      System.out.println("No IS loaded");

      courseIS = new CourseIS("Moodle1", LocalDate.now(), "V1");
      courseIS.getAllAdmins().add(new Administrator("admin", "admin", "--", "--"));
    }

    Scanner scanner = new Scanner(System.in);
    System.out.println("What are You? A/S: ");
    String userType = scanner.next();
    System.out.println("Enter Your login:");
    String userLogin = scanner.next();
    System.out.println("Enter Your password:");
    String userPsw = scanner.next();

    if (userType.equals("A")) {
      userExists =
          courseIS.getAllAdmins().stream()
              .anyMatch(
                  admin -> admin.getLogin().equals(userLogin) && admin.getPsw().equals(userPsw));

      Administrator administrator =
          courseIS.getAllAdmins().stream()
              .filter(admin -> admin.getLogin().equals(userLogin) && admin.getPsw().equals(userPsw))
              .findFirst()
              .orElse(null);

      ArrayList<Administrator> admins = courseIS.getAllAdmins();

      for (Administrator a : admins) {
        if (a.getLogin().equals(userLogin) && a.getPsw().equals(userPsw)) {
          currentAdmin = a;
          break;
        }
      }

    } else if (userType.equals("S")) {
      userExists =
          courseIS.getAllStudents().stream()
              .anyMatch(
                  student ->
                      student.getLogin().equals(userLogin) && student.getPsw().equals(userPsw));

      Student student =
          courseIS.getAllStudents().stream()
              .filter(s -> s.getLogin().equals(userLogin) && s.getPsw().equals(userPsw))
              .findFirst()
              .orElse(null);
      // Tas pats tik gal aiskiau
      ArrayList<Student> students = courseIS.getAllStudents();
      for (Student s : students) {
        if (s.getLogin().equals(userLogin) && s.getPsw().equals(userPsw)) {
          currentStudent = s;
          break;
        }
      }
    } else {
      System.out.println("Read again.");
    }

    if (userExists) {
      String cmd = "";
      while (!cmd.equals("q")) {
        System.out.println(
            "Choose an action:\n"
                + "\t 1 - manage courses.\n"
                + "\t 2 - manage administrators.\n"
                + "\t 3 - manage students.\n"
                + "\t 4 - manage files and folders.\n"
                + "\t 5 - save to file.\n"
                + "\t q - quit.\n");
        cmd = scanner.next();
        try {
          switch (cmd) {
            case "1":
              CourseManagement.manageCourses(scanner, courseIS, currentAdmin, currentStudent);
              break;
            case "2":
              AdministratorManagement.manageAdmins(scanner, courseIS, currentAdmin);
              break;
            case "3":
              StudentManagement.manageStudents(scanner, courseIS, currentAdmin);
              break;
            case "4":
              FileFolderManagement.manageFilesFolders(scanner, courseIS, currentAdmin, currentStudent);
              break;
            case "5":
              RW.saveCourseIS(courseIS);
              System.out.println("Course system successfully saved.");
              break;
            case "q":
              System.out.println("Exit program.");
              break;
            default:
              System.out.println("Wrong input, read again.\n");
          }
        } catch (Exception e) {
          System.out.println("There were errors");
          e.printStackTrace();
        }
      }

    } else {
      System.out.println("such user does not exit.");
    }
  }
}
