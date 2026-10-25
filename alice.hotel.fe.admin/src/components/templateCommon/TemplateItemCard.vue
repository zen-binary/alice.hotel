<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div>
    <div class="box_section">
      <!-- <h3>Item card</h3> -->
      <div style="margin: 32px 0">
        <div class="search_box card col_3">
          <ul>
            <li v-for="i in itemCards1">
              <ItemCard class="class_test" border-shape="normal" :is-show-icon="true">
                <!--              <template #header>-->
                <!--                <h3>Header</h3>-->
                <!--              </template>-->
                <template #status>
                  <p :data-status="i.status">
                    {{ i.statusContent }}
                  </p>
                </template>
                <template #title>
                  <p class="title_title">
                    {{ i.title }}
                  </p>
                  <p class="title_des">
                    {{ i.description }}
                  </p>
                </template>
                <template #icon>
                  <img :src="i.icon" alt="icon" />
                </template>
                <template #body>
                  <div class="card_custom_content">
                    <p>1</p>
                    <p>1</p>
                    <p>1</p>
                  </div>
                </template>
              </ItemCard>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="box_section">
      <h3>Item card</h3>
      <div style="margin: 32px 0">
        <div class="search_box card col_3">
          <ul>
            <li v-for="i in itemCards.splice(0, 6)">
              <ItemCard class="class_test">
                <!--              <template #header>-->
                <!--                <h3>Header</h3>-->
                <!--              </template>-->
                <template #image>
                  <img :src="i.image" alt="image" />
                </template>
                <template #status>
                  <p :data-status="i.status">
                    {{ i.statusContent }}
                  </p>
                </template>
                <template #title>
                  <p class="title_title">
                    {{ i.title }}
                  </p>
                  <p class="title_des">
                    {{ i.description }}
                  </p>
                </template>
                <template #body>
                  <!--                  <div class="card_custom_content">-->
                  <!--                    <p>1</p>-->
                  <!--                    <p>1</p>-->
                  <!--                    <p>1</p>-->
                  <!--                  </div>-->
                </template>
              </ItemCard>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import mockDataItemCard from "@/data/mockItemCard.json";
import mockDataItemCard1 from "@/data/mockItemCard1.json";
import ItemCard from "@/components/common/ItemCard.vue";
import { ref } from "vue";

const itemCards = ref(mockDataItemCard);
const itemCards1 = ref(mockDataItemCard1);
</script>

<style scoped></style>
