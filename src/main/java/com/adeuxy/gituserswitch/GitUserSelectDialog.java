package com.adeuxy.gituserswitch;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GitUserSelectDialog extends DialogWrapper {
    private final List<GitUserConfigLoader.UserProfile> users;
    private final JList<String> userList;
    private final JCheckBox fixedCheckBox;
    private JPanel mainPanel;
    private boolean fixedMode = false;

    public GitUserSelectDialog(Project project, List<GitUserConfigLoader.UserProfile> users, int defaultIndex, boolean fixedDefault) {
        super(project);
        this.users = users;
        setTitle("Git User switch");
        userList = new JList<>(users.stream().map(Object::toString).toArray(String[]::new));
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setSelectedIndex(defaultIndex);
        fixedCheckBox = new JCheckBox("pin", fixedDefault);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
        mainPanel.add(fixedCheckBox, BorderLayout.EAST);
        return mainPanel;
    }

    public int getSelectedUserIndex() {
        return userList.getSelectedIndex();
    }

    public boolean isFixedSelected() {
        return fixedCheckBox.isSelected();
    }

    public void setFixedMode(boolean fixedMode) {
        this.fixedMode = fixedMode;
        if (userList != null) {
            userList.setEnabled(!fixedMode);
        }
        if (fixedCheckBox != null) {
            fixedCheckBox.setEnabled(!fixedMode);
        }
    }
} 