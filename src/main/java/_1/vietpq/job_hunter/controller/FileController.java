package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.dto.file.RestUploadFileDTO;
import _1.vietpq.job_hunter.service.file.FileService;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;
import _1.vietpq.job_hunter.config.file.FilePathConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;
    //private final FilePathConfig filePathConfig;

    public FileController(FileService fileService, FilePathConfig filePathConfig) throws URISyntaxException {
        this.fileService = fileService;
       // this.filePathConfig = filePathConfig;
    }

    @PostMapping("/files")
    @ApiMessage("FETCH UPLOAD FILE SINGLE")
    public ResponseEntity<RestUploadFileDTO> uploadFile(@RequestParam(name="file", required = false) MultipartFile file, @RequestParam("folder") String folder) throws URISyntaxException, IOException, URISyntaxException, IOException {
        String uploadFile = fileService.handleUploadFile(file,folder);
        RestUploadFileDTO res = new RestUploadFileDTO(uploadFile, Instant.now());
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/files")
    @ApiMessage("Download a file")
    public ResponseEntity<Resource> downloadFile(@RequestParam(name = "fileName", required = false) String fileName,
                                                 @RequestParam(name = "folder", required = false) String folder ) throws  URISyntaxException, FileNotFoundException, FileNotFoundException {

        //download
        Resource resource = fileService.handleDownloadFile(fileName,folder);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\"" + fileName+ "\"")
                .contentLength(fileService.getFileLength(fileName,folder)).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

}
