package com.adeuxy.gituserswitch;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

public class GitUserCheckinHandlerFactory extends CheckinHandlerFactory {
    private static final Logger LOG = Logger.getInstance(GitUserCheckinHandlerFactory.class);

    /**
     * @param checkinProjectPanel
     * @param commitContext
     * @return
     */
    @Override
    public @NotNull CheckinHandler createHandler(@NotNull CheckinProjectPanel checkinProjectPanel, @NotNull CommitContext commitContext) {
        LOG.info("[GitUserSwitch] CheckinHandlerFactory.createHandler called");
        Project project = checkinProjectPanel.getProject();
        LOG.info("[GitUserSwitch] Project: " + project.getName());
        return new GitUserCheckinHandler(project);
    }
}