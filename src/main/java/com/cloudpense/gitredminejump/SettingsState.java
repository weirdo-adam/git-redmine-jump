package com.cloudpense.gitredminejump;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
@State(name = "GitRedmineJumpSettings", storages = @Storage("GitRedmineJumpSettings.xml"))
public final class SettingsState implements PersistentStateComponent<SettingsState> {
    public String branchPattern = "\\d+"; // 默认正则表达式
    public String redmineBaseUrl = "https://redmine.example.com/issues/"; // 默认 Redmine 地址

    @Nullable
    @Override
    public SettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SettingsState state) {
        this.branchPattern = state.branchPattern;
        this.redmineBaseUrl = state.redmineBaseUrl;
    }

    public static SettingsState getInstance() {
        return ServiceManager.getService(SettingsState.class);
    }
}