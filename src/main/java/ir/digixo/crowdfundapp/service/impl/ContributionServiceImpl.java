package ir.digixo.crowdfundapp.service.impl;

import ir.digixo.crowdfundapp.dto.ContributionDto;
import ir.digixo.crowdfundapp.dto.ProjectDto;
import ir.digixo.crowdfundapp.dto.UserDto;
import ir.digixo.crowdfundapp.entity.Contribution;
import ir.digixo.crowdfundapp.entity.Project;
import ir.digixo.crowdfundapp.entity.User;
import ir.digixo.crowdfundapp.entity.enumeration.ContributionStatus;
import ir.digixo.crowdfundapp.mapper.ContributionMapper;
import ir.digixo.crowdfundapp.repository.ContributionRepository;
import ir.digixo.crowdfundapp.repository.ProjectRepository;
import ir.digixo.crowdfundapp.repository.UserRepository;
import ir.digixo.crowdfundapp.service.ContributionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContributionServiceImpl implements ContributionService {
    private final ContributionRepository contributionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ContributionMapper contributionMapper;

    public ContributionServiceImpl(ContributionRepository contributionRepository, ProjectRepository projectRepository, UserRepository userRepository, ContributionMapper contributionMapper) {
        this.contributionRepository = contributionRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.contributionMapper = contributionMapper;
    }

    @Override
    public BigDecimal getProjectFundingProgress(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        BigDecimal targetAmount = project.getTargetAmount();
        if (targetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalRaised = contributionRepository.getTotalAmountForProject(projectId);
        return (totalRaised.divide(targetAmount, 4, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100));
    }

    @Override
    public List<ContributionDto> getRecentContributionsByProjectId(Long projectId) {
        return contributionRepository.findTop10ByProjectIdOrderByContributionDateDesc(projectId).stream()
                .map(contributionMapper::toDto)
                .toList();
    }

    @Override
    public BigDecimal getTotalAmountContributionByUserId(Long userId) {
        return contributionRepository.getTotalAmountForUser(userId);
    }

    @Override
    public BigDecimal getTotalAmountContributionByUserIdPerProject(Long projectId, Long userId) {
        return contributionRepository.getTotalAmountForUserPerProject(projectId, userId);
    }

    @Override
    public BigDecimal getTotalAmountContributedToProject(Long projectId) {
        return contributionRepository.getTotalAmountForProject(projectId);
    }

    @Override
    public List<ContributionDto> getContributionsByProjectId(Long projectId) {
        return contributionRepository.findByProjectId(projectId).stream()
                .map(contributionMapper::toDto)
                .toList();
    }

    @Override
    public void deleteContribution(Long id) {
        contributionRepository.deleteById(id);
    }

    @Override
    public List<ContributionDto> getContributionsByUserId(Long userId) {
        return contributionRepository.findByUserId(userId).stream()
                .map(contributionMapper::toDto)
                .toList();
    }

    @Override
    public ContributionDto updateContribution(ContributionDto contributionDto) {
        Contribution contribution = contributionRepository.findById(
                contributionDto.getId()).orElseThrow(() -> new EntityNotFoundException("Contribution not found")
        );
        contribution.setAmount(contributionDto.getAmount());
        contribution.setStatus(contributionDto.getStatus());
        contribution.setContributionDate(LocalDateTime.now());
        return contributionMapper.toDto(contributionRepository.save(contribution));
    }

    @Override
    public List<ContributionDto> getAllContributions() {
        return contributionRepository.findAll().stream()
                .map(contributionMapper::toDto)
                .toList();
    }

    @Override
    public ContributionDto getContributionById(Long id) {
        return contributionMapper.toDto(
                contributionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contribution not found"))
        );
    }

    @Override
    public ContributionDto createContribution(ContributionDto contributionDto) {
        Contribution contribution = contributionMapper.toEntity(contributionDto);
        Project project = projectRepository.findById(
                contributionDto.getProject().getId()).orElseThrow(() -> new EntityNotFoundException("Project not found")
        );
        User user = userRepository.findById(
                contributionDto.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found")
        );
        contribution.setProject(project);
        contribution.setUser(user);
        contribution.setContributionDate(LocalDateTime.now());
        return contributionMapper.toDto(contributionRepository.save(contribution));
    }

    @Override
    public List<ContributionDto> findAllByProjectIdAndStatus(Long projectId, ContributionStatus status) {
        List<Contribution> contributions = contributionRepository.findAllByProjectIdAndStatus(projectId, status);
        return contributions.stream()
                .map(contributionMapper::toDto)
                .toList();
    }

    @Override
    public void setComplete(Long id) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contribution not found with id: " + id));
        contribution.setStatus(ContributionStatus.COMPLETED);
        contributionRepository.save(contribution);
    }

    @Override
    public void setCancel(Long id) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contribution not found with id: " + id));
        contribution.setStatus(ContributionStatus.CANCELED);
        contributionRepository.save(contribution);
    }

    @Override
    public List<ContributionDto> getUserContributionPercentageByUserIdAndStatus(Long userId, ContributionStatus status) {
        List<ContributionDto> result = new ArrayList<>();
        List<Object[]> allUserContributionPercentageByUserId = contributionRepository.getUserContributionPercentageByUserId(userId, status);

        for (Object[] row : allUserContributionPercentageByUserId) {
            ContributionDto contributionDto = new ContributionDto();
            contributionDto.setUserContributionPercentage((BigDecimal) row[0]);

            ProjectDto projectDto = new ProjectDto();
            projectDto.setId((Long) row[1]);
            projectDto.setTitle((String) row[2]);
            contributionDto.setProject(projectDto);

            result.add(contributionDto);
        }

        return result;
    }

    @Override
    public List<ContributionDto> getUserContributionPercentageByProjectIdAndStatus(Long projectId, ContributionStatus status) {
        List<ContributionDto> result = new ArrayList<>();
        List<Object[]> allUserContributionPercentageByProjectId = contributionRepository.getUserContributionPercentageByProjectId(projectId, status);

        for (Object[] row : allUserContributionPercentageByProjectId) {
            ContributionDto contributionDto = new ContributionDto();
            contributionDto.setUserContributionPercentage((BigDecimal) row[0]);
            contributionDto.setAmount((BigDecimal) row[1]);

            UserDto userDto = new UserDto();
            userDto.setId((Long) row[2]);
            userDto.setUsername((String) row[3]);
            contributionDto.setUser(userDto);

            result.add(contributionDto);
        }

        return result;
    }
}
