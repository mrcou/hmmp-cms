<script setup lang="ts">
import { useSlots } from 'vue';

const slots = useSlots();
</script>

<template>
  <div class="mb-7 sm:mx-auto sm:w-full sm:max-w-md">
    <h2
      class="text-3xl/9 font-bold tracking-tight text-foreground lg:text-4xl"
      :class="{ 'mb-3': !!slots.desc }"
    >
      <slot></slot>
    </h2>

    <p v-if="slots.desc" class="lg:text-md text-sm">
      <slot name="desc"></slot>
    </p>
  </div>
</template>
