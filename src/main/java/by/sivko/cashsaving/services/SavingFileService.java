package by.sivko.cashsaving.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SavingFileService {
    String saveFile(MultipartFile file) throws IOException;
}
