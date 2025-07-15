package com.adeuxy.gituserswitch;

import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GitUserConfigLoader {
    private static final String DEFAULT_CONFIG = ".git_user_profiles";

    public static List<UserProfile> loadUserProfiles() {
        Path configPath = Paths.get(System.getProperty("user.home"), DEFAULT_CONFIG);
        List<UserProfile> result = new ArrayList<>();
        if (!Files.exists(configPath)) return result;
        Yaml yaml = new Yaml();
        try (InputStream in = Files.newInputStream(configPath)) {
            Map<String, Object> config = yaml.load(in);
            List<Map<String, String>> users = (List<Map<String, String>>) config.get("users");
            if (users != null) {
                for (Map<String, String> u : users) {
                    result.add(new UserProfile(u.get("name"), u.get("email")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class UserProfile {
        public final String name;
        public final String email;
        public UserProfile(String name, String email) {
            this.name = name;
            this.email = email;
        }
        @Override
        public String toString() {
            return name + " <" + email + ">";
        }
    }
} 