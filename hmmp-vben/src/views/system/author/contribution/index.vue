<script lang="ts" setup>
import { computed, nextTick, onActivated, ref } from 'vue';
import { useRouter } from 'vue-router';

import { IconifyIcon } from '@vben/icons';

import { message } from 'antdv-next';
import {
  Alert,
  Button,
  Card,
  Form,
  FormItem,
  Input,
  Modal,
  Space,
  Steps,
  Upload,
} from 'antdv-next';

import * as authorApi from '#/api/biz/author';

import AuthorPageShell from '../_components/author-page-shell.vue';
import {
  createEmptyManuscriptInfo,
  type ManuscriptInfoForm,
} from './modules/manuscript-info';
import AuthorInfoStep from './modules/author-info-step.vue';
import ManuscriptInfoStep from './modules/manuscript-info-step.vue';

defineOptions({ name: 'AuthorContribution' });

interface AttachmentItem {
  id: string;
  /** 预置类型，便于后续落库 */
  type: string;
  description: string;
  path: string;
  fileName: string;
  required: boolean;
  removable: boolean;
}

const router = useRouter();
const infoStepRef = ref<InstanceType<typeof ManuscriptInfoStep>>();
const authorStepRef = ref<InstanceType<typeof AuthorInfoStep>>();
const loading = ref(false);
const currentStep = ref(0);

/** 是否已阅读并同意投稿须知 */
const noticeAccepted = ref(false);
const noticeVisible = ref(true);
const noticeReadDone = ref(false);
const noticeBodyRef = ref<HTMLElement | null>(null);

const manuscriptPath = ref('');
const manuscriptFileName = ref('');
const manuscriptInfo = ref<ManuscriptInfoForm>(createEmptyManuscriptInfo());

let attachmentSeq = 0;
function createAttachment(
  partial: Omit<AttachmentItem, 'id' | 'path' | 'fileName'> &
    Partial<Pick<AttachmentItem, 'path' | 'fileName'>>,
): AttachmentItem {
  attachmentSeq += 1;
  return {
    id: `att-${attachmentSeq}`,
    path: '',
    fileName: '',
    ...partial,
  };
}

function createDefaultAttachments(): AttachmentItem[] {
  return [
    createAttachment({
      type: 'copyright',
      description: '版权转让协议',
      required: true,
      removable: false,
    }),
    createAttachment({
      type: 'letter',
      description: '单位介绍信',
      required: true,
      removable: false,
    }),
    createAttachment({
      type: 'cnki',
      description: '查重报告',
      required: true,
      removable: false,
    }),
    createAttachment({
      type: 'ethics',
      description: '伦理证明审查及备案',
      required: false,
      removable: true,
    }),
    createAttachment({
      type: 'fund',
      description: '基金项目/课题证明资料',
      required: false,
      removable: true,
    }),
    createAttachment({
      type: 'custom',
      description: '',
      required: false,
      removable: true,
    }),
  ];
}

const attachments = ref<AttachmentItem[]>(createDefaultAttachments());

const stepItems = [
  { title: '上传文件' },
  { title: '填写稿件信息' },
  { title: '发布机构及联系人' },
];

