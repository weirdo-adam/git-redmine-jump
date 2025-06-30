package com.pengfei.li.gitredminejump;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

@Service
@State(name = "GitRedmineJumpSettings", storages = @Storage("GitRedmineJumpSettings.xml"))
public final class SettingsState implements PersistentStateComponent<SettingsState> {
    public String branchPattern = "\\d+"; // 默认正则表达式
    public String redmineBaseUrl = "https://redmine.example.com/issues/"; // 默认 Redmine 地址

    @Override
    public @NotNull SettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SettingsState state) {
        this.branchPattern = state.branchPattern;
        this.redmineBaseUrl = state.redmineBaseUrl;
    }

    public static SettingsState getInstance() {
        return ApplicationManager.getApplication().getService(SettingsState.class);
    }
}