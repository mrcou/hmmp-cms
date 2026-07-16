<script lang="ts" setup>
/**
 * 作者端业务页轻量外壳：标题 + 说明 + 限宽内容区，弱化后台表格感。
 */
defineProps<{
  title: string;
  description?: string;
  /** 是否限制内容最大宽度（表单类页面用） */
  narrow?: boolean;
}>();
</script>

<template>
  <div class="author-page h-full w-full px-3 py-4 md:px-4">
    <header class="mb-4" :class="narrow ? 'max-w-3xl' : 'w-full'">
      <h1 class="text-foreground m-0 text-lg font-semibold tracking-tight">
        {{ title }}
      </h1>
      <p
        v-if="description"
        class="text-muted-foreground mt-1 mb-0 text-sm leading-relaxed"
      >
        {{ description }}
      </p>
    </header>
    <div :class="narrow ? 'max-w-3xl' : 'w-full'">
      <slot />
    </div>
  </div>
</template>
