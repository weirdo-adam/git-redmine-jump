package com.cloudpense.gitredminejump;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URI;
import java.awt.Desktop;

/**
 * @author pengfei.li
 */
public class GitRedmineJumpAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        // 获取当前 Git 仓库和分支名称
        GitRepository repository = GitRepositoryManager.getInstance(project).getRepositories().stream().findFirst().orElse(null);
        if (repository == null) {
            return;
        }
        String branchName = repository.getCurrentBranchName();
        if (branchName == null) {
            return;
        }

        // 提取任务编号
        String taskId = extractTaskId(branchName);
        if (taskId == null) {
            showNotification(project, "Error", "No task ID found in branch name.", NotificationType.WARNING);
            return;
        }

        // 打开浏览器
        openInBrowser(taskId, project);
    }

    private void openInBrowser(String taskId, Project project) {
        String baseUrl = SettingsState.getInstance().redmineBaseUrl; // 获取用户配置的 Redmine 地址
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        String fullUrl = baseUrl + taskId; // 构建完整的任务 URL

        try {
            Desktop.getDesktop().browse(new URI(fullUrl)); // 打开系统默认浏览器
        } catch (Exception ex) {
            showNotification(project, "Error",  "Failed to open browser: " + ex.getMessage(),
                    NotificationType.ERROR);
            ex.printStackTrace();
        }
    }

    private String extractTaskId(String branchName) {
        String pattern = SettingsState.getInstance().branchPattern;
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(branchName);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private void showNotification(Project project, String title, String content, NotificationType type) {
        Notification notification = NotificationGroupManager.getInstance()
                .getNotificationGroup("Git Redmine Jump Notifications")
                .createNotification(title, content, type);
        notification.notify(project);
    }
}