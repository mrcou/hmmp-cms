/**
 * 从 _forms_extracted.json 生成站点配置表单 schema
 * 运行: node scripts/generate-site-config-schemas.mjs
 */
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const rootDir = path.resolve(__dirname, '../../..');
const inputPath = path.join(rootDir, '_forms_extracted.json');
const outputDir = path.join(
  __dirname,
  '../src/views/system/site-config/schemas',
);

const LEAF_TO_FORM = {
  'journal-info': 'JournalInfoForm',
  'mail-info': 'MailInfoForm',
  'timed-task': 'TimedTaskForm',
  'cost-processing': 'CostProcessingForm',
  'author-config': 'AuthorsRelatedForm',
  'editorial-config': 'EditorialParameterForm',
  'reviewer-config': 'ReviewerParameterForm',
  'reader-config': 'ReaderParameterForm',
  'external-interface': 'ExternalInterfaceParameterForm',
  'combining-articles': 'CombiningArticlesParameterForm',
  'other-config': 'OtherParameterForm',
};

const TYPE_MAP = {
  input: 'Input',
  number: 'InputNumber',
  select: 'Select',
  textarea: 'Textarea',
  password: 'InputPassword',
  radio: 'RadioGroup',
  checkbox: 'Checkbox',
};

function escapeStr(str) {
  return str.replace(/\\/g, '\\\\').replace(/'/g, "\\'");
}

function buildFieldSchema(field) {
  const component = TYPE_MAP[field.type] ?? 'Input';
  const lines = [
    `    {`,
    `      component: '${component}',`,
    `      fieldName: '${field.fieldName}',`,
    `      label: '${escapeStr(field.label)}',`,
  ];

  if (field.helpText) {
    lines.push(`      help: '${escapeStr(field.helpText)}',`);
  }

  if (field.defaultValue != null && field.defaultValue !== '') {
    const val =
      typeof field.defaultValue === 'string'
        ? `'${escapeStr(String(field.defaultValue))}'`
        : field.defaultValue;
    lines.push(`      defaultValue: ${val},`);
  }

  const props = {};
  if (component === 'InputNumber') {
    props.class = 'w-full';
    props.min = 0;
  }
  if (component === 'Select' || component === 'RadioGroup') {
    if (field.options?.length) {
      const allSameValue =
        component === 'RadioGroup' &&
        field.options.every((opt) => opt.value === field.options[0].value);
      props.options = field.options.map((opt) => ({
        label: opt.label,
        value: allSameValue && opt.id ? opt.id : opt.value,
      }));
    }
  }
  if (component === 'Select') {
    props.class = 'site-config-select';
    props.style = { width: '100%' };
    if (field.options?.some((o) => (o.label?.length ?? 0) > 12)) {
      props.classNames = { popup: { root: 'site-config-select-popup' } };
      props.popupMatchSelectWidth = false;
    }
  }
  if (component === 'RadioGroup') {
    props.optionType = 'button';
  }

  if (Object.keys(props).length > 0) {
    lines.push(`      componentProps: ${JSON.stringify(props, null, 8).replace(/\n/g, '\n      ')},`);
  }

  lines.push(`    },`);
  return lines.join('\n');
}

function buildSchemaFunction(leaf, form) {
  const seenSections = new Set();
  const schemaItems = [];

  for (const field of form.fields) {
    if (field.section && !seenSections.has(field.section)) {
      seenSections.add(field.section);
      const dividerName = field.section.replace(/[^\w\u4e00-\u9fff]/g, '_');
      schemaItems.push(`    {
      component: 'Divider',
      fieldName: 'divider_${dividerName}',
      formItemClass: 'col-span-full',
      label: '${escapeStr(field.section)}',
    },`);
    }
    schemaItems.push(buildFieldSchema(field));
  }

  const fnName = `use${leaf
    .split('-')
    .map((s) => s.charAt(0).toUpperCase() + s.slice(1))
    .join('')}FormSchema`;

  return `export function ${fnName}(): VbenFormSchema[] {
  return [
${schemaItems.join('\n')}
  ];
}`;
}

function main() {
  const raw = fs.readFileSync(inputPath, 'utf8');
  const forms = JSON.parse(raw);

  fs.mkdirSync(outputDir, { recursive: true });

  const exports = [];
  for (const [leaf, formKey] of Object.entries(LEAF_TO_FORM)) {
    const form = forms[formKey];
    if (!form) {
      console.warn(`Form not found: ${formKey}`);
      continue;
    }
    const fnName = `use${leaf
      .split('-')
      .map((s) => s.charAt(0).toUpperCase() + s.slice(1))
      .join('')}FormSchema`;
    const content = `import type { VbenFormSchema } from '#/adapter/form';

${buildSchemaFunction(leaf, form)}
`;
    fs.writeFileSync(path.join(outputDir, `${leaf}.ts`), content, 'utf8');
    exports.push(`export { ${fnName} } from './${leaf}';`);
    console.log(`Generated ${leaf}.ts (${form.fieldCount} fields)`);
  }

  fs.writeFileSync(
    path.join(outputDir, 'index.ts'),
    `import type { VbenFormSchema } from '#/adapter/form';

${Object.keys(LEAF_TO_FORM)
  .map((leaf) => {
    const fnName = `use${leaf
      .split('-')
      .map((s) => s.charAt(0).toUpperCase() + s.slice(1))
      .join('')}FormSchema`;
    return `import { ${fnName} } from './${leaf}';`;
  })
  .join('\n')}

export {
${Object.keys(LEAF_TO_FORM)
  .map((leaf) => {
    const fnName = `use${leaf
      .split('-')
      .map((s) => s.charAt(0).toUpperCase() + s.slice(1))
      .join('')}FormSchema`;
    return `  ${fnName},`;
  })
  .join('\n')}
};

export const SITE_CONFIG_SECTION_SCHEMAS: Record<string, () => VbenFormSchema[]> = {
${Object.keys(LEAF_TO_FORM)
  .map((leaf) => {
    const fnName = `use${leaf
      .split('-')
      .map((s) => s.charAt(0).toUpperCase() + s.slice(1))
      .join('')}FormSchema`;
    return `  '${leaf}': ${fnName},`;
  })
  .join('\n')}
};
`,
    'utf8',
  );
  console.log('Done.');
}

main();
