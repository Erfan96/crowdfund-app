package ir.digixo.crowdfundapp.dto;

import ir.digixo.crowdfundapp.entity.enumeration.ContributionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContributionDto {

    private Long id;

    private UserDto user;

    private ProjectDto project;

    private BigDecimal amount;

    private LocalDateTime contributionDate;

    private ContributionStatus status;

}
