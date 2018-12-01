package student_list;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import studentList.model.Grade;
import studentList.model.Student;
import studentList.services.LaboratoryClass;
import studentList.services.file.DiaryToFileSaver;
import studentList.services.file.FromFileReader;
import studentList.services.file.ToFileSaver;

import static factory.TestFactory.createDefaultStudent;
import static factory.TestFactory.newStudent;
import static java.time.LocalDate.now;

@RunWith(MockitoJUnitRunner.class)
public class FileTests {

    @Mock
    ToFileSaver saver;

    @Mock
    FromFileReader reader;

    @Test
    public void shouldSave() {
        Mockito.doNothing().when(saver).saveToFile(Mockito.anyString(), Mockito.any());

        LaboratoryClass laboratoryClass = new LaboratoryClass();

        Student student1 = createDefaultStudent();
        laboratoryClass.addStudent(student1);

        Student student2 = newStudent("abc", "def", "ghj");
        laboratoryClass.addStudent(student2);

        laboratoryClass.addGradeToStudent(student1, Grade.THREE);
        laboratoryClass.addGradeToStudent(student1, Grade.TWO);
        laboratoryClass.addGradeToStudent(student1, Grade.FOUR_PLUS);
        laboratoryClass.addGradeToStudent(student2, Grade.FOUR);
        laboratoryClass.addGradeToStudent(student2, Grade.FIVE);

        laboratoryClass.checkPresence(now(), Lists.newArrayList(student1));
        laboratoryClass.checkPresence(now(), Lists.newArrayList(student1));
        laboratoryClass.checkPresence(now(), Lists.newArrayList(student2));


        saver.saveToFile("asdasd", laboratoryClass);
    }

    @Test
    public void shouldRead() {
        Mockito.doReturn(new LaboratoryClass()).when(reader).readFromFile(Mockito.anyString());

        LaboratoryClass asdasd = reader.readFromFile("asdasd");

        Assert.assertNotNull(asdasd);
    }
}
