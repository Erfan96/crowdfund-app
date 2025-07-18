package ir.digixo.crowdfundapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.digixo.crowdfundapp.dto.ContributionDto;
import ir.digixo.crowdfundapp.entity.enumeration.ContributionStatus;
import ir.digixo.crowdfundapp.service.ContributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/contributions")
@RequiredArgsConstructor
@Tag(name = "Contribution", description = "Contribution management APIs")
public class ContributionController {

    private final ContributionService contributionService;

    @PostMapping
    @Operation(summary = "Create a new contribution")
    @ApiResponse(responseCode = "201", description = "Contribution created")
    public ResponseEntity<ContributionDto> createContribution(
            @Valid @RequestBody ContributionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contributionService.createContribution(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing contribution")
    public ResponseEntity<ContributionDto> updateContribution(
            @Valid @RequestBody ContributionDto dto) {
        return ResponseEntity.ok(contributionService.updateContribution(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a contribution by ID")
    public ResponseEntity<ContributionDto> getContributionById(@PathVariable Long id) {
        return ResponseEntity.ok(contributionService.getContributionById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a contribution by ID")
    public ResponseEntity<Void> deleteContribution(@PathVariable Long id) {
        contributionService.deleteContribution(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get a user's contribution")
    public ResponseEntity<List<ContributionDto>> getContributionByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(contributionService.getContributionsByUserId(userId));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get a contribution's to a specific project")
    public ResponseEntity<List<ContributionDto>> getContributionByProjectId(@PathVariable Long projectId) {
        return ResponseEntity.ok(contributionService.getContributionsByProjectId(projectId));
    }

    @GetMapping("/project/{projectId}/status/{status}")
    @Operation(summary = "Get contributions by project and status")
    public ResponseEntity<List<ContributionDto>> getByProjectAndStatus(
            @PathVariable Long projectId,
            @PathVariable ContributionStatus status) {
        return ResponseEntity.ok(contributionService.findAllByProjectIdAndStatus(projectId, status));
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Complete a contribution")
    public ResponseEntity<Void> completeContribution(@PathVariable Long id) {
        contributionService.setComplete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel a contribution")
    public ResponseEntity<Void> cancelContribution(@PathVariable Long id) {
        contributionService.setCancel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all contributions")
    public ResponseEntity<List<ContributionDto>> getAllContributions() {
        return ResponseEntity.ok(contributionService.getAllContributions());
    }

    @GetMapping("/project/{projectId}/progress")
    @Operation(summary = "Get funding progress percentage of a project")
    public ResponseEntity<BigDecimal> getFundingProgress(@PathVariable Long projectId) {
        return ResponseEntity.ok(contributionService.getProjectFundingProgress(projectId));
    }

    @GetMapping("/project/{projectId}/recent")
    @Operation(summary = "Get 10 most recent contributions for a project")
    public ResponseEntity<List<ContributionDto>> getRecentContributions(@PathVariable Long projectId) {
        return ResponseEntity.ok(contributionService.getRecentContributionsByProjectId(projectId));
    }

    @GetMapping("/user/{userId}/total")
    @Operation(summary = "Get total contribution amount by user")
    public ResponseEntity<BigDecimal> getTotalAmountByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(contributionService.getTotalAmountContributionByUserId(userId));
    }

    @GetMapping("/user/{userId}/total-per-project")
    @Operation(summary = "Get total contribution amount by user per project")
    public ResponseEntity<BigDecimal> getTotalAmountByUserPerProject(
            @PathVariable Long userId,
            @RequestParam Long projectId
    ) {
        return ResponseEntity.ok(contributionService.getTotalAmountContributionByUserIdPerProject(projectId, userId));
    }

    @GetMapping("/project/{projectId}/total")
    @Operation(summary = "Get total amount contributed to a project")
    public ResponseEntity<BigDecimal> getTotalAmountToProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(contributionService.getTotalAmountContributedToProject(projectId));
    }

    @GetMapping("/user/{userId}/contribution-percentage")
    @Operation(summary = "Get user contribution percentage per project with status filter")
    public ResponseEntity<List<ContributionDto>> getUserContributionPercentageByUserId(
            @PathVariable Long userId,
            @RequestParam ContributionStatus status
    ) {
        return ResponseEntity.ok(contributionService.getUserContributionPercentageByUserIdAndStatus(userId, status));
    }

    @GetMapping("/project/{projectId}/contribution-percentage")
    @Operation(summary = "Get user contribution percentage by project id with status filter")
    public ResponseEntity<List<ContributionDto>> getUserContributionPercentageByProjectId(
            @PathVariable Long projectId,
            @RequestParam ContributionStatus status
    ) {
        return ResponseEntity.ok(contributionService.getUserContributionPercentageByProjectIdAndStatus(projectId, status));
    }

}
