package studentList.services.file;

import studentList.services.LaboratoryClass;

public interface FromFileReader {

    LaboratoryClass readFromFile(String path);
}
