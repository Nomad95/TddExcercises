package services;

import org.junit.Assert;
import org.junit.Test;
import studentList.services.LaboratoryClass;
import studentList.model.Student;
import studentList.model.DiaryEntry;
import studentList.services.diary.CollegeDiary;

import java.util.List;

import static java.time.LocalDate.*;
import static java.util.Arrays.*;

public class DiaryEntryTest {

    @Test
    public void shouldMarkPresenceWhenStudentIsPresent() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        Student student1 = new Student("First", "First", "1");
        Student student2 = new Student("Second", "Second", "2");
        Student student3 = new Student("Third", "Third", "3");
        Student student4 = new Student("Fourth", "Fourth", "4");

        addStudentsToClass(laboratoryClass, student1, student2, student3, student4);
        List<Student> presentStudents = asList(student2, student4);

        CollegeDiary collegeDiary = new CollegeDiary(laboratoryClass);

        //when
        DiaryEntry diaryEntry = new DiaryEntry(now(), collegeDiary, presentStudents);

        //then
        List<DiaryEntry.Presence> presences = diaryEntry.getPresences();
        AssertThatPresenceWasCheckedProperly(student1, student2, student3, student4, presences);
    }

    private void addStudentsToClass(LaboratoryClass laboratoryClass, Student student1, Student student2,
            Student student3, Student student4) {
        laboratoryClass.addStudent(student1);
        laboratoryClass.addStudent(student2);
        laboratoryClass.addStudent(student3);
        laboratoryClass.addStudent(student4);
    }


    private void AssertThatPresenceWasCheckedProperly(Student student1, Student student2, Student student3,
            Student student4, List<DiaryEntry.Presence> presences) {
        Assert.assertTrue(presences.contains(new DiaryEntry.Presence(student1, false)));
        Assert.assertTrue(presences.contains(new DiaryEntry.Presence(student2, true)));
        Assert.assertTrue(presences.contains(new DiaryEntry.Presence(student3, false)));
        Assert.assertTrue(presences.contains(new DiaryEntry.Presence(student4, true)));
    }

    @Test
    public void shouldEditPresence() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        Student student1 = new Student("First", "First", "1");
        Student student2 = new Student("Second", "Second", "2");
        Student student3 = new Student("Third", "Third", "3");
        Student student4 = new Student("Fourth", "Fourth", "4");

        addStudentsToClass(laboratoryClass, student1, student2, student3, student4);
        List<Student> presentStudents = asList(student2, student4);

        CollegeDiary collegeDiary = new CollegeDiary(laboratoryClass);
        DiaryEntry diaryEntry = new DiaryEntry(now(), collegeDiary, presentStudents);

        //when
        diaryEntry.changePresenceFor(student3, true);

        //then
        List<DiaryEntry.Presence> presences = diaryEntry.getPresences();
        Assert.assertTrue(presences.contains(new DiaryEntry.Presence(student3, true)));
    }


}
