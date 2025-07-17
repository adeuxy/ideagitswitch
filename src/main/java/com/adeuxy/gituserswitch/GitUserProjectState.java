package com.adeuxy.gituserswitch;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.components.Service;


@Service(Service.Level.PROJECT)
@State(
        name = "GitUserProjectState",
        storages = @Storage("gitUserProjectState.xml")
)
public final class GitUserProjectState implements PersistentStateComponent<GitUserProjectState.StateData> {
    public static class StateData {
        public Integer fixedUserIndex = null;
        public boolean fixed = false;
    }

    private StateData state = new StateData();

    @Nullable
    @Override
    public StateData getState() {
        if (state == null) state = new StateData();
        return state;
    }

    @Override
    public void loadState(@NotNull StateData state) {
        if (state == null) {
            this.state = new StateData();
        } else {
            this.state = state;
        }
    }

    public Integer getFixedUserIndex() {
        if (state == null) state = new StateData();
        return state.fixedUserIndex;
    }

    public void setFixedUserIndex(Integer idx) {
        if (state == null) state = new StateData();
        state.fixedUserIndex = idx;
    }

    public boolean isFixed() {
        if (state == null) state = new StateData();
        return state.fixed;
    }

    public void setFixed(boolean fixed) {
        if (state == null) state = new StateData();
        state.fixed = fixed;
    }
} 