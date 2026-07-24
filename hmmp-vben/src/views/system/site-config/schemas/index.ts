import type { VbenFormSchema } from '#/adapter/form';

import { useAuthorConfigFormSchema } from './author-config';
import { useCombiningArticlesFormSchema } from './combining-articles';
import { useCostProcessingFormSchema } from './cost-processing';
import { useEditorialConfigFormSchema } from './editorial-config';
import { useExternalInterfaceFormSchema } from './external-interface';
import { useJournalInfoFormSchema } from './journal-info';
import { useMailInfoFormSchema } from './mail-info';
import { useOtherConfigFormSchema } from './other-config';
import { useReaderConfigFormSchema } from './reader-config';
import { useReviewerConfigFormSchema } from './reviewer-config';
import { useTimedTaskFormSchema } from './timed-task';

export {
  useAuthorConfigFormSchema,
  useCombiningArticlesFormSchema,
  useCostProcessingFormSchema,
  useEditorialConfigFormSchema,
  useExternalInterfaceFormSchema,
  useJournalInfoFormSchema,
  useMailInfoFormSchema,
  useOtherConfigFormSchema,
  useReaderConfigFormSchema,
  useReviewerConfigFormSchema,
  useTimedTaskFormSchema,
};

export const SITE_CONFIG_SECTION_SCHEMAS: Record<string, () => VbenFormSchema[]> = {
  'author-config': useAuthorConfigFormSchema,
  'combining-articles': useCombiningArticlesFormSchema,
  'cost-processing': useCostProcessingFormSchema,
  'editorial-config': useEditorialConfigFormSchema,
  'external-interface': useExternalInterfaceFormSchema,
  'journal-info': useJournalInfoFormSchema,
  'mail-info': useMailInfoFormSchema,
  'other-config': useOtherConfigFormSchema,
  'reader-config': useReaderConfigFormSchema,
  'reviewer-config': useReviewerConfigFormSchema,
  'timed-task': useTimedTaskFormSchema,
};
