/**
 * 一次性工具：把 hmmpcms_menu.sql 解析为 hmmpcms_menu.json
 * 用法：node sql/menu-sql-to-json.js
 */
const fs = require('fs');
const path = require('path');

const SQL_FILE = path.join(__dirname, 'hmmpcms_menu.sql');
const JSON_FILE = path.join(__dirname, 'hmmpcms_menu.json');

const text = fs.readFileSync(SQL_FILE, 'utf8');
const lines = text.split(/\r?\n/);

function unescapeSqlString(raw) {
  let out = '';
  for (let i = 0; i < raw.length; i++) {
    const c = raw[i];
    if (c === '\\' && i + 1 < raw.length) {
      out += raw[++i];
    } else {
      out += c;
    }
  }
  return out;
}

// 把一个 "(...)" 元组字符串（不含首尾括号）按顶层逗号拆分为字段数组，并转换为 JS 值
function parseTuple(tupleStr) {
  const fields = [];
  let i = 0;
  const n = tupleStr.length;
  while (i < n) {
    while (i < n && /\s/.test(tupleStr[i])) i++;
    if (i >= n) break;
    if (tupleStr[i] === "'") {
      let j = i + 1;
      let raw = '';
      while (j < n) {
        if (tupleStr[j] === '\\' && j + 1 < n) {
          raw += tupleStr[j] + tupleStr[j + 1];
          j += 2;
          continue;
        }
        if (tupleStr[j] === "'") { j++; break; }
        raw += tupleStr[j];
        j++;
      }
      fields.push({ type: 'string', value: unescapeSqlString(raw) });
      i = j;
      while (i < n && /\s/.test(tupleStr[i])) i++;
      if (i < n && tupleStr[i] === ',') i++;
    } else {
      let j = i;
      while (j < n && tupleStr[j] !== ',') j++;
      const tok = tupleStr.slice(i, j).trim();
      if (tok === 'NULL') fields.push({ type: 'null', value: null });
      else if (tok === 'NOW()') fields.push({ type: 'now', value: 'NOW()' });
      else if (/^-?\d+$/.test(tok)) fields.push({ type: 'number', value: Number(tok) });
      else fields.push({ type: 'raw', value: tok });
      i = j + 1;
    }
  }
  return fields;
}

// 从形如 "(a, b, c)" 的整段 VALUES 主体中，按顶层括号切出各个元组
function splitTuples(valuesBody) {
  const tuples = [];
  let depth = 0;
  let inStr = false;
  let start = -1;
  for (let i = 0; i < valuesBody.length; i++) {
    const c = valuesBody[i];
    if (inStr) {
      if (c === '\\') { i++; continue; }
      if (c === "'") inStr = false;
      continue;
    }
    if (c === "'") { inStr = true; continue; }
    if (c === '(') { if (depth === 0) start = i + 1; depth++; }
    else if (c === ')') {
      depth--;
      if (depth === 0) tuples.push(valuesBody.slice(start, i));
    }
  }
  return tuples;
}

const HEADER_END_MARKER = '-- =============================================';
let headerLines = [];
{
  // 文件开头的第一个 "====" 块视为头部说明
  let idx = 0;
  if (lines[idx] === HEADER_END_MARKER) {
    headerLines.push(lines[idx]);
    idx++;
    while (idx < lines.length && lines[idx] !== HEADER_END_MARKER) {
      headerLines.push(lines[idx]);
      idx++;
    }
    headerLines.push(lines[idx]); // 结束的 ====
  }
}

// 收集所有 INSERT INTO `sys_menu` 语句块及其前置注释
const groups = [];
let i = 0;
let pendingComment = [];
let sawFirstHeader = false;
while (i < lines.length) {
  const line = lines[i];
  if (!sawFirstHeader && line === HEADER_END_MARKER) {
    // 跳过头部块（已单独处理）
    i++;
    while (i < lines.length && lines[i] !== HEADER_END_MARKER) i++;
    i++; // 跳过结束 ====
    sawFirstHeader = true;
    continue;
  }
  if (line.startsWith('INSERT INTO `sys_menu`')) {
    // 收集列名
    const colMatch = line.match(/\(([^)]+)\)\s*VALUES/);
    const columns = colMatch[1].split(',').map((s) => s.trim().replace(/`/g, ''));
    // 收集 VALUES 部分，直到遇到以 ; 结尾的行
    let stmt = line;
    let j = i;
    while (!stmt.trim().endsWith(';')) {
      j++;
      stmt += '\n' + lines[j];
    }
    const valuesStart = stmt.indexOf('VALUES') + 'VALUES'.length;
    let valuesBody = stmt.slice(valuesStart).trim();
    valuesBody = valuesBody.replace(/;\s*$/, '');
    const tupleStrs = splitTuples(valuesBody);
    const rows = tupleStrs.map((t) => {
      const fields = parseTuple(t);
      const obj = {};
      columns.forEach((col, idx) => {
        obj[col] = fields[idx];
      });
      return obj;
    });
    groups.push({
      comment: pendingComment.join('\n'),
      columns,
      rows,
    });
    pendingComment = [];
    i = j + 1;
    continue;
  }
  if (line.trim().startsWith('--')) {
    pendingComment.push(line);
    i++;
    continue;
  }
  if (line.trim() === '') {
    // 空行：如果紧跟着的不是注释/INSERT，且已经开始收集了 footer，则忽略
    i++;
    continue;
  }
  // 其他内容（footer 里的注释性 INSERT 示例等）归入 footer 原样保留
  break;
}
const footerLines = [pendingComment.join('\n'), ...lines.slice(i)].filter((s) => s !== undefined);

function fieldToJson(field, colName) {
  if (!field) return null;
  if (colName === 'create_by' || colName === 'create_time' || colName === 'update_by' || colName === 'update_time') {
    return undefined; // 这些列固定生成，不落 JSON
  }
  if (field.type === 'null') return null;
  if (colName === 'query' && field.type === 'string') {
    if (field.value === '') return null;
    try {
      return JSON.parse(field.value);
    } catch (e) {
      return field.value;
    }
  }
  return field.value;
}

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

const outGroups = groups.map((g) => ({
  comment: g.comment,
  items: g.rows.map((row) => {
    const item = {};
    for (const col of g.columns) {
      if (col === 'create_by' || col === 'create_time' || col === 'update_by' || col === 'update_time') continue;
      const key = CAMEL[col] || col;
      item[key] = fieldToJson(row[col], col);
    }
    return item;
  }),
}));

const result = {
  _readme: '本文件由 sql/hmmpcms_menu.sql 解析生成，作为开发期间的菜单编辑源；改完后运行 node sql/menu-json-to-sql.js 重新生成 sql/hmmpcms_menu.sql 再提交数据库。',
  header: headerLines.join('\n'),
  defaults: { createBy: 'admin', updateBy: '', updateTime: null },
  groups: outGroups,
  footer: footerLines.join('\n').replace(/\n+$/, ''),
};

fs.writeFileSync(JSON_FILE, JSON.stringify(result, null, 2) + '\n', 'utf8');

const totalRows = outGroups.reduce((n, g) => n + g.items.length, 0);
console.log(`解析完成: ${groups.length} 个分组, ${totalRows} 条菜单记录 -> ${JSON_FILE}`);
