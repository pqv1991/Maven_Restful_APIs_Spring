package _1.vietpq.job_hunter.service.skill;

import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.exception.InValidException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SkillService {

    Skill handleCreateSkill(Skill skill);
    Skill handleUpdateSkill(Skill skill);
    Skill fetchSkillById (long id);
    ResultPaginationDTO fetchAllSkills(Specification<Skill> specification, Pageable pageable);
    void handleDeleteSkill(long  id);
    boolean isNameExist(String name);
}
