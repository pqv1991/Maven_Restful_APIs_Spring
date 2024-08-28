package _1.vietpq.job_hunter.service.file;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface FileService {
    String handleUploadFile(MultipartFile file,String folder) throws URISyntaxException, IOException;
    long getFileLength(String fileName, String folder) throws URISyntaxException;
    InputStreamResource getResource(String fileName, String folder) throws  URISyntaxException, FileNotFoundException;

    Resource handleDownloadFile(String fileName, String folder) throws URISyntaxException, FileNotFoundException;

}
