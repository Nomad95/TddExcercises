package services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import studentList.model.Grade;
import studentList.services.LaboratoryClass;
import studentList.model.Student;
import studentList.model.DiaryEntry;
import studentList.services.grades.CollegeGrades;
import studentList.services.grades.Grades;

import java.time.LocalDate;
import java.util.List;

import static factory.TestFactory.*;
import static java.time.LocalDate.*;
import static java.util.Arrays.*;
import static org.junit.Assert.*;


public class LaboratoryClassTest {

    private static final String MODIFIED_INDEX_NUMBER = "modifiedIndexNumber";
    private static final String MODIFIED_SURNAME = "modifiedSurname";
    private static final String MODIFIED_NAME = "modifiedName";

    @Test
    public void shouldAddStudentToList() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        Student student = createDefaultStudent();
        int studentCountBefore = laboratoryClass.getStudentCount();

        //when
        laboratoryClass.addStudent(student);

        //then
        int studentCountAfter = laboratoryClass.getStudentCount();
        assertEquals(studentCountBefore + 1, studentCountAfter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenAddingNullStudent() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();

        //when
        laboratoryClass.addStudent(null);
    }

    @Test
    public void shouldNotAddSameStudentToTheListTwice() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        Student student = createDefaultStudent();
        int studentCountBefore = laboratoryClass.getStudentCount();

        //when
        laboratoryClass.addStudent(student);
        laboratoryClass.addStudent(student);

