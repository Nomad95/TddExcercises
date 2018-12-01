package student_list;

import org.junit.Assert;
import org.junit.Test;
import studentList.services.LaboratoryClass;
import studentList.model.Student;
import studentList.services.diary.Diary;
import studentList.services.diary.CollegeDiary;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static factory.TestFactory.*;
import static java.time.LocalDate.*;
import static java.util.Arrays.*;

public class DiaryTest {

    @Test
    public void shouldAddEntryToDiary() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        Diary simpleDiary = new CollegeDiary(laboratoryClass);
        Student student = createDefaultStudent();

        //when
        simpleDiary.addEntry(now(), asList(student));

        //then
        Assert.assertEquals(simpleDiary.getDiaryEntries().size(), 1);
    }

    @Test
    public void shouldGetAllStudents() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        Diary simpleDiary = new CollegeDiary(laboratoryClass);

        //when
        List<Student> students = simpleDiary.getStudents();

        //then
        Assert.assertEquals(students.get(students.size() - 1), student);
    }

    @Test
    public void shouldIgnoreStudentThatIsNotOnTheClassList() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);
        Diary simpleDiary = new CollegeDiary(laboratoryClass);
        Student notInClassStudent = createDefaultStudent();

        //when
        simpleDiary.addEntry(now(), asList(student, notInClassStudent));

        //then
        Assert.assertEquals(simpleDiary.getDiaryEntries().size(), 1);
    }

    @Test
    public void shouldThrowWhenAddingDiaryEntryWithNullDate() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        new CollegeDiary(laboratoryClass).addEntry(null, asList(student));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhileAddingNullListToDiaryEntry() {
        //given
        Student student = createDefaultStudent();
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        laboratoryClass.addStudent(student);

        //when
        new CollegeDiary(laboratoryClass).addEntry(now(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAddMoreThan15DiaryEntries() {
        //given
        LaboratoryClass laboratoryClass = new LaboratoryClass();
        addNStudentsToClass(laboratoryClass, 15);

        //when
        IntStream.range(1, 17).forEach(i -> laboratoryClass.checkPresence(now(), Collections.emptyList()));
    }
}
