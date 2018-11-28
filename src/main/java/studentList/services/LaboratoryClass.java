package studentList.services;

import lombok.NonNull;
import studentList.model.DiaryEntry;
import studentList.model.Grade;
import studentList.model.Student;
import studentList.services.diary.Diary;
import studentList.services.diary.CollegeDiary;
import studentList.services.grades.Grades;
import studentList.services.grades.CollegeGrades;

import java.time.LocalDate;
import java.util.*;

public class LaboratoryClass {

    private static final int ORDINARY_GROUP_SIZE = 15;
    private final Set<Student> students;
    private final Diary diary;
    private final Grades grades;

    public LaboratoryClass() {
        this.students = new HashSet<>(ORDINARY_GROUP_SIZE);
        this.diary = new CollegeDiary(this);
        this.grades = new CollegeGrades(this);
    }

    public void addStudent(Student student) {
        if (Objects.isNull(student)) throw new IllegalArgumentException("Could not add null student");
        students.add(student);
        grades.registerStudent(student);
    }

    public int getStudentCount() {
        return students.size();
    }

    public void removeStudent(Student student) {
        if (!students.remove(student))
            throw new IllegalArgumentException("This student wasn't on the list");
        grades.removeStudentGrades(student);
    }

    public void checkPresence(LocalDate now, List<Student> students) {
        diary.addEntry(now, students);
    }

    public List<DiaryEntry> getDiaryEntries() {
        return diary.getDiaryEntries();
    }

    public List<Student> getStudentList() {
        return Collections.unmodifiableList(new ArrayList<>(students));
    }

    public void addGradeToStudent(Student student, @NonNull Grade grade) {
        grades.add(student, grade);
    }

    public List<Grade> getGradesFor(Student student) {
        return grades.getGradesFor(student);
    }

    public void editGrade(Student student, Grade from, Grade to) {
        grades.editGrade(student, from, to);
    }

    public void deleteGrade(Student student, Grade grade) {
        grades.deleteGrade(student, grade);
    }

    public double getGradeAverageFor(Student student) {
        return grades.getGradeAverageFor(student);
    }

    public double getClassGradeAverage() {
        return grades.getClassGradeAverage();
    }

    public int countAbsenceForStudent(Student student) {
        return diary.countAbsenceForStudent(student);
    }
}