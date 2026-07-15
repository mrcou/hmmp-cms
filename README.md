# HMMP
## 基于 Spring Boot + Vue3 前后端分离的企业级管理系统

## 项目简介

HMMP 是一套前后端分离的管理系统，后端采用 Spring Boot 多模块架构，前端基于 Vue3 + Ant Design Vue 构建。系统内置了用户、部门、角色、菜单等基础权限管理能力，并提供代码生成、定时任务、系统监控等辅助开发工具，可作为中后台管理系统的基础脚手架。

## 技术架构

项目采用前后端分离模式：

- **后端**：Maven 多模块工程，模块间通过接口解耦，便于独立维护和扩展。
- **前端**：基于 Vben Admin 框架的 Vue3 单页应用，通过 REST API 与后端交互。
- **鉴权**：基于 JWT 的无状态令牌认证，登录态与验证信息存储于 Redis，支持多终端登录。
- **数据访问**：MyBatis 作为持久层框架，Druid 提供数据库连接池与 SQL 监控，PageHelper 提供分页支持。

### 后端模块划分

| 模块 | 说明 |
| :--- | :--- |
| `hmmp-admin` | 启动入口，聚合各业务模块，提供 Web Controller 层 |
| `hmmp-framework` | 核心框架层，包含安全认证（Spring Security）、AOP、拦截器、数据源等基础能力 |
| `hmmp-system` | 系统管理模块，用户、部门、角色、菜单、字典、参数、日志等业务逻辑与数据访问 |
| `hmmp-quartz` | 定时任务模块，基于 Quartz 实现任务的可视化管理与调度 |
| `hmmp-generator` | 代码生成模块，基于数据表结构自动生成前后端 CRUD 代码 |
| `hmmp-common` | 通用工具模块，提供公共常量、工具类、异常处理等基础组件 |
| `hmmp-vben` | 前端工程，基于 Vben Admin（Vue3）构建的管理后台界面 |

## 技术栈

### 后端

| 技术 | 说明 |
| :--- | :--- |
| Java 17 | 开发语言 |
| Spring Boot 4.x | 应用基础框架 |
| Spring Security | 认证与授权框架 |
| JWT (jjwt) | 无状态令牌生成与解析 |
| MyBatis | ORM 持久层框架 |
| Druid | 数据库连接池及 SQL 监控 |
| PageHelper | MyBatis 分页插件 |
| Redis | 缓存、会话及验证码存储 |
| Quartz | 定时任务调度 |
| Velocity | 代码生成模板引擎 |
| Kaptcha | 图形验证码 |
| Springdoc OpenAPI | 接口文档（Swagger UI） |
| Fastjson2 | JSON 序列化 |
| Apache POI | Excel 导入导出 |
| OSHI | 服务器硬件信息采集 |
| MySQL | 关系型数据库 |

### 前端

| 技术 | 说明 |
| :--- | :--- |
| Vue 3 | 前端框架 |
| TypeScript | 开发语言 |
| Vite | 构建工具 |
| Vben Admin | 中后台前端框架 |
| Ant Design Vue (antdv-next) | UI 组件库 |
| Pinia | 状态管理 |
| Vue Router 4 | 路由管理 |

## 数据结构

系统核心数据表（MySQL，前缀 `sys_`）：

| 表名 | 说明 |
| :--- | :--- |
| `sys_user` | 用户信息表 |
| `sys_dept` | 部门表 |
| `sys_post` | 岗位信息表 |
| `sys_role` | 角色信息表 |
| `sys_menu` | 菜单权限表 |
| `sys_user_role` | 用户与角色关联表 |
| `sys_role_menu` | 角色与菜单关联表 |
| `sys_role_dept` | 角色与部门关联表（数据权限） |
| `sys_user_post` | 用户与岗位关联表 |
| `sys_dict_type` / `sys_dict_data` | 数据字典类型与字典数据表 |
| `sys_config` | 系统参数配置表 |
| `sys_notice` / `sys_notice_read` | 通知公告表及已读记录表 |
| `sys_oper_log` | 操作日志记录表 |
| `sys_logininfor` | 登录访问日志表 |
| `sys_job` / `sys_job_log` | 定时任务调度表及执行日志表 |
| `gen_table` / `gen_table_column` | 代码生成业务表及字段配置表 |

## 功能描述

1. **用户管理**：系统操作者维护，支持用户新增、编辑、状态管理、密码重置及岗位/角色分配。
2. **部门管理**：以树形结构维护组织架构（公司、部门、小组），支持基于部门的数据权限控制。
3. **岗位管理**：维护用户在部门中所任职务。
4. **菜单管理**：配置系统菜单、页面路由及按钮级操作权限标识。
5. **角色管理**：分配角色对应的菜单权限，并可按机构划分数据范围权限。
6. **字典管理**：维护系统中常用的固定选项数据（如状态、类型等）。
7. **参数管理**：动态维护系统级配置参数，无需重启即可生效。
8. **通知公告**：发布和维护系统内的通知公告信息。
9. **操作日志**：记录并查询系统正常操作及异常操作日志。
10. **登录日志**：记录并查询系统登录情况，包含异常登录识别。
11. **在线用户**：监控当前系统中活跃的在线用户会话，支持强制下线。
12. **定时任务**：可视化管理定时任务的新增、修改、删除、暂停/恢复，并记录执行结果日志。
13. **代码生成**：基于数据库表结构一键生成 Java、Vue、SQL 等前后端 CRUD 代码。
14. **接口文档**：基于 Springdoc 自动生成并展示业务接口的 API 文档（Swagger UI）。
15. **服务监控**：实时查看服务器 CPU、内存、磁盘、JVM 等运行状态信息。
16. **缓存监控**：查看 Redis 缓存使用情况及命令统计信息。
17. **连接池监控**：基于 Druid 监控数据库连接池状态，辅助排查 SQL 性能问题。

## 快速启动

### 后端

```bash
# 在项目根目录下编译打包
mvn clean package

# 启动方式一：直接运行 jar
java -jar hmmp-admin/target/hmmp-admin.jar

# 启动方式二：使用启动脚本（Linux/macOS）
./server.sh start
```

后端服务默认监听 `8080` 端口，数据库、Redis 等连接信息见 `hmmp-admin/src/main/resources/application*.yml`。

### 前端

```bash
cd hmmp-vben

# 安装依赖
pnpm install

# 本地开发
pnpm dev

# 生产构建
pnpm build
```
