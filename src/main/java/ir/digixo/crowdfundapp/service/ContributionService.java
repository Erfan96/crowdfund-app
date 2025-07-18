package ir.digixo.crowdfundapp.service;

import ir.digixo.crowdfundapp.dto.ContributionDto;
import ir.digixo.crowdfundapp.entity.enumeration.ContributionStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ContributionService {
    ContributionDto createContribution(ContributionDto contributionDto);
    ContributionDto getContributionById(Long id);
    List<ContributionDto> getAllContributions();
    ContributionDto updateContribution(ContributionDto contributionDto);
    void deleteContribution(Long id);

    List<ContributionDto> findAllByProjectIdAndStatus(Long projectId, ContributionStatus status);
    List<ContributionDto> getContributionsByUserId(Long userId);
    List<ContributionDto> getContributionsByProjectId(Long projectId);
    BigDecimal getTotalAmountContributedToProject(Long projectId);
    BigDecimal getTotalAmountContributionByUserId(Long userId);
    BigDecimal getTotalAmountContributionByUserIdPerProject(Long projectId, Long userId);
    List<ContributionDto> getRecentContributionsByProjectId(Long projectId);
    BigDecimal getProjectFundingProgress(Long projectId);
    void setCancel(Long id);
    void setComplete(Long id);
    List<ContributionDto> getUserContributionPercentageByUserIdAndStatus(Long userId, ContributionStatus status);
    List<ContributionDto> getUserContributionPercentageByProjectIdAndStatus(Long projectId, ContributionStatus status);

}
