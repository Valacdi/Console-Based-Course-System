package utils;

import model.Administrator;
import model.CourseIS;
import model.Student;

import java.util.Scanner;

public class StudentManagement {
  public static void manageStudents(
      Scanner scanner, CourseIS courseIS, Administrator administrator) {
    String bookCmd = "";
    while (!bookCmd.equals("q")) {
      System.out.println(
          "Choose an action:\n"
              + "\t 1 - check all students.\n"
              + "\t 2 - delete student.\n"
              + "\t 3 - create student.\n"
              + "\t 4 - update student\n"
              + "\t q - quit.\n");
      bookCmd = scanner.next();
      switch (bookCmd) {
        case "1":
          checkAllStudents(courseIS);
          break;
        case "2":
          deleteStudent(scanner, courseIS, administrator);
          break;
        case "3":
          addStudent(scanner, courseIS, administrator);
          break;
        case "4":
          updateStudent(scanner, courseIS, administrator);
          break;
        case "q":
          System.out.println("Returning to main menu.\n");
          break;
        default:
          System.out.println("Wrong command, read again.\n");
      }
    }
  }

  private static void updateStudent(Scanner scanner, CourseIS courseIS, Administrator administrator) {
    // reikia patikrint ar toks admin jau yra
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      checkAllStudents(courseIS);
      System.out.println("Type in the index of the student you would like to update: ");
      int index = scanner.nextInt();
      System.out.println("Enter new login and password [login,psw]: ");
      String updateInfo = scanner.next();
      String[] values = updateInfo.split(",");
      courseIS.getAllStudents().get(index).setLogin(values[0]); // kol kas tik login kartu su psw galima pakeist
      courseIS.getAllStudents().get(index).setPsw(values[1]);
      System.out.println("Student successfully updated.");
    }
  }

  private static void addStudent(Scanner scanner, CourseIS courseIS, Administrator administrator) {
    // reikia patikrint ar toks student jau yra
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter student info [login,psw,email,accNumber]\n");
      String bookData = scanner.next();
      String[] values = bookData.split(",");
      courseIS.getAllStudents().add(new Student(values[0], values[1], values[2], values[3]));
      System.out.println("Student successfully added.");
    }
  }

  private static void checkAllStudents(CourseIS courseIS) {
    for (int i = 0; i < courseIS.getAllStudents().size(); i++) {
      System.out.println(i + ". " + courseIS.getAllStudents().get(i));
    }
  }

  private static void deleteStudent(
      Scanner scanner, CourseIS courseIS, Administrator administrator) {
    // reikia patikrint ar toks student jau yra
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      for (int i = 0; i < courseIS.getAllStudents().size(); i++) {
        System.out.println(i + ". " + courseIS.getAllAdmins().get(i));
      }
      System.out.println("Type in the index of the student you would like to remove: ");
      int index = scanner.nextInt();
      if (index > courseIS.getAllStudents().size()) {
        System.out.println("Error. out of bounds.");
      } else {
        courseIS.getAllStudents().remove(index);
        System.out.println("Student successfully removed.");
      }
    }
  }
}
