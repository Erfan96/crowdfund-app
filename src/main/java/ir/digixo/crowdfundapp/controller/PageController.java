package ir.digixo.crowdfundapp.controller;

import ir.digixo.crowdfundapp.dto.ProjectDto;
import ir.digixo.crowdfundapp.service.ContributionService;
import ir.digixo.crowdfundapp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ProjectService projectService;
    private final ContributionService contributionService;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/create-user")
    public String createUserPage() {
        return "create-user";
    }

    @GetMapping("/create-project")
    public String createProjectPage() {
        return "create-project";
    }

    @GetMapping("/projects")
    public String projectListPage() {
        return "projects";
    }

    @GetMapping("/users")
    public String userListPage() {
        return "users";
    }
}