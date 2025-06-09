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

    List<Contribution> findTop10ByProjectIdOrderByContributionDateDesc(Long projectId);

    List<Contribution> findAllByProjectIdAndStatus(Long projectId, ContributionStatus status);

    @Query("""
    SELECT c.project.id, c.project.title, SUM(c.amount)
    FROM Contribution c
    WHERE c.user.id = :userId AND c.status = :status
    GROUP BY c.project.id, c.project.title
    """)
    List<Object[]> findUserContributionByProject(@Param("userId") Long userId,
                                                 @Param("status") ContributionStatus status);

    @Query("""
    SELECT c.project.id, SUM(c.amount)
    FROM Contribution c
    WHERE c.status = :status
    GROUP BY c.project.id
    """)
    List<Object[]> findTotalContributionPerProject(@Param("status") ContributionStatus status);
}
