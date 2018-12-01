package studentList.services.file;

import studentList.services.LaboratoryClass;

public interface ToFileSaver {
    void saveToFile(String path, LaboratoryClass laboratoryClass);
}
