package ir.digixo.crowdfundapp.repository;

import ir.digixo.crowdfundapp.entity.Project;
import ir.digixo.crowdfundapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);
}
