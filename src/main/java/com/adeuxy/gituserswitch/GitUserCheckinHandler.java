package com.adeuxy.gituserswitch;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import com.intellij.openapi.diagnostic.Logger;

import java.util.List;


public class GitUserCheckinHandler extends CheckinHandler {
    private final Project project;
    // Static fields for demo; replace with persistent storage if needed
    private static Integer fixedUserIndex = null;
    private static boolean fixed = false;
    private static final Logger LOG = Logger.getInstance(GitUserCheckinHandler.class);

    public GitUserCheckinHandler(Project project) {
        this.project = project;
        LOG.info("[GitUserSwitch] GitUserCheckinHandler created for project: " + project.getName());
    }

    @Override
    public ReturnResult beforeCheckin() {
        LOG.info("[GitUserSwitch] beforeCheckin called");
        List<GitUserConfigLoader.UserProfile> users = GitUserConfigLoader.loadUserProfiles();
        LOG.info("[GitUserSwitch] loaded users: " + users.size());
        if (users.isEmpty()) {
            LOG.info("[GitUserSwitch] No users found, skipping dialog");
            return ReturnResult.COMMIT;
        }
        GitUserProjectState state = project.getService(GitUserProjectState.class);
        if (state == null) {
            LOG.warn("[GitUserSwitch] GitUserProjectState is null, creating new");
            state = new GitUserProjectState();
        }
        int defaultIndex = 0;
        boolean fixedDefault = false;
        Integer fixedUserIndex = state.getFixedUserIndex();
        LOG.info("[GitUserSwitch] fixedUserIndex=" + fixedUserIndex + ", isFixed=" + state.isFixed());
        if (state.isFixed() && fixedUserIndex != null && fixedUserIndex >= 0 && fixedUserIndex < users.size()) {
            defaultIndex = fixedUserIndex;
            fixedDefault = true;
        }
        LOG.info("[GitUserSwitch] Showing dialog, defaultIndex=" + defaultIndex + ", fixedDefault=" + fixedDefault);
        GitUserSelectDialog dialog = new GitUserSelectDialog(project, users, defaultIndex, fixedDefault);
        if (!dialog.showAndGet()) {
            LOG.info("[GitUserSwitch] Dialog cancelled by user");
            return ReturnResult.CANCEL;
        }
        int userIndex = dialog.getSelectedUserIndex();
        LOG.info("[GitUserSwitch] User selected index: " + userIndex);
        state.setFixed(dialog.isFixedSelected());
        if (dialog.isFixedSelected()) {
            state.setFixedUserIndex(userIndex);
        } else {
            state.setFixedUserIndex(null);
        }
        GitRepositoryManager repoManager = GitRepositoryManager.getInstance(project);
        List<GitRepository> repos = repoManager.getRepositories();
        if (!repos.isEmpty()) {
            String repoPath = repos.get(0).getRoot().getPath();
            GitUserConfigLoader.UserProfile user = users.get(userIndex);
            LOG.info("[GitUserSwitch] Switching user to: " + user.name + " <" + user.email + "> for repo: " + repoPath);
            GitUserSwitcher.switchUser(user.name, user.email, repoPath);
        } else {
            LOG.warn("[GitUserSwitch] No git repositories found in project");
        }
        return ReturnResult.COMMIT;
    }
} 