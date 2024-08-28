package _1.vietpq.job_hunter.service.file;

import _1.vietpq.job_hunter.config.file.FilePathConfig;
import _1.vietpq.job_hunter.exception.UploadException;
import _1.vietpq.job_hunter.exception.message.FileMessage;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    private final FilePathConfig filePathConfig;

    public FileServiceImpl(FilePathConfig filePathConfig) {
        this.filePathConfig = filePathConfig;
    }


    @Override
    public String handleUploadFile(MultipartFile file, String folder) throws URISyntaxException, IOException {
        // Check if file is null or empty
        if (file == null || file.isEmpty()) {
            throw new UploadException(FileMessage.FILE_REQUIRED);
        }

        // Check if the file has a valid extension
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean isValid = allowedExtensions.stream().anyMatch(item -> {
            assert fileName != null;
            return fileName.toLowerCase().endsWith(item);
        });

        if (!isValid) {
            throw new UploadException(FileMessage.FILE_INVALID + allowedExtensions.toString());
        }

        // Convert base directory from configuration to URI
        String baseURI = filePathConfig.getFileURL();
        URI folderURI = new URI(baseURI + folder);
        Path folderPath = Paths.get(folderURI);

        // Create the directory if it doesn't exist
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
                System.out.println(">>> CREATE NEW DIRECTORY SUCCESSFUL, PATH = " + folder);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("Failed to create directory: " + folder, e);
            }
        } else {
            System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
        }

        // Create the file with a unique name
        String finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        URI fileURI = new URI(folderURI + "/" + finalName);
        Path filePath = Paths.get(fileURI);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to upload file: " + finalName, e);
        }

        return finalName;
    }

    @Override
    public long getFileLength(String fileName, String folder) throws URISyntaxException {
        String baseURI = filePathConfig.getFileURL();
        URI uri = new URI(baseURI+ folder+"/"+fileName);
        Path path = Paths.get(uri);
        File tmpDir=  new File(path.toString());
        if(!tmpDir.exists()|| tmpDir.isDirectory()){
            return 0;
        }
        return tmpDir.length();
    }

    @Override
    public InputStreamResource getResource(String fileName, String folder) throws FileNotFoundException, URISyntaxException, FileNotFoundException {
        String baseURI = filePathConfig.getFileURL();
        URI uri = new URI(baseURI+ folder+"/"+fileName);
        Path path = Paths.get(uri);
        File file=  new File(path.toString());
        return new InputStreamResource(new FileInputStream(file));
    }

    @Override
    public Resource handleDownloadFile(String fileName, String folder) throws URISyntaxException, FileNotFoundException {
        if(fileName == null || folder == null){
            throw  new UploadException("Missing required params :(fileName or folder)");
        }
        //check file exist and not a directory
        long fileLength = getFileLength(fileName,folder);
        if(fileLength == 0){
            throw new UploadException("File with name = "+ fileName +" not found");
        }
        //download
        InputStreamResource resource = getResource(fileName,folder);
        return resource;
    }


}
