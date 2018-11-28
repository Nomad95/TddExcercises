package studentList.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Let's complicate a bit and make student immutable
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public final class Student {

    private final String name;
    private final String surname;
    private final String indexNumber;

    public final Student changeName(String newName) {
        return new Student(newName, surname, indexNumber);
    }

    public Student changeSurname(String newSurname) {
        return new Student(name, newSurname, indexNumber);
    }

    public Student changeIndexNumber(String newIndexNumber) {
        return new Student(name, surname, newIndexNumber);
    }
}
