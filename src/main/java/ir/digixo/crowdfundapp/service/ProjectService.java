package ir.digixo.crowdfundapp.service;

import ir.digixo.crowdfundapp.dto.ProjectDto;
import ir.digixo.crowdfundapp.entity.Project;

import java.util.List;

public interface ProjectService {
    ProjectDto saveProject(ProjectDto projectDto);
    ProjectDto getProjectById(Long id);
    Project findById(Long id);
    List<ProjectDto> getAllProjects();
    ProjectDto updateProject(ProjectDto projectDto);
    void deleteById(Long id);
}
