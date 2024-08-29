package _1.vietpq.job_hunter.service.resume;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.domain.Resume;
import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.dto.convertToDTO.ConvertToResumeDTO;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.dto.resume.ResResumeDTO;
import _1.vietpq.job_hunter.exception.InValidException;
import _1.vietpq.job_hunter.exception.NotNullException;
import _1.vietpq.job_hunter.exception.message.ResumeMessage;
import _1.vietpq.job_hunter.repository.JobRepository;
import _1.vietpq.job_hunter.repository.ResumeRepository;
import _1.vietpq.job_hunter.repository.UserRepository;
import _1.vietpq.job_hunter.util.SecurityUtil;
import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecification;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final FilterBuilder filterBuilder;
    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;


    public ResumeServiceImpl(ResumeRepository resumeRepository, JobRepository jobRepository, UserRepository userRepository, FilterBuilder filterBuilder, FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter) {
        this.resumeRepository = resumeRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.filterBuilder = filterBuilder;
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
    }

    @Override
    public boolean checkResumeExistsByUserAndJob(Resume resume) {
        if(resume.getUser() == null) return  false;
        Optional<User> userOptional = userRepository.findById(resume.getUser().getId());
        if (userOptional.isEmpty()) return  false;
        if (resume.getJob() == null) return  false;
        Optional<Job> jobOptional = jobRepository.findById(resume.getJob().getId());
        if (jobOptional.isEmpty()) return  false;
        return true;
    }

    @Override
    public Optional<Resume> fetchResumeById(long id) {
        Optional<Resume> resumeOptional = resumeRepository.findById(id);
        if(resumeOptional.isEmpty()){
            throw new NotNullException(ResumeMessage.NOT_FOUND);
        }
        return resumeOptional;
    }

    @Override
    public Resume handleCreateResume(Resume resume) throws InValidException {
       if(!checkResumeExistsByUserAndJob(resume)){
           throw new InValidException(ResumeMessage.RESUME_USER_JOB_NOT_EXIST);
       }
       return this.resumeRepository.save(resume);
    }

    @Override
    public Resume handleUpdateResume(Resume resume) {
       Optional<Resume> resumeOptional = fetchResumeById(resume.getId());
       Resume resumeUpdate = resumeOptional.get();
       resumeUpdate.setStatus(resume.getStatus());
       return this.resumeRepository.save(resumeUpdate);
    }

    @Override
    public ResultPaginationDTO fetchAllResumes(Specification<Resume> spec, Pageable pageable) {
        List<Long> arrJobIds = null;
        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        User currentUser = userRepository.findByEmail(email);
        if( currentUser != null){
            Company userCompany = currentUser.getCompany();
            if (userCompany !=null) {
                List<Job> companyJobs = userCompany.getJobs();
                if(companyJobs != null && companyJobs.size() > 0){
                    arrJobIds = companyJobs.stream().map(x->x.getId()).collect(Collectors.toList());
                }
            }
        }
        Specification<Resume> jobJnSpec = filterSpecificationConverter.convert(filterBuilder.field("job").in(filterBuilder.input(arrJobIds)).get());
        Specification<Resume> finalSpec = jobJnSpec.and(spec);
        Page<Resume> resumePage = resumeRepository.findAll(finalSpec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber()+1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(resumePage.getTotalPages());
        mt.setTotal(resumePage.getTotalElements());
        List<ResResumeDTO> resumeDTOList = resumePage.getContent().stream().map(item-> ConvertToResumeDTO.convertToResumeDTO(item)).collect(Collectors.toList());
        rs.setMeta(mt);
        rs.setResult(resumeDTOList);
        return rs;

    }

    @Override
    public ResultPaginationDTO fetchAllResumesByUser(Pageable pageable) {
        String email = SecurityUtil.getCurrentUserLogin().isPresent()? SecurityUtil.getCurrentUserLogin().get():"";
        FilterNode filterNode = filterParser.parse("email='"+email+"'");
        FilterSpecification<Resume> specificationConverter = filterSpecificationConverter.convert(filterNode);
        Page<Resume> resumePage = resumeRepository.findAll(specificationConverter,pageable);

        ResultPaginationDTO res = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(resumePage.getTotalPages());
        mt.setTotal(resumePage.getTotalElements());
        res.setMeta(mt);
        List<ResResumeDTO> resumeDTOList = resumePage.getContent().stream().map(item-> ConvertToResumeDTO.convertToResumeDTO(item)).collect(Collectors.toList());
        res.setResult(resumeDTOList);

        return res;

}

    @Override
    public void handleDeleteResume(long id) {
        Optional<Resume> resumeOptional = fetchResumeById(id);
        this.resumeRepository.deleteById(id);

    }
}
