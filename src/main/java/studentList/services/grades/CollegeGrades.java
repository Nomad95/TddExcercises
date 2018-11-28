package studentList.services.grades;

import lombok.NonNull;
import studentList.model.Grade;
import studentList.services.LaboratoryClass;
import studentList.model.Student;

import java.util.*;

public class CollegeGrades implements Grades {

    private final Map<Student, List<Grade>> gradesOfStudents;
    private final LaboratoryClass laboratoryClass;

    public CollegeGrades(LaboratoryClass laboratoryClass) {
        this.gradesOfStudents = new HashMap<>(15);
        this.laboratoryClass = laboratoryClass;
    }

    @Override
    public void add(Student student, Grade grade) {
        List<Grade> grades = gradesOfStudents.get(student);
        if (Objects.isNull(grades)) throw new IllegalStateException("Can't put value to non existing student");

        grades.add(grade);
    }

    @Override
    public List<Grade> getGradesFor(Student student) {
        return Collections.unmodifiableList(gradesOfStudents.getOrDefault(student, Collections.emptyList()));
    }

    @Override
    public void registerStudent(Student student) {
        gradesOfStudents.putIfAbsent(student, new ArrayList<>());
    }

    @Override
    public void editGrade(@NonNull Student student, @NonNull Grade from, @NonNull Grade to) {
        List<Grade> grades = Optional.ofNullable(gradesOfStudents.get(student))
                .orElseThrow(() -> new IllegalArgumentException("Student doesn't exists in this list"));
        ListIterator<Grade> gradeListIterator = grades.listIterator();
        while (gradeListIterator.hasNext()) {
            if(gradeListIterator.next().equals(from)) {
                gradeListIterator.set(to);
                return;
            }
        }

        throw new IllegalArgumentException("Student doesn't have this grade: " + from);
    }

    @Override
    public void deleteGrade(@NonNull Student student, @NonNull Grade grade) {
        List<Grade> grades = Optional.ofNullable(gradesOfStudents.get(student))
                .orElseThrow(() -> new IllegalArgumentException("Student doesn't exists in this list"));
        ListIterator<Grade> gradeListIterator = grades.listIterator();
        while (gradeListIterator.hasNext()) {
            if(gradeListIterator.next().equals(grade)) {
                gradeListIterator.remove();
                return;
            }
        }

        throw new IllegalArgumentException("Student doesn't have this grade: " + grade);
    }

    @Override
    public double getGradeAverageFor(@NonNull Student student) {
        List<Grade> grades = Optional.ofNullable(gradesOfStudents.get(student))
                .orElseThrow(() -> new IllegalArgumentException("Student doesn't exists in this list"));
        return grades.stream().mapToDouble(Grade::getValue).average()
                .orElseThrow(() -> new IllegalArgumentException("Student doesn't have any grades!"));
    }

    @Override
    public double getClassGradeAverage() {
        if (gradesOfStudents.isEmpty()) throw new IllegalStateException("Class is empty");
        return gradesOfStudents.values().stream().flatMap(Collection::stream).mapToDouble(Grade::getValue).average()
                .orElseThrow(() -> new IllegalArgumentException("There aren't any grades"));
    }

    @Override
    public void removeStudentGrades(Student student) {
        gradesOfStudents.remove(student);
    }
}
