package _1.vietpq.job_hunter.service.skill;

import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.exception.DuplicatedException;
import _1.vietpq.job_hunter.exception.NotFoundException;
import _1.vietpq.job_hunter.exception.message.SkillMessage;
import _1.vietpq.job_hunter.repository.SkillRepository;
import _1.vietpq.job_hunter.util.validator.SkillValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService{
    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill handleCreateSkill(Skill skill) {
        SkillValidator.notNullName(skill.getName());
        if(isNameExist(skill.getName())){
            throw  new DuplicatedException(SkillMessage.SKILL_ALREADY_EXIST);
        }
        return this.skillRepository.save(skill);
    }

    @Override
    public Skill handleUpdateSkill(Skill skill) {
        Optional<Skill> skillOptional = skillRepository.findById(skill.getId());
        if(skillOptional.isEmpty()) {
            throw  new NotFoundException(SkillMessage.NOT_FOUND);
        }
        Skill existingSkill = skillOptional.get();
        if(isNameExist(skill.getName())){
            throw  new DuplicatedException(SkillMessage.SKILL_ALREADY_EXIST);
        }
        existingSkill.setName(skill.getName());
        return this.skillRepository.save(existingSkill);
    }

    @Override
    public Skill fetchSkillById(long id) {
       Optional<Skill> skillOptional = skillRepository.findById(id);
       if(skillOptional.isEmpty()) {
           throw  new NotFoundException(SkillMessage.NOT_FOUND);
       }
       return skillOptional.get();
    }

    @Override
    public ResultPaginationDTO fetchAllSkills(Specification<Skill> specification, Pageable pageable) {
        Page<Skill> skillPage = skillRepository.findAll(specification,pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        ResultPaginationDTO.Meta  mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber()+1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(skillPage.getTotalPages());
        mt.setTotal(skillPage.getTotalElements());
        res.setMeta(mt);
        res.setResult(skillPage.getContent());
        return res;

    }
    @Override
    public void handleDeleteSkill(long id) {
        Optional<Skill> skillOptional = skillRepository.findById(id);
        if(skillOptional.isEmpty()) {
            throw  new NotFoundException(SkillMessage.NOT_FOUND);
        }
        Skill currentSkill = skillOptional.get();
        currentSkill.getJobs().forEach(job->job.getSkills().remove(currentSkill));
        this.skillRepository.deleteById(id);
    }

    @Override
    public boolean isNameExist(String name) {
        return this.skillRepository.existsByName(name);
    }
}
