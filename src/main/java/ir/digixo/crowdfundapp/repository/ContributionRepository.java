package ir.digixo.crowdfundapp.repository;

import ir.digixo.crowdfundapp.entity.Contribution;
import ir.digixo.crowdfundapp.entity.enumeration.ContributionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findByUserId(Long userId);
    List<Contribution> findByProjectId(Long projectId);

    @Query("select sum(c.amount) from Contribution c where c.project.id = :projectId")
    BigDecimal getTotalAmountForProject(@Param("projectId") Long projectId);

    @Query("select sum(c.amount) from Contribution c where c.user.id = :userId")
    BigDecimal getTotalAmountForUser(@Param("userId") Long userId);

    @Query("select sum (c.amount) from Contribution c where c.project.id = :projectId and c.user.id = :userId")
    BigDecimal getTotalAmountForUserPerProject(@Param("projectId") Long projectId, @Param("userId") Long userId);

    List<Contribution> findTop10ByProjectIdOrderByContributionDateDesc(Long projectId);

    List<Contribution> findAllByProjectIdAndStatus(Long projectId, ContributionStatus status);

    @Query("""
    SELECT Round(((c.amount / p.targetAmount) * 100), 2) AS userContributionPercentage,
    c.project.id, p.title FROM Contribution c
    LEFT JOIN Project p ON c.project.id = p.id
    WHERE c.user.id = :userId AND c.status = :status
    """)
    List<Object[]> getUserContributionPercentageByUserId(Long userId, ContributionStatus status);

    @Query("""
    SELECT Round(((c.amount / p.targetAmount) * 100), 2) AS userContributionPercentage,
    c.user.id, c.user.username FROM Contribution c
    LEFT JOIN Project p ON c.project.id = p.id
    WHERE c.project.id = :projectId AND c.status = :status
    """)
    List<Object[]> getUserContributionPercentageByProjectId(Long projectId, ContributionStatus status);
}
