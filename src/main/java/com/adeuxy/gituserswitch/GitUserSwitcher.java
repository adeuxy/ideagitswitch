package com.adeuxy.gituserswitch;

import java.io.IOException;

public class GitUserSwitcher {
    public static void switchUser(String name, String email, String repoPath) {
        try {
            Process p1 = new ProcessBuilder("git", "config", "user.name", name)
                    .directory(new java.io.File(repoPath)).start();
            p1.waitFor();
            Process p2 = new ProcessBuilder("git", "config", "user.email", email)
                    .directory(new java.io.File(repoPath)).start();
            p2.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
} 