const noticeSections = [
  {
    title: '一、投稿范围与要求',
    paragraphs: [
      '本刊欢迎符合办刊宗旨、具有学术价值或实践意义的原创稿件。稿件应符合学术规范，论点明确、资料可靠、文字规范。',
      '请勿一稿多投。稿件一经录用，版权归本刊所有；如需转载或二次发表，须经本刊书面同意。',
      '投稿前请确认稿件尚未在其他公开出版物或预印本平台以正式版本发表；如有相关说明，请在投稿时如实告知编辑部。',
    ],
  },
  {
    title: '二、稿件格式与材料',
    paragraphs: [
      '请提交完整稿件文件，推荐格式为 PDF、DOC 或 DOCX，单个文件大小不超过 20MB。',
      '除正文外，通常还需上传版权转让协议、单位介绍信、查重报告等材料；如有伦理证明、基金证明等可一并作为附件提交。',
      '请勿在「附件」区域重复上传稿件全文，附件仅用于补充材料（如图片、成果说明、个人简历等）。',
    ],
  },
  {
    title: '三、作者信息与伦理声明',
    paragraphs: [
      '请确保通讯作者姓名、单位、邮箱、电话等信息准确完整，便于编辑部联系及后续流程沟通。',
      '所有署名作者应对稿件内容负责，并同意投稿。涉及人类受试者、动物实验或敏感数据的研究，须符合相关伦理规范并在文中说明。',
      '请如实声明利益冲突、基金资助等信息。存在潜在利益冲突时，须在投稿说明中主动披露。',
    ],
  },
  {
    title: '四、审稿与修改流程',
    paragraphs: [
      '稿件提交后，编辑部将进行形式审查与学术初审，并通过后送外审。审稿周期因稿件类型与审稿安排而异，请耐心等待并关注系统通知。',
      '如收到退修意见，请在规定时间内完成修改并重新上传，逾期可能导致流程中断或退稿处理。',
      '作者可在「稿件查询」中查看当前状态；如有疑问，可通过「联系编审」与编辑部沟通。',
    ],
  },
  {
    title: '五、其他说明',
    paragraphs: [
      '投稿即视为作者已阅读并同意本须知及本刊相关版权、保密与学术诚信规定。',
      '编辑部有权对录用稿件进行文字润色与版式调整，但不改变作者的学术观点与核心结论。',
      '请向下滚动阅读完整内容。阅读至文末后，「下一步」按钮方可启用。',
    ],
  },
];

const filledAttachmentCount = computed(
  () => attachments.value.filter((item) => item.path).length,
);

function checkNoticeScrollComplete() {
  const el = noticeBodyRef.value;
  if (!el) return;
  const reachedBottom = el.scrollHeight - el.scrollTop - el.clientHeight <= 8;
  if (reachedBottom) {
    noticeReadDone.value = true;
  }
}

function onNoticeScroll() {
  checkNoticeScrollComplete();
}

async function openNoticeModal() {
  noticeAccepted.value = false;
  noticeReadDone.value = false;
  noticeVisible.value = true;
  currentStep.value = 0;
  await nextTick();
  const el = noticeBodyRef.value;
  if (el && el.scrollHeight <= el.clientHeight + 2) {
    noticeReadDone.value = true;
  }
}

function onNoticeCancel() {
  noticeVisible.value = false;
  router.push('/author/home');
}

function onNoticeNext() {
  if (!noticeReadDone.value) {
    message.warning('请先阅读完整投稿须知');
    return;
  }
  noticeAccepted.value = true;
  noticeVisible.value = false;
  currentStep.value = 0;
}

function resetUploadState() {
  manuscriptPath.value = '';
  manuscriptFileName.value = '';
  attachments.value = createDefaultAttachments();
  manuscriptInfo.value = createEmptyManuscriptInfo();
  infoStepRef.value?.resetFields?.();
}

function resolveUploadPath(res: any) {
  return (
    res?.fileName ??
    res?.data?.fileName ??
    res?.url ??
    res?.data?.url ??
    ''
  );
}

function validateFile(file: File) {
  const isLt20M = file.size / 1024 / 1024 <= 20;
  if (!isLt20M) {
    message.error('文件大小不能超过 20MB');
    return false;
  }
  return true;
}

function uploadManuscript(file: File) {
  if (!validateFile(file)) {
    return Upload.LIST_IGNORE;
  }
  void (async () => {
    try {
      const res = (await authorApi.uploadAuthorFile(file)) as any;
      const path = resolveUploadPath(res);
      if (!path) {
        message.warning('上传成功，但未返回文件路径');
        return;
      }
      manuscriptPath.value = path;
      manuscriptFileName.value = file.name;
      message.success('稿件上传成功');
    } catch {
      message.error('稿件上传失败');
    }
  })();
  return Upload.LIST_IGNORE;
}

