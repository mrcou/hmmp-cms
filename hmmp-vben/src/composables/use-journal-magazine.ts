import { computed, onMounted, ref } from 'vue';

import { preferences } from '@vben/preferences';

import { getSectionConfig } from '#/api/system/site-config';

const DEFAULT_JOURNAL_CODE = 'ddhl';

/** 站群「期刊设置」分区 */
const JOURNAL_BASIC_SECTION = 'journal-basic';
/** 站点「编辑部信息」分区（杂志名简写，兼容旧编号来源） */
const JOURNAL_INFO_SECTION = 'journal-info';

export const JOURNAL_NAME_LABEL = '期刊名称';
export const JOURNAL_CODE_LABEL = '期刊编号';

export type JournalMagazineOptionValue = 'default' | 'journalCode';

export interface UseJournalMagazineOptions {
  /** 下拉 value：发行等业务用 journalCode，统计接口用 default */
  optionValue?: JournalMagazineOptionValue;
}

/**
 * 期刊名称/编号与站群「期刊设置」对齐：
 * - journal_name → 期刊名称
 * - journal_code → 期刊编号（优先；无则回退 JournalSimpleName）
 */
export function useJournalMagazine(options: UseJournalMagazineOptions = {}) {
  const { optionValue = 'journalCode' } = options;
  const journalName = ref('');
  const journalCode = ref(DEFAULT_JOURNAL_CODE);
  const loading = ref(false);

  const magazineOptions = computed(() => [
    {
      value:
        optionValue === 'default' ? 'default' : journalCode.value,
      label:
        journalName.value ||
        preferences.app.name ||
        JOURNAL_NAME_LABEL,
    },
  ]);

  async function loadJournalMagazine() {
    loading.value = true;
    try {
      const [basicRes, infoRes] = await Promise.all([
        getSectionConfig(JOURNAL_BASIC_SECTION),
        getSectionConfig(JOURNAL_INFO_SECTION),
      ]);
      const name = String(basicRes.data?.journal_name ?? '').trim();
      const codeFromBasic = String(basicRes.data?.journal_code ?? '').trim();
      const codeFromInfo = String(infoRes.data?.JournalSimpleName ?? '').trim();
      if (name) {
        journalName.value = name;
      }
      if (codeFromBasic) {
        journalCode.value = codeFromBasic;
      } else if (codeFromInfo) {
        journalCode.value = codeFromInfo;
      }
    } catch {
      // 配置未加载时保留默认值
    } finally {
      loading.value = false;
    }
  }

  onMounted(loadJournalMagazine);

  return {
    journalCode,
    journalName,
    magazineOptions,
    loading,
    loadJournalMagazine,
  };
}
