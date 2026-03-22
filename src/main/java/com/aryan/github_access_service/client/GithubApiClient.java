package com.aryan.github_access_service.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubApiClient {

    private static final String BASE_URL = "https://api.github.com";

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getOrganizationRepos(String org, String token) {

        String url = BASE_URL + "/orgs/" + org + "/repos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
    }

    public ResponseEntity<String> getRepoCollaborators(String org, String repo, String token) {

        String url = BASE_URL + "/repos/" + org + "/" + repo + "/collaborators";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
    }
}