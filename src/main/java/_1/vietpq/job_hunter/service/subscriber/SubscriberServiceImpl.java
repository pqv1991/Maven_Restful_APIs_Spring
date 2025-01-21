package _1.vietpq.job_hunter.service.subscriber;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.domain.Subscriber;
import _1.vietpq.job_hunter.dto.convertToDTO.ConvertToRestEmailDTO;
import _1.vietpq.job_hunter.dto.email.RestEmailJobDTO;
import _1.vietpq.job_hunter.exception.DuplicatedException;
import _1.vietpq.job_hunter.exception.NotFoundException;
import _1.vietpq.job_hunter.exception.message.SubscriberMessage;
import _1.vietpq.job_hunter.repository.JobRepository;
import _1.vietpq.job_hunter.repository.SkillRepository;
import _1.vietpq.job_hunter.repository.SubscriberRepository;
import _1.vietpq.job_hunter.service.email.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;
    private final JobRepository jobRepository;
    private final EmailService emailService;


    public SubscriberServiceImpl(SubscriberRepository subscriberRepository, SkillRepository skillRepository, JobRepository jobRepository, EmailService emailService) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
        this.emailService = emailService;
    }

    @Override
    public boolean isEmail(String email) {
        return subscriberRepository.existsByEmail(email);
    }

    @Override
    public Subscriber handleCreateSubscriber(Subscriber subscriber) {
        if(this.isEmail(subscriber.getEmail())){
            throw  new DuplicatedException(SubscriberMessage.SUBSCRIBER_ALREADY_EXIST);
        }
        if(subscriber.getSkills() != null){
            List<Long> listIdSkill = subscriber.getSkills().stream().map(x->x.getId()).toList();
            List<Skill> skillList = skillRepository.findByIdIn(listIdSkill);
            subscriber.setSkills(skillList);
        }
        return subscriberRepository.save(subscriber);
    }

    @Override
    public Subscriber handleUpdateSubscriber(Subscriber subscriberDb, Subscriber subscriber) {
        //check skill
        if(subscriber.getSkills() !=null){
            List<Long> listIdSkill = subscriber.getSkills().stream().map(x->x.getId()).toList();
            List<Skill> skillList = skillRepository.findByIdIn(listIdSkill);
            subscriberDb.setSkills(skillList);
        }
        return subscriberRepository.save(subscriberDb);
    }

    @Override
    public Subscriber fetchSubscriberById(long id) {
        Optional<Subscriber> subscriberOptional = this.subscriberRepository.findById(id);
        if(subscriberOptional.isEmpty()){
            throw  new NotFoundException(SubscriberMessage.NOT_FOUND);
        }
        return subscriberOptional.get();
    }

    @Override
    public Subscriber getSubscriberByEmail(String email) {
        return this.subscriberRepository.findByEmail(email);
    }

    @Override
    public void sendSubscriberEmailJobs() {
        List<Subscriber> listSubs = this.subscriberRepository.findAll();
        if (listSubs != null && listSubs.size() > 0) {
            for (Subscriber sub : listSubs) {
                List<Skill> listSkills = sub.getSkills();
                if (listSkills != null && listSkills.size() > 0) {
                    List<Job> listJobs = this.jobRepository.findBySkillsIn(listSkills);
                    if (listJobs != null && listJobs.size() > 0) {
                        List<RestEmailJobDTO> arr = listJobs.stream().map(job -> ConvertToRestEmailDTO.convertResEmailJobDTO(job)).collect(Collectors.toList());
                        this.emailService.sendEmailFromTemplateSync(
                                sub.getEmail(),
                                "Cơ hội việc làm hot đang chờ đón bạn, khám phá ngay",
                                "job",
                                sub.getName(), arr);
                    }
                }
            }
        }
    }
}
