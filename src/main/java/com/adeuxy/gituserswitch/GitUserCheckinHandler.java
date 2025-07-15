package com.adeuxy.gituserswitch;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;

import java.util.List;

public class GitUserCheckinHandler extends CheckinHandler {
    private final Project project;

    public GitUserCheckinHandler(Project project) {
        this.project = project;
    }

    @Override
    public ReturnResult beforeCheckin() {
        List<GitUserConfigLoader.UserProfile> users = GitUserConfigLoader.loadUserProfiles();
        if (users.isEmpty()) {
            return ReturnResult.COMMIT;
        }
        String[] userNames = users.stream().map(u -> u.toString()).toArray(String[]::new);
        int idx = Messages.showChooseDialog(
                project,
                "Select the Git user for this commit:",
                "Git User switch",
                null, // 图标
                userNames,
                userNames[0]
        );
        if (idx >= 0) {
            GitRepositoryManager repoManager = GitRepositoryManager.getInstance(project);
            List<GitRepository> repos = repoManager.getRepositories();
            if (!repos.isEmpty()) {
                String repoPath = repos.get(0).getRoot().getPath();
                GitUserConfigLoader.UserProfile user = users.get(idx);
                GitUserSwitcher.switchUser(user.name, user.email, repoPath);
            }
        }
        return ReturnResult.COMMIT;
    }
} 