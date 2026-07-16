/**
 * 开发完成后运行本脚本，把 hmmpcms_menu.json 重新生成为 hmmpcms_menu.sql，
 * 再把生成的 SQL 提交到数据库。
 * 用法：node sql/menu-json-to-sql.js
 */
const fs = require('fs');
const path = require('path');

const JSON_FILE = path.join(__dirname, 'hmmpcms_menu.json');
const SQL_FILE = path.join(__dirname, 'hmmpcms_menu.sql');

const data = JSON.parse(fs.readFileSync(JSON_FILE, 'utf8'));

const COLUMNS = [
  'menu_id', 'menu_name', 'parent_id', 'order_num', 'path', 'component', 'query',
  'route_name', 'is_frame', 'is_cache', 'menu_type', 'visible', 'status', 'perms',
  'icon', 'create_by', 'create_time', 'update_by', 'update_time', 'remark',
];

const CAMEL = {
  menu_id: 'menuId',
  menu_name: 'menuName',
  parent_id: 'parentId',
  order_num: 'orderNum',
  path: 'path',
  component: 'component',
  query: 'query',
  route_name: 'routeName',
  is_frame: 'isFrame',
  is_cache: 'isCache',
  menu_type: 'menuType',
  visible: 'visible',
  status: 'status',
  perms: 'perms',
  icon: 'icon',
  remark: 'remark',
};

function escapeSqlString(str) {
  return String(str).replace(/\\/g, '\\\\').replace(/'/g, "\\'").replace(/"/g, '\\"');
}

function formatValue(col, item) {
  if (col === 'create_by') return `'${data.defaults.createBy}'`;
  if (col === 'create_time') return 'NOW()';
  if (col === 'update_by') return `'${data.defaults.updateBy}'`;
  if (col === 'update_time') return 'NULL';

  const key = CAMEL[col];
  let val = item[key];

  if (col === 'query') {
    if (val === null || val === undefined) return 'NULL';
    return `'${escapeSqlString(JSON.stringify(val))}'`;
  }
  if (val === null || val === undefined) return 'NULL';
  if (typeof val === 'number') return String(val);
  return `'${escapeSqlString(val)}'`;
}

const columnList = COLUMNS.map((c) => `\`${c}\``).join(', ');

const groupBlocks = data.groups.map((g) => {
  const rows = g.items.map((item) => {
    const vals = COLUMNS.map((c) => formatValue(c, item));
    return `(${vals.join(', ')})`;
  });
  const stmt = `INSERT INTO \`sys_menu\` (${columnList}) VALUES\n${rows.join(',\n')};`;
  return g.comment ? `${g.comment}\n${stmt}` : stmt;
});

const out = [data.header, '', groupBlocks.join('\n\n'), '', data.footer].join('\n') + '\n';

fs.writeFileSync(SQL_FILE, out, 'utf8');
console.log(`已生成 ${SQL_FILE}`);
