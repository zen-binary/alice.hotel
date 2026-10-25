<template>
  <PageContainer class="home_page">
    <div class="home_page__content">
      <h1>Alice Hotel</h1>
      <p>Website đặt phòng khách sạn Alice — client base sẵn sàng cho module booking mới.</p>
    </div>
  </PageContainer>
</template>

<script setup lang="ts">
import PageContainer from "@/components/common/PageContainer.vue";
</script>

<style scoped lang="scss">
.home_page {
  &__content {
    padding: 4rem 2rem;
    max-width: 720px;

    h1 {
      font-size: 2.4rem;
      font-weight: 500;
      margin-bottom: 1.6rem;
    }

    p {
      font-size: 1.6rem;
      line-height: 2.4rem;
      color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
    }
  }
}
</style>
