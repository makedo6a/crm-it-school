package kg.itschool;

 import kg.itschool.dao.daoutil.DaoFactory;
import kg.itschool.dao.ManagerDao;
 import kg.itschool.model.Course;
 import kg.itschool.model.CourseFormat;
 import kg.itschool.model.Manager;

 import java.time.LocalDate;
 import java.time.LocalTime;
 import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        CourseFormat bootcampFormat = new CourseFormat();
        bootcampFormat.setId(1L);
        bootcampFormat.setFormat("BOOTCAMP");
        bootcampFormat.setCourseDurationWeeks(12);
        bootcampFormat.setLessonDuration(LocalTime.of(3,0));
        bootcampFormat.setLessonsPerWeek(5);
        bootcampFormat.setOnline(false);

        CourseFormat ordinaryFormat = new CourseFormat();
        ordinaryFormat.setId(2L);
        ordinaryFormat.setFormat("ORDINARY");
        ordinaryFormat.setCourseDurationWeeks(30);
        ordinaryFormat.setLessonDuration(LocalTime.of(2,0));
        ordinaryFormat.setLessonsPerWeek(3);
        ordinaryFormat.setOnline(true);

        Course javaCourse = new Course();
        javaCourse.setId(1L);
        javaCourse.setName("Java");
        javaCourse.setPrice(39000);
        javaCourse.setCourseFormat(bootcampFormat);

        Course pythonCourse = new Course();
        pythonCourse.setId(2L);
        pythonCourse.setName("Python");
        pythonCourse.setPrice(29000);
        pythonCourse.setCourseFormat(ordinaryFormat);

        System.out.println(javaCourse);
        System.out.println(pythonCourse);
    }
}

