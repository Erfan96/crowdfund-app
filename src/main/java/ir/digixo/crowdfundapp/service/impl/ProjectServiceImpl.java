package ir.digixo.crowdfundapp.service.impl;

import ir.digixo.crowdfundapp.dto.ProjectDto;
import ir.digixo.crowdfundapp.entity.Project;
import ir.digixo.crowdfundapp.mapper.ProjectMapper;
import ir.digixo.crowdfundapp.repository.ProjectRepository;
import ir.digixo.crowdfundapp.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }


    @Override
    public ProjectDto saveProject(ProjectDto projectDto) {
        return projectMapper.toDto(
                projectRepository.save(
                        projectMapper.toEntity(projectDto)
                )
        );
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        return projectMapper.toDto(findById(id));
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("not found project"));
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectMapper.toDtoList(projectRepository.findAll());
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectDto) {
        Project project = findById(projectDto.getId());
        projectMapper.updateProjectFromDto(projectDto, project);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    @Override
    public void deleteById(Long id) {
        Project project = findById(id);
        projectRepository.delete(project);
    }
}
