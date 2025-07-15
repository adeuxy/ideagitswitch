package com.adeuxy.gituserswitch;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "GitUserProjectState",
        storages = @Storage("gitUserProjectState.xml")
)
public class GitUserProjectState implements PersistentStateComponent<GitUserProjectState.StateData> {
    public static class StateData {
        public Integer fixedUserIndex = null;
        public boolean fixed = false;
    }

    private StateData state = new StateData();

    @Nullable
    @Override
    public StateData getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull StateData state) {
        this.state = state;
    }

    public Integer getFixedUserIndex() {
        return state.fixedUserIndex;
    }

    public void setFixedUserIndex(Integer idx) {
        state.fixedUserIndex = idx;
    }

    public boolean isFixed() {
        return state.fixed;
    }

    public void setFixed(boolean fixed) {
        state.fixed = fixed;
    }
} 