function uploadAttachment(item: AttachmentItem) {
  return (file: File) => {
    if (!validateFile(file)) {
      return Upload.LIST_IGNORE;
    }
    void (async () => {
      try {
        const res = (await authorApi.uploadAuthorFile(file)) as any;
        const path = resolveUploadPath(res);
        if (!path) {
          message.warning('上传成功，但未返回文件路径');
          return;
        }
        item.path = path;
        item.fileName = file.name;
        message.success('附件上传成功');
      } catch {
        message.error('附件上传失败');
      }
    })();
    return Upload.LIST_IGNORE;
  };
}

function addAttachment() {
  attachments.value.push(
    createAttachment({
      type: 'custom',
      description: '',
      required: false,
      removable: true,
    }),
  );
}

function removeAttachment(id: string) {
  attachments.value = attachments.value.filter((item) => item.id !== id);
}

function clearAttachmentFile(item: AttachmentItem) {
  item.path = '';
  item.fileName = '';
}

function validateStepUpload() {
  if (!manuscriptPath.value) {
    message.warning('请上传稿件正文');
    return false;
  }
  for (const item of attachments.value) {
    if (item.required && !item.path) {
      message.warning(`请上传必填附件：${item.description}`);
      return false;
    }
    if (item.path && !item.description.trim()) {
      message.warning('已上传文件的附件请填写附件描述');
      return false;
    }
  }
  return true;
}

function goPrev() {
  if (currentStep.value <= 0) {
    openNoticeModal();
    return;
  }
  currentStep.value -= 1;
}

async function goNext() {
  if (currentStep.value === 0) {
    if (!validateStepUpload()) return;
    currentStep.value = 1;
    return;
  }

  if (currentStep.value === 1) {
    try {
      await infoStepRef.value?.validate();
      currentStep.value = 2;
    } catch (error: any) {
      if (error?.message) message.warning(error.message);
    }
  }
}

async function onSubmit() {
  try {
    await infoStepRef.value?.validate();
    await authorStepRef.value?.validate();
  } catch (error: any) {
    if (error?.message) {
      message.warning(error.message);
    }
    return;
  }
  if (!validateStepUpload()) {
    currentStep.value = 0;
    return;
  }

  loading.value = true;
  try {
    const info = manuscriptInfo.value;
    const payload: authorApi.AuthorApi.Submission = {
      title: info.title,
      abstractText: info.abstractText,
      keywords: info.keywords.join(' '),
      filePath: manuscriptPath.value,
      remark: info.remark,
      enTitle: info.enTitle,
      enKeywords: info.enKeywords.join(' '),
      enAbstract: info.enAbstract,
      fundProject: info.fundProject,
      enFundProject: info.enFundProject,
      studyClassCode: info.studyClassCode,
      clcNumber: info.clcNumber,
      pageNum: info.pageNum ?? undefined,
      authors: info.authors.map((a, index) => ({
        sequence: index + 1,
        isContactAuthor: a.isContactAuthor,
        isFirstAuthor: index === 0 || a.isFirstAuthor,
        realName: a.realName,
        pinYin: a.pinYin,
        sex: a.sex,
        birthday: a.birthday,
        unit: a.unit,
        enUnit: a.enUnit,
        address: a.address,
        postcode: a.postcode,
        country: a.country,
        province: a.province,
        major: a.major,
        education: a.education,
        title: a.title,
        tel: a.tel,
        mobile: a.mobile,
        email: a.email,
        resume: a.resume,
      })),
      attachments: attachments.value
        .filter((item) => item.path)
        .map((item) => ({
          type: item.type,
          label: item.description.trim() || item.fileName,
          path: item.path,
          required: item.required,
        })),
    };

    await authorApi.submitNewArticle(payload);
    message.success('投稿成功');
    resetUploadState();
    currentStep.value = 0;
    openNoticeModal();
  } catch {
    message.error('投稿失败');
  } finally {
    loading.value = false;
  }
}

// keepAlive 下每次进入「提交新稿」都重新弹出须知
onActivated(() => {
  openNoticeModal();
});
</script>

