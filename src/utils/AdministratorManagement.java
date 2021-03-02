package utils;

import model.Administrator;
import model.CourseIS;

import java.util.Optional;
import java.util.Scanner;

public class AdministratorManagement {
  public static void manageAdmins(Scanner scanner, CourseIS courseIS, Administrator administrator) {

    String bookCmd = "";
    while (!bookCmd.equals("q")) {
      System.out.println(
          "Choose an action:\n"
              + "\t 1 - check all admins.\n"
              + "\t 2 - delete admin.\n"
              + "\t 3 - create admin.\n"
              + "\t 4 - update admin.\n"
              + "\t q - quit.\n");
      bookCmd = scanner.next();
      switch (bookCmd) {
        case "1":
          checkAllAdmins(courseIS);
          break;
        case "2":
          deleteAdmin(scanner, courseIS, administrator);
          break;
        case "3":
          addAdmin(scanner, courseIS, administrator);
          break;
        case "4":
          updateAdmin(scanner, courseIS, administrator);
          break;
        case "q":
          System.out.println("Returning to main menu.\n");
          break;
        default:
          System.out.println("Wrong command, read again.\n");
      }
    }
  }

  private static void updateAdmin(Scanner scanner, CourseIS courseIS, Administrator administrator) {
    // reikia patikrint ar toks admin jau yra
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      checkAllAdmins(courseIS);
      System.out.println("Type in the index of the admin you would like to update: ");
      int index = scanner.nextInt();
      System.out.println("Enter new login and password [login,psw]: ");
      String updateInfo = scanner.next();
      String[] values = updateInfo.split(",");
      courseIS.getAllAdmins().get(index).setLogin(values[0]); // kol kas tik login kartu su psw galima pakeist
      courseIS.getAllAdmins().get(index).setPsw(values[1]);
      System.out.println("Admin successfully updated.");
    }
  }

  private static void addAdmin(Scanner scanner, CourseIS courseIS, Administrator administrator) {
    // reikia patikrint ar toks admin jau yra
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter admin info [login,psw,email,phoneNumber]\n");
      String bookData = scanner.next();
      String[] values = bookData.split(",");
      courseIS.getAllAdmins().add(new Administrator(values[0], values[1], values[2], values[3]));
      System.out.println("Admin successfully added.");
    }
  }

  private static void checkAllAdmins(CourseIS courseIS) {
    for (int i = 0; i < courseIS.getAllAdmins().size(); i++) {
      System.out.println(i + ". " + courseIS.getAllAdmins().get(i));
    }
  }

  private static void deleteAdmin(Scanner scanner, CourseIS courseIS, Administrator administrator) {
    // reikia patikrint ar toks admin jau yra
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      checkAllAdmins(courseIS);
      System.out.println("Type in the index of the admin you would like to remove: ");
      int index = scanner.nextInt();
      if (index > courseIS.getAllAdmins().size()) {
        System.out.println("Error. out of bounds.");
      } else {
        courseIS.getAllAdmins().remove(index);
        System.out.println("Admin successfully removed.");
      }
    }
  }
}
