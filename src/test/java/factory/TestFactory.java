package factory;

import studentList.services.LaboratoryClass;
import studentList.model.Student;

import java.util.stream.IntStream;

public class TestFactory {

    private static final String DEFAULT_INDEX_NUMBER = "defaultIndexNumber";
    private static final String DEFAULT_SURNAME = "defaultSurname";
    private static final String DEFAULT_NAME = "defaultName";

    public static Student createDefaultStudent() {
        return new Student(DEFAULT_NAME, DEFAULT_SURNAME, DEFAULT_INDEX_NUMBER);
    }

    public static Student newStudent(String name, String surname, String indexNumber) {
        return new Student(name, surname, indexNumber);
    }

    public static void addNStudentsToClass(LaboratoryClass laboratoryClass, int n) {
        IntStream.range(1, n + 1).forEach(integer -> laboratoryClass.addStudent(new Student(DEFAULT_NAME + integer, DEFAULT_SURNAME + integer, DEFAULT_INDEX_NUMBER + integer)));
    }

}
