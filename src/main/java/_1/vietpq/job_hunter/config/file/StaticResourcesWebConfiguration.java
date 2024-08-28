package _1.vietpq.job_hunter.config.file;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {
    private final FilePathConfig filePathConfig;

    public StaticResourcesWebConfiguration(FilePathConfig filePathConfig) {
        this.filePathConfig = filePathConfig;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseURI = filePathConfig.getFileURL();
        registry.addResourceHandler("/storage/**")
                .addResourceLocations(baseURI);
    }

}
