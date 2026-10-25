<template>
  <div class="page_container">
    <h1>Data Fetch Demo</h1>

    <p>List Data</p>
    <ul>
      <li v-for="item in listData" :key="item?.id">
        <p>{{ item?.id }}</p>
        <p>{{ item?.title }}</p>
        <p>{{ item?.body }}</p>
      </li>
    </ul>

    <div class="pagination">
      <button :disabled="currentPage <= 1" @click="currentPage -= 1">Previous</button>
      <span>Page {{ currentPage }}</span>
      <button :disabled="listData.length < 3" @click="currentPage += 1">Next</button>
    </div>

    <p>Data Id 1</p>
    <p>{{ dataId?.id }}</p>
    <p>{{ dataId?.title }}</p>
    <p>{{ dataId?.body }}</p>

    <div class="pagination">
      <button @click="fetchDataId">Call data id 1</button>
    </div>

    <p>List Comment Id 1</p>
    <ul>
      <li v-for="item in listComment" :key="item?.id">
        <p>{{ item?.id }}</p>
        <p>{{ item?.name }}</p>
        <p>{{ item?.email }}</p>
        <p>{{ item?.body }}</p>
      </li>
    </ul>
    <div class="pagination">
      <button @click="fetchListComment">Call data comment post id 1</button>
    </div>
    <div class="pagination">
      <button @click="changePost">Change post</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { codeMngStore } from "@/stores/common/codeMng";
import { demoStore } from "@/stores/common/demoFetchData";
import { onBeforeMount, ref, watch } from "vue";
import { PostType, PostType1 } from "@/stores/common/demoFetchData/demo.type";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";

const store = demoStore();
const listData = ref<PostType[]>([]);
const listComment = ref<PostType1[]>([]);
const dataId = ref<PostType>();
const currentPage = ref(1);

const fetchData = async (page: number) => {
  currentPage.value = page;
  await store.fetchList({
    method: "GET",
    params: {
      _page: page,
      _limit: 3,
    },
  });
  listData.value = store.list;
};

const fetchDataId = async () => {
  await store.fetchId(1, {
    method: "GET",
  });
  dataId.value = store.dataId;
};

const fetchListComment = async () => {
  await store.fetchListComment({
    method: "GET",
    params: {
      postId: 1,
    },
  });
  listComment.value = store.listComment;
};

const changePost = async () => {
  await store.changePost(1, {
    method: "PUT",
    body: {
      userId: 1,
      id: 1,
      title: "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
      body: "text demo",
    },
  });
  listComment.value = store.listComment;
};

//codeMng
const codeMng = codeMngStore();
const listDataCodeMng = ref<CodeMngModel[]>([]);
const fetchDataCodeMng = async () => {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [],
    },
  });
  listDataCodeMng.value = codeMng.codeInfo;
  console.log(codeMng.codeInfo, codeMng.status);
};
watch(currentPage, (newVal) => {
  fetchData(newVal);
  fetchDataCodeMng();
});
// Fetch data when component is mounted
onBeforeMount(() => {
  fetchData(currentPage.value);
  fetchDataCodeMng();
});
</script>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