        //then
        int studentCountAfter = laboratoryClass.getStudentCount();
        assertEquals(studentCountBefore + 1, studentCountAfter);
    }

    @Test
    public void shouldRemoveStudentFromList() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        Student student = createDefaultStudent();
        laboratoryClass.addStudent(student);
        int studentCountBefore = laboratoryClass.getStudentCount();

        //when
        laboratoryClass.removeStudent(student);

        //then
        int studentCountAfter = laboratoryClass.getStudentCount();
        assertEquals(studentCountBefore - 1, studentCountAfter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenRemovingNullStudentFromList() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();

        //when
        laboratoryClass.removeStudent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenRemovingStudentIsNotOnTheList() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        Student student = createDefaultStudent();

        //when
        laboratoryClass.removeStudent(student);
    }

    @Test
    public void shouldAddDiaryEntry() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        laboratoryClass.checkPresence(now(), asList(student));

        //then
        List<DiaryEntry> diaryEntries = laboratoryClass.getDiaryEntries();
        assertEquals(diaryEntries.size(), 1);
    }

    @Test
    public void shouldAddDiaryEntryWhenListIsEmpty() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        laboratoryClass.checkPresence(now(), ImmutableList.of());

        //then
        List<DiaryEntry> diaryEntries = laboratoryClass.getDiaryEntries();
        assertEquals(diaryEntries.size(), 1);
    }

    @Test
    public void shouldGetStudentList() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        List<Student> students = laboratoryClass.getStudentList();

        //then
        assertEquals(students.size(), 1);
        assertEquals(students.get(students.size() - 1), student);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotModifyStudentList() {
        //given
        final Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        List<Student> students = laboratoryClass.getStudentList();

        //then
        students.add(newStudent("null", "null", "null"));
    }

    @Test
    public void shouldChangePresence() throws ClassNotFoundException {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        laboratoryClass.checkPresence(now(), asList(student)); //student should be marked as present

        //when
        laboratoryClass.getDiaryEntries().get(laboratoryClass.getDiaryEntries().size() - 1).changePresenceFor(student, false);

        //then
        List<DiaryEntry> diaryEntries = laboratoryClass.getDiaryEntries();
        DiaryEntry.Presence studentPresence = diaryEntries.get(diaryEntries.size() - 1).getPresences().stream()
                .filter(p -> p.getStudent().equals(student))
                .findFirst()
                .orElseThrow(ClassNotFoundException::new);
        assertFalse(studentPresence.isPresent());
    }

    @Test
    public void shouldAddGradeEntryWhenAddingStudentToClass() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();

        //when
        laboratoryClass.addStudent(student);

        //then
        List<Grade> gradesFor = laboratoryClass.getGradesFor(student);
        Assert.assertEquals(gradesFor.size(), 0);
    }

    @Test
    public void shouldAddGradeToStudent() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        laboratoryClass.addGradeToStudent(student, Grade.THREE);

        //then
        List<Grade> gradesFor = laboratoryClass.getGradesFor(student);
        Assert.assertEquals(gradesFor.size(), 1);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrow() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        laboratoryClass.addGradeToStudent(student, null);
    }

    @Test
    public void shouldEditGrade() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        laboratoryClass.addGradeToStudent(student, Grade.THREE);

        //when
        laboratoryClass.editGrade(student, Grade.THREE, Grade.FOUR);

        //then
        List<Grade> gradesFor = laboratoryClass.getGradesFor(student);
        Assert.assertEquals(gradesFor.size(), 1);
        Assert.assertEquals(Grade.FOUR, gradesFor.get(gradesFor.size() - 1 ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotEditGradeList() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        laboratoryClass.addGradeToStudent(student, Grade.THREE);

        //when
        laboratoryClass.getGradesFor(student).clear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenReplacingNonExistingGrade() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        laboratoryClass.addGradeToStudent(student, Grade.THREE);

        //when
        laboratoryClass.editGrade(student, Grade.TWO, Grade.FOUR);
    }

    @Test
    public void shouldDeleteGrade() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        laboratoryClass.addGradeToStudent(student, Grade.THREE);

        //when
        laboratoryClass.deleteGrade(student, Grade.THREE);

        //then
        List<Grade> gradesFor = laboratoryClass.getGradesFor(student);
        Assert.assertTrue(gradesFor.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenDeletingNonExistingGrade() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        laboratoryClass.addGradeToStudent(student, Grade.THREE);

        //when
        laboratoryClass.deleteGrade(student, Grade.TWO);
    }

    @Test
    public void shouldGetAverageGradeValueForOneStudent() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        laboratoryClass.addGradeToStudent(student, Grade.THREE);
        laboratoryClass.addGradeToStudent(student, Grade.THREE_PLUS);
        laboratoryClass.addGradeToStudent(student, Grade.FOUR);
        laboratoryClass.addGradeToStudent(student, Grade.FOUR);

        //when
        double result = laboratoryClass.getGradeAverageFor(student);

        //then
        Assert.assertEquals(3.625, result, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenGetAverageForStudentWithoutGrades() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        laboratoryClass.getGradeAverageFor(student);
    }

    @Test
    public void shouldGetAverageGradeValueForClass() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        Student student1 = createDefaultStudent();
        laboratoryClass.addStudent(student1);
        laboratoryClass.addGradeToStudent(student1, Grade.THREE);
        laboratoryClass.addGradeToStudent(student1, Grade.THREE_PLUS);
        laboratoryClass.addGradeToStudent(student1, Grade.FOUR);
        laboratoryClass.addGradeToStudent(student1, Grade.FOUR);

        Student student2 = newStudent(MODIFIED_NAME, MODIFIED_SURNAME, MODIFIED_INDEX_NUMBER);
        laboratoryClass.addStudent(student2);
        laboratoryClass.addGradeToStudent(student2, Grade.TWO);
        laboratoryClass.addGradeToStudent(student2, Grade.THREE);
        laboratoryClass.addGradeToStudent(student2, Grade.FIVE);
        laboratoryClass.addGradeToStudent(student2, Grade.FIVE);

        //when
        double result = laboratoryClass.getClassGradeAverage();

        //then
        Assert.assertEquals(3.688d, result, 0.001);
    }

    @Test
    public void shouldCountAbsenceForStudent() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();

        Student student1 = createDefaultStudent();
        laboratoryClass.addStudent(student1);

        Student student2 = newStudent(MODIFIED_NAME, MODIFIED_SURNAME, MODIFIED_INDEX_NUMBER);
        laboratoryClass.addStudent(student2);

        laboratoryClass.checkPresence(now(), Lists.newArrayList(student1));
        laboratoryClass.checkPresence(now(), Lists.newArrayList(student1));
        laboratoryClass.checkPresence(now(), Lists.newArrayList(student2));

        //when
        int absenceCount = laboratoryClass.countAbsenceForStudent(student2);

        //then
        Assert.assertEquals(2, absenceCount);
    }

    //when removed should be removed from grades and diary (or not?)

    @Test
    public void shouldNotChangeRegistryWhenRemovingStudent() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();

        Student student1 = createDefaultStudent();
        laboratoryClass.addStudent(student1);

        Student student2 = newStudent(MODIFIED_NAME, MODIFIED_SURNAME, MODIFIED_INDEX_NUMBER);
        laboratoryClass.addStudent(student2);

        laboratoryClass.checkPresence(now(), Lists.newArrayList(student1));
        laboratoryClass.checkPresence(now(), Lists.newArrayList(student1));
        laboratoryClass.checkPresence(now(), Lists.newArrayList(student2));

        //when
        laboratoryClass.removeStudent(student2);
        laboratoryClass.removeStudent(student1);

        //then
        int absenceCount = laboratoryClass.countAbsenceForStudent(student2);
        Assert.assertEquals(2, absenceCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRemoveGradesWhenRemovingStudentFromClass() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        laboratoryClass.addGradeToStudent(student, Grade.THREE);
        laboratoryClass.addGradeToStudent(student, Grade.THREE_PLUS);
        laboratoryClass.addGradeToStudent(student, Grade.FOUR);
        laboratoryClass.addGradeToStudent(student, Grade.FOUR);

        //when
        laboratoryClass.removeStudent(student);

        //then
        laboratoryClass.getGradeAverageFor(student);
    }
}
