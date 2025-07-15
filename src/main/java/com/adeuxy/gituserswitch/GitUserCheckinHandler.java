package com.adeuxy.gituserswitch;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import com.intellij.openapi.components.ServiceManager;

import java.util.List;

public class GitUserCheckinHandler extends CheckinHandler {
    private final Project project;
    // Static fields for demo; replace with persistent storage if needed
    private static Integer fixedUserIndex = null;
    private static boolean fixed = false;

    public GitUserCheckinHandler(Project project) {
        this.project = project;
    }

    @Override
    public ReturnResult beforeCheckin() {
        List<GitUserConfigLoader.UserProfile> users = GitUserConfigLoader.loadUserProfiles();
        if (users.isEmpty()) {
            return ReturnResult.COMMIT;
        }
        GitUserProjectState state = project.getService(GitUserProjectState.class);
        int userIndex = 0;
        if (state.isFixed() && state.getFixedUserIndex() != null && state.getFixedUserIndex() >= 0 && state.getFixedUserIndex() < users.size()) {
            userIndex = state.getFixedUserIndex();
        } else {
            GitUserSelectDialog dialog = new GitUserSelectDialog(project, users, 0, false);
            if (!dialog.showAndGet()) {
                // User cancelled
                return ReturnResult.CANCEL;
            }
            userIndex = dialog.getSelectedUserIndex();
            state.setFixed(dialog.isFixedSelected());
            if (dialog.isFixedSelected()) {
                state.setFixedUserIndex(userIndex);
            } else {
                state.setFixedUserIndex(null);
            }
        }
        GitRepositoryManager repoManager = GitRepositoryManager.getInstance(project);
        List<GitRepository> repos = repoManager.getRepositories();
        if (!repos.isEmpty()) {
            String repoPath = repos.get(0).getRoot().getPath();
            GitUserConfigLoader.UserProfile user = users.get(userIndex);
            GitUserSwitcher.switchUser(user.name, user.email, repoPath);
        }
        return ReturnResult.COMMIT;
    }
} 