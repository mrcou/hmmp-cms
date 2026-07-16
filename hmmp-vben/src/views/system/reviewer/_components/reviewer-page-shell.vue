<script lang="ts" setup>
/**
 * 审稿端业务页轻量外壳：标题 + 说明 + 内容区
 * 可通过 #header 插槽完全自定义顶部区域（用于更紧凑的列表页）
 */
defineProps<{
  title?: string;
  description?: string;
}>();
</script>

<template>
  <div class="reviewer-page h-full w-full px-3 py-4 md:px-4">
    <header v-if="$slots.header || title" class="mb-3 w-full">
      <slot name="header">
        <h1 class="text-foreground m-0 text-lg font-semibold tracking-tight">
          {{ title }}
        </h1>
        <p
          v-if="description"
          class="text-muted-foreground mt-1 mb-0 text-sm leading-relaxed"
        >
          {{ description }}
        </p>
      </slot>
    </header>
    <div class="w-full">
      <slot />
    </div>
  </div>
</template>
