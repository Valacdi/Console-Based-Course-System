package utils;

import model.Administrator;
import model.Course;
import model.CourseIS;
import model.Student;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CourseManagement {

  public static void manageCourses(
      Scanner scanner, CourseIS courseIS, Administrator administrator, Student student)
      throws ParseException {
    String bookCmd = "";
    while (!bookCmd.equals("q")) {
      System.out.println(
          "Choose an action:\n"
              + "\t 1 - create a course.\n"
              + "\t 2 - update a course.\n"
              + "\t 3 - check course info.\n"
              + "\t 4 - delete a course.\n"
              + "\t 5 - print all courses.\n"
              + "\t 6 - enroll to course.\n"
              + "\t q - quit.\n");
      bookCmd = scanner.next();
      switch (bookCmd) {
        case "1":
          addCourse(scanner, courseIS, administrator);
          break;
        case "2":
          updateCourse(scanner, courseIS, administrator);
          break;
        case "3":
          printCourseData(scanner, courseIS);
          break;
        case "4":
          deleteCourse(scanner, courseIS, administrator);
          break;
        case "5":
          courseIS.getAllCourses().forEach(course -> System.out.println(course.toString()));
          break;
        case "6":
          enrollToCourse(scanner, courseIS, student);
          break;
        case "q":
          System.out.println("Returning to main menu.\n");
          break;
        default:
          System.out.println("Wrong command, read again.\n");
      }
    }
  }

  private static void enrollToCourse(Scanner scanner, CourseIS courseIS, Student student) {
    if (student == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter course name: \n");
      String name = scanner.next();
      Course course = getCourse(courseIS, name);
      if (course != null) {
        student.getMyEnrolledCourses().add(course);
        course.getEnrolledUsers().add(student);
        System.out.println("Successfully enrolled.\n");
      } else {
        System.out.println("No such course exists.\n");
      }
      }
  }

  private static void deleteCourse(
      Scanner scanner, CourseIS courseIS, Administrator administrator) {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      // Sakykime, kad musu kursai negali buti vienodais pavadinimais
      System.out.println("Enter course name: \n");
      String name = scanner.next();
      Course course = getCourse(courseIS, name);
      if (course != null) {
        courseIS.getAllCourses().remove(course);
        System.out.println("Successfully removed.\n");
      } else {
        System.out.println("No such course exists.\n");
      }
    }
  }

  private static void printCourseData(Scanner scanner, CourseIS courseIS) {
    System.out.println("Enter course name: \n");
    String name = scanner.next();
    System.out.println(getCourse(courseIS, name).toString());
  }

  private static void updateCourse(
      Scanner scanner, CourseIS courseIS, Administrator administrator) throws ParseException {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println("Enter course name: \n");
      String name = scanner.next();
      Course course = getCourse(courseIS, name);
      if (course != null) {
        System.out.println(
            "Choose an action:\n"
                + "\t 1 - update name.\n"
                + "\t 2 - update start date.\n"
                + "\t 3 - update end date.\n");
        String option = scanner.next();
        String in = "";
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD");
        switch (option) {
          case "1":
            System.out.println("Enter new name: \n");
            name = scanner.next();
            Course courseNewCheck = getCourse(courseIS, name);
            if (courseNewCheck == null) {
              course.setName(name);
            }
            break;
          case "2":
            System.out.println("Enter new start date: \n");
            in = scanner.next();
            course.setStartDate(df.parse(in));
            break;
          case "3":
            System.out.println("Enter new end date: \n");
            in = scanner.next();
            course.setEndDate(df.parse(in));
            break;
          default:
            System.out.println("Wrong command, read again.\n");
        }
      } else {
        System.out.println("No such course exists.\n");
      }
    }
  }

  private static void addCourse(Scanner scanner, CourseIS courseIS, Administrator administrator)
      throws ParseException {
    if (administrator == null) {
      System.out.println("You have no rights");
    } else {
      System.out.println(
          "Enter course info [name,start date (format 2000-01-01),end date (format 2000-01-01),price (format 30.99)] \n");
      String bookData = scanner.next();
      String[] values = bookData.split(",");
      if (!courseExists(courseIS, values[0])) {

        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD");
        Course course =
            new Course(
                values[0],
                df.parse(values[1]),
                df.parse(values[2]),
                administrator,
                Double.parseDouble(values[3]));
        courseIS.getAllCourses().add(course);
      } else {
        System.out.println("Course already exists.\n");
      }
    }
  }

  private static boolean courseExists(CourseIS courseIS, String name) {
    return courseIS.getAllCourses().stream().anyMatch(c -> c.getName().equals(name));
  }

  public static Course getCourse(CourseIS courseIS, String name) {
    return courseIS.getAllCourses().stream()
        .filter(c -> c.getName().equals(name))
        .findFirst()
        .orElse(null);
  }
}
