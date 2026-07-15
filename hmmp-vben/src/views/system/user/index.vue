<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemUserApi } from '#/api/system/user';

import { onMounted, ref } from 'vue';

import { Page, Tree, useVbenModal, VbenTableAction } from '@vben/common-ui';
import { IconifyIcon, Plus } from '@vben/icons';

import { Button, Card, message, Modal } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import {
  changeUserStatus,
  deleteUser,
  exportUser,
  getUserDeptTree,
  getUserList,
} from '#/api/system/user';
import { compactSearchFormOptions } from '#/views/_shared/table-search';

import { useColumns, useGridFormSchema } from './data';
import Form from './modules/form.vue';
import ImportModal from './modules/import.vue';
import ResetPassword from './modules/reset-password.vue';

const deptTreeData = ref<SystemUserApi.DeptTreeNode[]>([]);
const selectedDeptId = ref<number>();
const placeholderDeptRootLabels = new Set(['根目录', '部门根目录', '全部部门']);

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnClose: true,
});

const [ResetPasswordModal, resetPasswordModalApi] = useVbenModal({
  connectedComponent: ResetPassword,
  destroyOnClose: true,
});

const [ImportUserModal, importUserModalApi] = useVbenModal({
  connectedComponent: ImportModal,
  destroyOnClose: true,
});

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    ...compactSearchFormOptions,
    schema: useGridFormSchema(),
    submitOnChange: true,
  },
  gridOptions: {
    columns: useColumns(onStatusChange),
    height: 'auto',
    keepSource: true,
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          return await getUserList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            deptId: selectedDeptId.value,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'userId' },
    toolbarConfig: {
      custom: true,
      export: false,
      refresh: true,
      search: true,
      zoom: true,
    },
  } as VxeTableGridOptions<SystemUserApi.SystemUser>,
});

function confirm(content: string, title: string) {
  return new Promise((resolve, reject) => {
    Modal.confirm({
      content,
      onCancel: () => reject(new Error('已取消')),
      onOk: () => resolve(true),
      title,
    });
  });
}

async function onStatusChange(newStatus: string, row: SystemUserApi.SystemUser) {
  const text = newStatus === '0' ? '启用' : '停用';
  try {
    await confirm(`确认要${text}"${row.userName}"用户吗？`, '提示');
    await changeUserStatus(row.userId!, newStatus);
    message.success(`${text}成功`);
    return true;
  } catch {
    return false;
  }
}

function onRefresh() {
  gridApi.query();
}

async function onExport() {
  const formValues = (await gridApi.formApi?.getValues()) ?? {};
  await exportUser({ ...formValues, deptId: selectedDeptId.value });
}

function onImport() {
  importUserModalApi.open();
}

function onCreate() {
  formModalApi.setData({}).open();
}

function onEdit(row: SystemUserApi.SystemUser) {
  formModalApi.setData(row).open();
}

function onResetPassword(row: SystemUserApi.SystemUser) {
  resetPasswordModalApi.setData({ userId: row.userId }).open();
}

function onDelete(row: SystemUserApi.SystemUser) {
  deleteUser(row.userId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}

async function loadDeptTree() {
  const res = await getUserDeptTree();
  deptTreeData.value = normalizeDeptTree(res.data);
}

function selectDept(item: { id: number }) {
  selectedDeptId.value = item.id;
  gridApi.query();
}

function isPlaceholderDeptRoot(node: SystemUserApi.DeptTreeNode) {
  return node.id === 0 || placeholderDeptRootLabels.has(node.label.trim());
}

function normalizeDeptTree(nodes: SystemUserApi.DeptTreeNode[]) {
  if (nodes.length !== 1) {
    return nodes;
  }
  const root = nodes[0];
  if (!root) {
    return nodes;
  }
  return isPlaceholderDeptRoot(root) ? (root.children ?? []) : nodes;
}

function isOrganizationDeptNode(
  node: SystemUserApi.DeptTreeNode,
  level: number,
) {
  return level === 0 || node.label === 'hmmp科技';
}

onMounted(() => {
  loadDeptTree();
});
</script>

<template>
  <Page auto-content-height>
    <FormModal @success="onRefresh" />
    <ResetPasswordModal @success="onRefresh" />
    <ImportUserModal @success="onRefresh" />
    <div class="flex size-full gap-4">
      <Card class="w-1/5" title="部门">
        <Tree
          :tree-data="deptTreeData"
          label-field="label"
          value-field="id"
          children-field="children"
          :show-switcher="false"
          :default-expanded-level="2"
          @select="selectDept"
        >
          <template #node="{ value, level, hasChildren, toggleNode }">
            <IconifyIcon
              v-if="isOrganizationDeptNode(value, level)"
              icon="lucide:network"
              class="size-4 shrink-0 text-foreground/70"
              :class="{ 'cursor-pointer hover:text-foreground': hasChildren }"
              @click="
                (event: MouseEvent) => {
                  if (hasChildren) {
                    event.stopPropagation();
                    toggleNode();
                  }
                }
              "
            />
            <svg
              v-else
              class="size-4 shrink-0 text-foreground/70"
              :class="{ 'cursor-pointer hover:text-foreground': hasChildren }"
              viewBox="0 0 16 16"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              @click="
                (event: MouseEvent) => {
                  if (hasChildren) {
                    event.stopPropagation();
                    toggleNode();
                  }
                }
              "
            >
              <path
                d="M5 3V11H12"
                stroke="currentColor"
                stroke-width="1.7"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
            <span class="truncate">{{ value.label }}</span>
          </template>
        </Tree>
      </Card>
      <div class="w-4/5">
        <Grid table-title="用户列表">
          <template #toolbar-tools>
            <Button class="mr-2" @click="onImport">导入</Button>
            <Button class="mr-2" @click="onExport">导出</Button>
            <Button type="primary" @click="onCreate">
              <Plus class="size-5" />
              新增用户
            </Button>
          </template>
          <template #action="{ row }">
            <VbenTableAction
              :actions="[
                { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
                {
                  text: '重置密码',
                  icon: 'lucide:key-round',
                  onClick: () => onResetPassword(row),
                },
              ]"
              :dropdown-actions="[
                {
                  text: '删除',
                  icon: 'lucide:trash-2',
                  danger: true,
                  popConfirm: {
                    title: `确认删除用户'${row.userName}'吗？`,
                    confirm: () => onDelete(row),
                  },
                },
              ]"
            />
          </template>
        </Grid>
      </div>
    </div>
  </Page>
</template>
