package ir.digixo.crowdfundapp.dto;

import ir.digixo.crowdfundapp.entity.enumeration.ProjectStatus;
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
public class ProjectDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal targetAmount;

    private BigDecimal collectedAmount;

    private UserDto user;

    private ProjectStatus status;

}
