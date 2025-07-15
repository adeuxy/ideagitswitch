# IDEA Git User Switch 插件

<div><a href="https://plugins.jetbrains.com/plugin/27912-git-user-switch" target="_blank">Get from Marketplace</a></div>

每次提交代码时弹出用户选择框，自动切换 git 用户（user.name/user.email），用户列表可通过 `~/.git_user_profiles`(C:\Users\你的用户名\.git_user_profiles) 配置。

## 配置文件示例

```yaml
users:
  - name: Alice
    email: alice@example.com
  - name: Bob
    email: bob@example.com
```

## 功能
- 提交前弹窗选择用户
- 自动切换 git 用户
- 支持自定义配置文件路径（后续可扩展） 
