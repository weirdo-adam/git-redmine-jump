package com.pengfei.li.gitredminejump;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class SettingsConfigurable implements Configurable {
    private JPanel mainPanel;
    private JTextField branchPatternField;
    private JTextField redmineBaseUrlField;

    @Override
    public @Nullable JComponent createComponent() {
        mainPanel = new JPanel(new BorderLayout());

        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        branchPatternField = new JTextField(20);
        redmineBaseUrlField = new JTextField(20);

        // 第一行：Branch Naming Pattern
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0; // 标签占小宽度
        formPanel.add(new JLabel("branch naming pattern (regex):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(branchPatternField, gbc);

        // 第二行：Redmine Base URL
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("redmine base url:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        formPanel.add(redmineBaseUrlField, gbc);

        // 将表单面板添加到主面板顶部
        mainPanel.add(formPanel, BorderLayout.NORTH);

        return mainPanel;
    }

    @Override
    public boolean isModified() {
        SettingsState state = SettingsState.getInstance();
        return !branchPatternField.getText().equals(state.branchPattern) ||
                !redmineBaseUrlField.getText().equals(state.redmineBaseUrl);
    }

    @Override
    public void apply() {
        SettingsState state = SettingsState.getInstance();
        state.branchPattern = branchPatternField.getText();
        state.redmineBaseUrl = redmineBaseUrlField.getText();
    }

    @Override
    public void reset() {
        SettingsState state = SettingsState.getInstance();
        branchPatternField.setText(state.branchPattern);
        redmineBaseUrlField.setText(state.redmineBaseUrl);
    }

    @Override
    public String getDisplayName() {
        return "Git Redmine Jump";
    }
}