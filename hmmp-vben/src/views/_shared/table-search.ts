import type { VbenFormProps } from '#/adapter/form';

export const compactSearchFormOptions = {
  actionLayout: 'rowEnd',
  actionPosition: 'right',
  actionWrapperClass: 'gap-2 whitespace-nowrap',
  commonConfig: {
    labelWidth: 72,
  },
  wrapperClass: 'grid-cols-1 sm:grid-cols-2 lg:grid-cols-4',
} satisfies Partial<VbenFormProps>;
