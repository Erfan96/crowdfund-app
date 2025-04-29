package ir.digixo.crowdfundapp.repository;

import ir.digixo.crowdfundapp.entity.Contribution;
import ir.digixo.crowdfundapp.entity.Project;
import ir.digixo.crowdfundapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findByUser(User user);
    List<Contribution> findByProject(Project project);
}
