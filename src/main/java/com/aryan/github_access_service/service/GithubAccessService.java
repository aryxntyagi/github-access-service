package com.aryan.github_access_service.service;

import com.aryan.github_access_service.client.GithubApiClient;
import com.aryan.github_access_service.model.UserRepoAccess;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GithubAccessService {

    private final GithubApiClient githubApiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GithubAccessService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    public List<UserRepoAccess> generateAccessReport(String org, String token) {

        Map<String, List<String>> userRepoMap = new HashMap<>();

        try {

            System.out.println("===== STEP 1 : Calling GitHub Repos API =====");

            ResponseEntity<String> repoResponse =
                    githubApiClient.getOrganizationRepos(org, token);

            System.out.println("Repos API Status: " + repoResponse.getStatusCode());

            if (repoResponse.getBody() != null) {
                String body = repoResponse.getBody();
                System.out.println("Repos API Body Preview: "
                        + body.substring(0, Math.min(body.length(), 200)));
            }

            JsonNode repoArray =
                    objectMapper.readTree(repoResponse.getBody());

            System.out.println("Total Repositories Found: " + repoArray.size());

            for (JsonNode repoNode : repoArray) {

                String repoName =
                        repoNode.get("name").asText();

                System.out.println("\n➡ Processing Repo: " + repoName);

                ResponseEntity<String> collabResponse =
                        githubApiClient.getRepoCollaborators(org, repoName, token);

                System.out.println("Collaborator API Status: "
                        + collabResponse.getStatusCode());

                JsonNode collabArray =
                        objectMapper.readTree(collabResponse.getBody());

                System.out.println("Collaborators Count: "
                        + collabArray.size());

                for (JsonNode userNode : collabArray) {

                    String username =
                            userNode.get("login").asText();

                    userRepoMap
                            .computeIfAbsent(username,
                                    k -> new ArrayList<>())
                            .add(repoName);
                }
            }

        } catch (Exception e) {

            System.out.println("❌ ERROR OCCURRED WHILE FETCHING DATA");
            e.printStackTrace();
        }

        List<UserRepoAccess> result = new ArrayList<>();

        for (String user : userRepoMap.keySet()) {
            result.add(
                    new UserRepoAccess(
                            user,
                            userRepoMap.get(user)
                    )
            );
        }

        System.out.println("\n===== FINAL USERS COUNT: " + result.size() + " =====");

        return result;
    }
}