<template>
  <AuthorPageShell
    title="提交新稿"
    description="按步骤上传稿件与附件并填写基本信息，提交后可在「稿件查询」中跟踪进度。"
  >
    <template v-if="noticeAccepted">
      <Card :bordered="false" class="shadow-sm">
        <div class="step-heading">
          <h2 class="m-0 text-base font-semibold">
            {{
              currentStep === 0
                ? '1. 上传文件'
                : currentStep === 1
                  ? '2. 填写稿件信息'
                  : '3. 发布机构及联系人'
            }}
          </h2>
        </div>

        <Steps
          class="contribution-steps"
          size="small"
          :current="currentStep"
          :items="stepItems"
        />

        <!-- 步骤 1：上传文件 -->
        <div v-show="currentStep === 0" class="upload-step">
          <Form layout="vertical" class="max-w-4xl">
            <FormItem required>
              <template #label>
                <span>请上传稿件</span>
              </template>
              <div class="flex flex-wrap items-center gap-2">
                <Input
                  class="min-w-[240px] flex-1"
                  :value="manuscriptFileName"
                  readonly
                  placeholder="请在此处上传投稿文件"
                />
                <Upload
                  :before-upload="uploadManuscript"
                  :show-upload-list="false"
                  :max-count="1"
                >
                  <Button>
                    <IconifyIcon
                      icon="lucide:folder-open"
                      class="mr-1 size-3.5"
                    />
                    浏览...
                  </Button>
                </Upload>
                <Button
                  v-if="manuscriptPath"
                  type="link"
                  danger
                  @click="
                    manuscriptPath = '';
                    manuscriptFileName = '';
                  "
                >
                  清除
                </Button>
              </div>
            </FormItem>
          </Form>

          <Alert
            class="mb-4 max-w-4xl"
            type="info"
            show-icon
            message="提示：如果您有其他与本稿相关的附件，请在下面添加附件（如：图片、成果说明、个人简历等，请不要在此上传稿件的全文）"
          />

          <div class="text-muted-foreground mb-2 max-w-4xl text-xs">
            已上传附件 {{ filledAttachmentCount }} / {{ attachments.length }}
          </div>

          <div class="attachment-list max-w-4xl space-y-3">
            <div
              v-for="item in attachments"
              :key="item.id"
              class="attachment-row"
            >
              <div class="attachment-desc">
                <span
                  class="attachment-label"
                  :class="{ required: item.required }"
                >
                  附件描述
                </span>
                <Input
                  v-model:value="item.description"
                  :readonly="!item.removable && item.required"
                  :placeholder="item.removable ? '附件文件名' : '附件描述'"
                  :maxlength="100"
                />
              </div>
              <div class="attachment-file">
                <Input
                  :value="item.fileName || item.path"
                  readonly
                  placeholder="请选择附件文件"
                />
                <Upload
                  :before-upload="uploadAttachment(item)"
                  :show-upload-list="false"
                  :max-count="1"
                >
                  <Button>浏览...</Button>
                </Upload>
                <Button
                  v-if="item.path && !item.removable"
                  type="link"
                  danger
                  @click="clearAttachmentFile(item)"
                >
                  清除
                </Button>
                <Button
                  v-if="item.removable"
                  type="link"
                  danger
                  @click="removeAttachment(item.id)"
                >
                  删除
                </Button>
              </div>
            </div>
          </div>
        </div>

        <!-- 步骤 2：填写稿件信息 -->
        <div v-show="currentStep === 1" class="info-step">
          <ManuscriptInfoStep
            ref="infoStepRef"
            v-model="manuscriptInfo"
            :manuscript-path="manuscriptPath"
          />
        </div>

        <!-- 步骤 3：发布机构及联系人 -->
        <div v-show="currentStep === 2" class="author-step">
          <AuthorInfoStep ref="authorStepRef" v-model="manuscriptInfo" />
        </div>

        <div class="mt-8 flex flex-col items-center gap-3">
          <Button
            v-if="currentStep === 0"
            type="primary"
            ghost
            @click="addAttachment"
          >
            <IconifyIcon icon="lucide:plus" class="mr-1 size-3.5" />
            添加附件...
          </Button>
          <div class="flex justify-center gap-3">
            <Button @click="goPrev">上一步</Button>
            <Button
              v-if="currentStep < stepItems.length - 1"
              type="primary"
              @click="goNext"
            >
              下一步
            </Button>
            <Button v-else type="primary" :loading="loading" @click="onSubmit">
              提交投稿
            </Button>
          </div>
        </div>
      </Card>
    </template>

    <Card v-else :bordered="false" class="shadow-sm">
      <div class="text-muted-foreground py-10 text-center text-sm">
        请先阅读并确认投稿须知后，再填写投稿信息。
      </div>
    </Card>

    <Modal
      v-model:open="noticeVisible"
      title="投稿须知"
      width="720px"
      :mask-closable="false"
      :keyboard="false"
      :closable="false"
      destroy-on-close
      @cancel="onNoticeCancel"
    >
      <div ref="noticeBodyRef" class="notice-body" @scroll="onNoticeScroll">
        <p class="notice-lead">
          欢迎投稿。为保障审稿流程顺利、维护学术规范，请仔细阅读以下须知。须滚动阅读至文末后，方可进入下一步。
        </p>
        <section
          v-for="(section, index) in noticeSections"
          :key="index"
          class="notice-section"
        >
          <h4>{{ section.title }}</h4>
          <p v-for="(p, pIndex) in section.paragraphs" :key="pIndex">
            {{ p }}
          </p>
        </section>
        <div class="notice-end">— 全文结束，感谢您的阅读 —</div>
      </div>

      <template #footer>
        <div class="flex items-center justify-between gap-3">
          <span class="text-muted-foreground text-xs">
            {{
              noticeReadDone
                ? '已阅读完毕，可进入下一步'
                : '请滚动阅读至文末'
            }}
          </span>
          <Space>
            <Button @click="onNoticeCancel">取消</Button>
            <Button
              type="primary"
              :disabled="!noticeReadDone"
              @click="onNoticeNext"
            >
              下一步
            </Button>
          </Space>
        </div>
      </template>
    </Modal>
  </AuthorPageShell>
