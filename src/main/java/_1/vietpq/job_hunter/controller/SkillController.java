package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.service.skill.SkillService;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }
    @PostMapping("/skills")
    @ApiMessage("FETCH CREATE SKILL")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleCreateSkill(skill));
    }

    @PutMapping("/skills")
    @ApiMessage("FETCH UPDATE SKILL")
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill){
        return ResponseEntity.ok().body(this.skillService.handleUpdateSkill(skill));
    }

    @GetMapping("/skills")
    @ApiMessage("FETCH GET ALL SKILLS")
    public ResponseEntity<ResultPaginationDTO> getAllSkills(@Filter  Specification<Skill> specification, Pageable pageable){
        return ResponseEntity.ok().body(this.skillService.fetchAllSkills(specification,pageable));
    }

    @GetMapping("/skills/{id}")
    @ApiMessage("FETCH GET SKILL BY ID")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") long id){
        return ResponseEntity.ok().body(this.skillService.fetchSkillById(id));
    }

    @DeleteMapping("/skills/{id}")
    @ApiMessage("DELETE A SKILL")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        this.skillService.handleDeleteSkill(id);
        return ResponseEntity.ok().body(null);

    }
}
