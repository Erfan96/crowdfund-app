package ir.digixo.crowdfundapp.mapper;

import ir.digixo.crowdfundapp.dto.ProjectDto;
import ir.digixo.crowdfundapp.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDto toDto(Project project);
    Project toEntity(ProjectDto projectDto);
    List<ProjectDto> toDtoList(List<Project> projects);
    void updateProjectFromDto(ProjectDto projectDto, @MappingTarget Project project);
}
