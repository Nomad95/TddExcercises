package student_list;

import org.junit.Assert;
import org.junit.Test;
import studentList.model.Student;

import static factory.TestFactory.*;

public class StudentEditorTest {

    private static final String MODIFIED_INDEX_NUMBER = "modifiedIndexNumber";
    private static final String MODIFIED_SURNAME = "modifiedSurname";
    private static final String MODIFIED_NAME = "modifiedName";

    @Test
    public void shouldChangeStudentName() {
        Student student = createDefaultStudent();

        student = student.changeName(MODIFIED_NAME);

        Assert.assertEquals(MODIFIED_NAME, student.getName());
    }

    @Test
    public void shouldChangeStudentSurname() {
        Student student = createDefaultStudent();

        student = student.changeSurname(MODIFIED_SURNAME);

        Assert.assertEquals(MODIFIED_SURNAME, student.getSurname());
    }

    @Test
    public void shouldChangeStudentIndexNumber() {
        Student student = createDefaultStudent();

        student = student.changeIndexNumber(MODIFIED_INDEX_NUMBER);

        Assert.assertEquals(MODIFIED_INDEX_NUMBER, student.getIndexNumber());
    }
}
