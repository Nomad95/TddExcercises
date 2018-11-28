package studentList.services.grades;

import studentList.model.Grade;
import studentList.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface Grades {

    void add(Student student, Grade grade);

    List<Grade> getGradesFor(Student student);

    void registerStudent(Student student);

    void editGrade(Student student, Grade from, Grade to);

    void deleteGrade(Student student, Grade grade);

    double getGradeAverageFor(Student student);

    double getClassGradeAverage();

    void removeStudentGrades(Student student);
}
