package com.aryan.github_access_service.controller;

import com.aryan.github_access_service.model.UserRepoAccess;
import com.aryan.github_access_service.service.GithubAccessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubAccessController {

    private final GithubAccessService githubAccessService;

    public GithubAccessController(GithubAccessService githubAccessService) {
        this.githubAccessService = githubAccessService;
    }

    @GetMapping("/access-report")
    public List<UserRepoAccess> getAccessReport(
            @RequestParam String org,
            @RequestParam String token
    ) {
        return githubAccessService.generateAccessReport(org, token);
    }
}
