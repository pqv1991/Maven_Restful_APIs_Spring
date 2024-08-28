package _1.vietpq.job_hunter.config.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
@Configuration
public class FilePathConfig {
    @Value("${upload-file.path}")
    private String filePath;
    public  String getFileURL(){
        Path path = Paths.get(filePath);
        try {
            URI uri = path.toUri();
            return uri.toString();
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}
