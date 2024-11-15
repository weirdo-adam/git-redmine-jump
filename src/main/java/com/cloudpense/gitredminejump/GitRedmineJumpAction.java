package com.cloudpense.gitredminejump;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;

import java.awt.Desktop;
import java.net.URI;

public class GitRedmineJumpAction extends AnAction {
    private static final String REDMINE_BASE_URL = "https://redmine.cloudpense.com/issues/";

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        GitRepositoryManager repositoryManager = GitRepositoryManager.getInstance(project);
        GitRepository repository = repositoryManager.getRepositoryForFileQuick(project.getBaseDir());
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
            return;
        }

        // 构造 Redmine URL
        String redmineUrl = REDMINE_BASE_URL + taskId;

        // 打开浏览器
        openInBrowser(redmineUrl);
    }

    private String extractTaskId(String branchName) {
        // 使用正则表达式提取第一个纯数字部分
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+");
        java.util.regex.Matcher matcher = pattern.matcher(branchName);
        if (matcher.find()) {
            return matcher.group(); // 返回第一个匹配的数字
        }
        return null; // 未找到任务编号
    }

    private void openInBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}