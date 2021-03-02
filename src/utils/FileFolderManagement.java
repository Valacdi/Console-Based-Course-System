package utils;

import model.*;

import java.util.Scanner;

public class FileFolderManagement {
  public static void manageFilesFolders(
      Scanner scanner, CourseIS courseIS, Administrator administrator, Student student) {
    // reikia patikrint ar isvis egzistuoja toks kursas
    System.out.println("With which course you would like to work with? ");
    String name = scanner.next();
    Course course = CourseManagement.getCourse(courseIS, name);
    String bookCmd = "";
    while (!bookCmd.equals("q")) {
      System.out.println(
          "Choose an action:\n"
              + "\t 1 - manage folders.\n"
              + "\t 2 - manage files.\n"
              + "\t q - quit.\n");
      bookCmd = scanner.next();
      switch (bookCmd) {
        case "1":
          manageFolders(scanner, course, administrator, student);
          break;
        case "2":
          manageFiles(scanner, course, administrator, student);
          break;
        case "q":
          System.out.println("Returning to main menu.\n");
          break;
        default:
          System.out.println("Wrong command, read again.\n");
      }
    }
  }

  private static void manageFiles(
      Scanner scanner, Course course, Administrator administrator, Student student) {
    String bookCmd = "";
    while (!bookCmd.equals("q")) {
      System.out.println(
          "Choose an action:\n"
              + "\t 1 - create file.\n"
              + "\t 2 - update file.\n"
              + "\t 3 - delete file.\n"
              + "\t 4 - check file info.\n"
              + "\t q - quit.\n");
      bookCmd = scanner.next();
      switch (bookCmd) {
        case "1":
          createFile(scanner, course, administrator);
          break;
        case "2":
          updateFile(scanner, course, administrator);
          break;
        case "3":
          deleteFile(scanner, course, administrator);
          break;
        case "4":
          printFileData(scanner, course, administrator);
          break;
        case "q":
          System.out.println("Returning to file and folder management.\n");
          break;
        default:
          System.out.println("Wrong command, read again.\n");
      }
    }
  }

  private static void updateFile(Scanner scanner, Course course, Administrator administrator) {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter folder name: "); // kuriame folderyje update faila
      String folderName = scanner.next();
      Folder folder = getAllFolders(course, folderName); // tikriname ar toks folderis yra
      if (folder != null) {
        System.out.println("Enter file name: ");
        String fileName = scanner.next();
        File file = getAllFiles(folder, fileName); // tikriname ar toks failas yra folderyje
        if (file != null) {
          System.out.println("Enter new file name: ");
          String newFileName = scanner.next();
          File fileNewCheck =
              getAllFiles(folder, newFileName); // tikriname ar nera tokio pacio failo
          if (fileNewCheck == null) {
            folder.getFolderFiles().get(0).setName(newFileName);
            System.out.println("File successfully renamed.");
          } else {
            System.out.println("File already exists");
          }
        }
      } else {
        System.out.println("No such folder exists.\n");
      }
    }
  }

  private static File getAllFiles(Folder folder, String newFileName) {
    return folder.getFolderFiles().stream()
        .filter(c -> c.getName().equals(newFileName))
        .findFirst()
        .orElse(null);
  }

  private static void deleteFile(Scanner scanner, Course course, Administrator administrator) {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter folder name: ");
      String folderName = scanner.next();
      Folder folder = getAllFolders(course, folderName);
      if (folder != null) {
        System.out.println("Enter file name: ");
        String fileName = scanner.next();
        File file = getAllFiles(folder, fileName);
        if (file != null) {
          folder.getFolderFiles().remove(file);
          System.out.println("File " + fileName + " successfully removed from " + folderName + " folder.");
        } else {
          System.out.println("No such file exists.\n");
        }
      } else {
        System.out.println("No such folder exists.\n");
      }
    }
  }

  private static void printFileData(Scanner scanner, Course course, Administrator administrator) {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter folder name: ");
      String folderName = scanner.next();
      Folder folder = getAllFolders(course, folderName);
      if (folder != null) {
        System.out.println("Enter file name: \n");
        String fileName = scanner.next();
        System.out.println(getAllFiles(folder, fileName).toString());
      } else {
        System.out.println("No such folder exists.\n");
      }
    }
  }

  private static void createFile(Scanner scanner, Course course, Administrator administrator) {
    // reikia idet patikrinima ar jau yra toks file
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter folder name: ");
      String folderName = scanner.next();
      Folder folder = getAllFolders(course, folderName);
      if (folder != null) {
        System.out.println("Enter file name: \n");
        String fileName = scanner.next();
        File file = new File(fileName);
        folder.getFolderFiles().add(file);
        System.out.println("File " + fileName + " successfully created and added to " + folderName + " folder.");
      } else {
        System.out.println("No such folder exists.\n");
      }
    }
  }

  private static void manageFolders(
      Scanner scanner, Course course, Administrator administrator, Student student) {
    String bookCmd = "";
    while (!bookCmd.equals("q")) {
      System.out.println(
          "Choose an action:\n"
              + "\t 1 - create folder.\n"
              + "\t 2 - update folder.\n"
              + "\t 3 - delete folder.\n"
              + "\t 4 - check folder info.\n"
              + "\t q - quit.\n");
      bookCmd = scanner.next();
      switch (bookCmd) {
        case "1":
          createFolder(scanner, course, administrator, student);
          break;
        case "2":
          updateFolder(scanner, course, administrator);
          break;
        case "3":
          deleteFolder(scanner, course, administrator);
          break;
        case "4":
          printFolderData(scanner, course, administrator);
          break;
        case "q":
          System.out.println("Returning to file and folder management.\n");
          break;
        default:
          System.out.println("Wrong command, read again.\n");
      }
    }
  }

  private static void printFolderData(Scanner scanner, Course course, Administrator administrator) {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter folder name: \n");
      String folderName = scanner.next();
      System.out.println(getAllFolders(course, folderName).getFolderFiles());
    }
  }

  private static void updateFolder(Scanner scanner, Course course, Administrator administrator) {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter folder name: ");
      String folderName = scanner.next();
      Folder folder = getAllFolders(course, folderName);
      if (folder != null) {
        System.out.println("Enter new name: \n");
        String newFolderName = scanner.next();
        Folder folderNewCheck = getAllFolders(course, newFolderName);
        if (folderNewCheck == null) {
          folder.setName(newFolderName);
          System.out.println("Folder successfully renamed.");
        } else {
          System.out.println("Folder already exists");
        }
      } else {
        System.out.println("No such folder exists.\n");
      }
    }
  }

  private static void deleteFolder(Scanner scanner, Course course, Administrator administrator) {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter folder name: ");
      String folderName = scanner.next();
      Folder folder = getAllFolders(course, folderName);
      if (folder != null) {
        course.getFolders().remove(folder);
        System.out.println("Successfully removed.\n");
      } else {
        System.out.println("No such folder exists.\n");
      }
    }
  }

  private static void createFolder(
      Scanner scanner, Course course, Administrator administrator, Student student) {
    // reikia idet patikrinima ar jau yra toks folderis
    if (administrator == null) {
      System.out.println("You have no rights.");
    } else {
      System.out.println("Enter folder name: ");
      String folderName = scanner.next();
      Folder folder = new Folder(folderName);
      course.getFolders().add(folder);
      System.out.println("Folder successfully created.");
    }
  }

  public static Folder getAllFolders(Course course, String name) {
    return course.getFolders().stream()
        .filter(c -> c.getName().equals(name))
        .findFirst()
        .orElse(null);
    // naudojama patikrint ar egzistuoja toks folderis
  }
}
