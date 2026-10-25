<template>
  <div class="pms-page-header">
    <nav v-if="crumbs.length" class="crumbs">
      <span v-for="(c, i) in crumbs" :key="i">
        <router-link v-if="c.to && i < crumbs.length - 1" :to="c.to">{{ c.label }}</router-link>
        <span v-else :class="{ current: i === crumbs.length - 1 }">{{ c.label }}</span>
        <span v-if="i < crumbs.length - 1" class="sep">/</span>
      </span>
    </nav>
    <div class="title-row">
      <div>
        <p v-if="kicker" class="kicker">{{ kicker }}</p>
        <h1>{{ title }}</h1>
        <p v-if="subtitle" class="sub">{{ subtitle }}</p>
      </div>
      <div v-if="$slots.actions" class="actions">
        <slot name="actions" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  title: string;
  subtitle?: string;
  kicker?: string;
  crumbs?: { label: string; to?: string }[];
}>();
</script>

<style scoped>
.pms-page-header { margin-bottom: 16px; }
.crumbs { font-size: 12px; color: #5b6b76; margin-bottom: 8px; }
.crumbs a { color: #0098e5; text-decoration: none; }
.crumbs .sep { margin: 0 6px; opacity: 0.5; }
.crumbs .current { color: #0f1c24; font-weight: 600; }
.title-row { display: flex; justify-content: space-between; gap: 16px; flex-wrap: wrap; align-items: flex-start; }
.kicker {
  margin: 0;
  font-size: 11px;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  color: #5b6b76;
}
h1 {
  margin: 2px 0 4px;
  font-size: 1.65rem;
  color: #0f1c24;
  letter-spacing: -0.02em;
  font-weight: 700;
}
.sub { margin: 0; color: #5b6b76; font-size: 13px; }
.actions { display: flex; gap: 8px; flex-wrap: wrap; align-items: center; }
</style>