</template>

<style scoped>
.step-heading {
  margin-bottom: 15px;
}

.contribution-steps {
  margin-bottom: 30px;
}

.attachment-row {
  display: grid;
  grid-template-columns: minmax(180px, 1fr) minmax(280px, 1.4fr);
  gap: 0.75rem 1rem;
  align-items: end;
  padding: 0.75rem 1rem;
  border: 1px solid hsl(var(--border));
  border-radius: 0.5rem;
  background: hsl(var(--card));
}

.attachment-label {
  display: inline-block;
  margin-bottom: 0.375rem;
  color: hsl(var(--muted-foreground));
  font-size: 0.75rem;
}

.attachment-label.required::before {
  margin-right: 0.25rem;
  color: hsl(var(--destructive));
  content: '*';
}

.attachment-file {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  align-items: center;
}

.attachment-file > :first-child {
  flex: 1 1 160px;
  min-width: 140px;
}

.notice-body {
  max-height: min(56vh, 480px);
  overflow-y: auto;
  padding-right: 0.5rem;
  line-height: 1.7;
}

.notice-lead {
  margin: 0 0 1rem;
  color: hsl(var(--muted-foreground));
  font-size: 0.875rem;
}

.notice-section {
  margin-bottom: 1.25rem;
}

.notice-section h4 {
  margin: 0 0 0.5rem;
  font-size: 0.9375rem;
  font-weight: 600;
}

.notice-section p {
  margin: 0 0 0.5rem;
  font-size: 0.875rem;
  color: hsl(var(--foreground) / 88%);
}

.notice-end {
  margin-top: 0.5rem;
  padding: 0.75rem 0 0.25rem;
  border-top: 1px dashed hsl(var(--border));
  color: hsl(var(--muted-foreground));
  font-size: 0.75rem;
  text-align: center;
}

@media (max-width: 768px) {
  .attachment-row {
    grid-template-columns: 1fr;
  }
}
</style>
