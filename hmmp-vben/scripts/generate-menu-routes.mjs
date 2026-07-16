/**
 * 开发期间使用：根据 sql/hmmpcms_menu.json 生成前端静态路由模块文件，
 * 免去每次改菜单都要往数据库 sys_menu 表提交 SQL 才能在页面上看到效果。
 *
 * 用法：在 hmmp-vben 目录下执行 node scripts/generate-menu-routes.mjs
 *
 * 原理：vben 的 accessMode 当前配置为 'mixed'（见 src/preferences.ts），
 * 会同时合并"前端静态路由"（本脚本生成的 src/router/routes/modules/*.ts）
 * 和"后端动态路由"（数据库 sys_menu 表，通过 getAllMenusApi 获取），
 * 按路由 name 合并，后端数据优先。
 * 所以这些生成的文件只是开发期间的"临时菜单"，等 sql/hmmpcms_menu.json
 * 通过 node ../sql/menu-json-to-sql.js 转成 SQL 并入库后，
 * 这些同名路由会被数据库里的真实菜单覆盖，即可删除本脚本生成的文件。
 *
 * 支持三级菜单：模块根(M) → 二级目录(M) → 三级页面(C)，以及二级页面(C)。
 */
import { readFileSync, writeFileSync, mkdirSync, unlinkSync, existsSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import path from 'node:path';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const JSON_FILE = path.join(__dirname, '../../sql/hmmpcms_menu.json');
const OUT_DIR = path.join(__dirname, '../src/router/routes/modules');

const data = JSON.parse(readFileSync(JSON_FILE, 'utf8'));

let all = [];
data.groups.forEach((g) => g.items.forEach((it) => all.push(it)));
const byId = new Map(all.map((it) => [it.menuId, it]));

const MODULES = [
  { menuId: 2000, slug: 'editor', icon: 'lucide:file-edit' },
  { menuId: 2100, slug: 'publisher', icon: 'lucide:send' },
  { menuId: 2200, slug: 'statistics', icon: 'lucide:bar-chart-3' },
  { menuId: 2300, slug: 'ad', icon: 'lucide:megaphone' },
  { menuId: 2400, slug: 'author', icon: 'lucide:pen-line' },
  { menuId: 2500, slug: 'reviewer', icon: 'lucide:clipboard-check' },
  { menuId: 2600, slug: 'meeting', icon: 'lucide:calendar' },
  // 互动管理已挂到采编中心下（absoluteRoute），不再作为一级模块生成
];

function pascalCase(p) {
  return p
    .split(/[/-]/)
    .filter(Boolean)
    .map((seg) => seg.charAt(0).toUpperCase() + seg.slice(1))
    .join('');
}

function quote(str) {
  return `'${String(str).replace(/\\/g, '\\\\').replace(/'/g, "\\'")}'`;
}

function renderPageRoute(it, namePrefix, indent = '      ') {
  const childName = `${namePrefix}${pascalCase(it.path)}`;
  const lines = [];
  lines.push(`${indent}{`);
  lines.push(`${indent}  name: ${quote(childName)},`);
  lines.push(`${indent}  path: ${quote(it.path)},`);

  // 与其它模块共用真实页面：仅做 redirect，不重复挂载 component
  if (it.redirect) {
    lines.push(`${indent}  redirect: ${quote(it.redirect)},`);
    lines.push(`${indent}  meta: {`);
    lines.push(`${indent}    title: ${quote(it.menuName)},`);
    lines.push(`${indent}    order: ${it.orderNum},`);
    if (it.icon && it.icon !== '#') {
      lines.push(`${indent}    icon: ${quote(it.icon)},`);
    }
    lines.push(`${indent}    activePath: ${quote(it.redirect)},`);
    lines.push(`${indent}  },`);
    lines.push(`${indent}},`);
    return { name: childName, lines: lines.join('\n'), isRedirect: true };
  }

  lines.push(
    `${indent}  component: () => import(${quote(`#/views/${it.component}.vue`)}),`,
  );
  lines.push(`${indent}  meta: {`);
  lines.push(`${indent}    title: ${quote(it.menuName)},`);
  lines.push(`${indent}    order: ${it.orderNum},`);
  if (it.icon && it.icon !== '#') {
    lines.push(`${indent}    icon: ${quote(it.icon)},`);
  }
  if (it.isCache === 0) lines.push(`${indent}    keepAlive: true,`);
  lines.push(`${indent}  },`);
  lines.push(`${indent}},`);
  return { name: childName, lines: lines.join('\n'), isRedirect: false };
}

mkdirSync(OUT_DIR, { recursive: true });

for (const mod of MODULES) {
  const root = byId.get(mod.menuId);
  if (!root) {
    console.warn(`跳过：找不到 menuId=${mod.menuId}`);
    continue;
  }

  const rootName = pascalCase(mod.slug);
  const directChildren = all
    .filter(
      (it) =>
        it.parentId === root.menuId &&
        (it.menuType === 'C' || it.menuType === 'M'),
    )
    .sort((a, b) => a.orderNum - b.orderNum);

  const childrenBlocks = [];
  let pageCount = 0;

  for (const child of directChildren) {
    if (child.menuType === 'C') {
      const rendered = renderPageRoute(child, rootName);
      childrenBlocks.push(rendered.lines);
      pageCount += 1;
      continue;
    }

    // 二级目录 M → 嵌套三级 C leaf
    // absoluteRoute=true：挂在上级菜单下展示，但 URL 仍为 /{path}/*（如 /message/*）
    const leaves = all
      .filter((it) => it.menuType === 'C' && it.parentId === child.menuId)
      .sort((a, b) => a.orderNum - b.orderNum);

    const abs = !!child.absoluteRoute;
    const dirName = abs
      ? pascalCase(child.path)
      : `${rootName}${pascalCase(child.path)}`;
    const routePath = abs ? `/${child.path}` : child.path;
    const leafBlocks = leaves.map((leaf) =>
      renderPageRoute(leaf, dirName, '        '),
    );
    pageCount += leaves.length;

    const firstLeaf = leaves[0];
    const firstLeafPath = firstLeaf
      ? firstLeaf.redirect ||
        (abs
          ? `/${child.path}/${firstLeaf.path}`
          : `/${mod.slug}/${child.path}/${firstLeaf.path}`)
      : undefined;

    const dirLines = [];
    dirLines.push(`      {`);
    dirLines.push(`        name: ${quote(dirName)},`);
    dirLines.push(`        path: ${quote(routePath)},`);
    dirLines.push(
      `        component: () => import('#/layouts/parent-view.vue'),`,
    );
    dirLines.push(`        meta: {`);
    dirLines.push(`          title: ${quote(child.menuName)},`);
    dirLines.push(`          order: ${child.orderNum},`);
    if (child.icon && child.icon !== '#') {
      dirLines.push(`          icon: ${quote(child.icon)},`);
    }
    dirLines.push(`        },`);
    if (firstLeafPath) {
      dirLines.push(`        redirect: ${quote(firstLeafPath)},`);
    }
    dirLines.push(`        children: [`);
    dirLines.push(leafBlocks.map((b) => b.lines).join('\n'));
    dirLines.push(`        ],`);
    dirLines.push(`      },`);
    childrenBlocks.push(dirLines.join('\n'));
  }

  const rootIcon =
    root.icon && root.icon !== '#' ? root.icon : mod.icon;

  // 作者中心 / 审稿中心：三栏门户布局
  // Vben 会删除「带 children 的一级路由」上的 component（避免多层 BasicLayout），
  // 因此工作台布局必须下沉到二级 path:'' 路由，而不能挂在模块根上。
  const isPortal = mod.slug === 'author' || mod.slug === 'reviewer';
  const portalName = `${pascalCase(mod.slug)}Portal`;
  const portalLayout = `#/views/system/${mod.slug}/portal/layout.vue`;
  const childrenSection = isPortal
    ? `    redirect: '/${mod.slug}/home',
    children: [
      {
        name: ${quote(portalName)},
        path: '',
        component: () => import(${quote(portalLayout)}),
        meta: {
          hideInMenu: true,
        },
        children: [
${childrenBlocks.map((b) => b.replace(/^/gm, '  ')).join('\n')}
        ],
      },
    ],`
    : `    children: [
${childrenBlocks.join('\n')}
    ],`;

  const rootMetaExtra = isPortal
    ? `\n      hideChildrenInMenu: true,`
    : '';

  const content = `import type { RouteRecordRaw } from 'vue-router';

/**
 * ${root.menuName} —— 由 scripts/generate-menu-routes.mjs 根据
 * sql/hmmpcms_menu.json 自动生成，请勿手工修改本文件。
 * 改动请编辑 sql/hmmpcms_menu.json 后重新运行生成脚本。
 */
const routes: RouteRecordRaw[] = [
  {
    name: ${quote(rootName)},
    path: ${quote(`/${mod.slug}`)},
    meta: {
      icon: ${quote(rootIcon)},
      order: ${root.orderNum},
      title: ${quote(root.menuName)},${rootMetaExtra}
    },
${childrenSection}
  },
];

export default routes;
`;

  const outFile = path.join(OUT_DIR, `${mod.slug}.ts`);
  if (pageCount === 0) {
    if (existsSync(outFile)) unlinkSync(outFile);
    console.log(`跳过空模块 ${mod.slug}（无页面）`);
    continue;
  }
  writeFileSync(outFile, content, 'utf8');
  console.log(`已生成 ${outFile} (${pageCount} 个页面)`);
}
