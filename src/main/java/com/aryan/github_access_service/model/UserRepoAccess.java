package com.aryan.github_access_service.model;

import java.util.List;

public class UserRepoAccess {

    private String username;
    private List<String> repositories;

    public UserRepoAccess(String username, List<String> repositories) {
        this.username = username;
        this.repositories = repositories;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRepositories() {
        return repositories;
    }
}