package com.adeuxy.gituserswitch;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import org.jetbrains.annotations.NotNull;

public class GitUserCheckinHandlerFactory extends CheckinHandlerFactory {


    /**
     * @param checkinProjectPanel
     * @param commitContext
     * @return
     */
    @Override
    public @NotNull CheckinHandler createHandler(@NotNull CheckinProjectPanel checkinProjectPanel, @NotNull CommitContext commitContext) {
        Project project = checkinProjectPanel.getProject();
        return new GitUserCheckinHandler(project);
    }
}