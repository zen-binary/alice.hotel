<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <PageContainer>
    <h1 v-once>
      {{ globalFile.capitalize("template capitalize") }}
    </h1>
    <Breadcrumb :page-title="pageTitle" :breadcrumb-items="breadcrumbItems"/>

    <router-link to="/demo">demp</router-link>
    <div id="top" class="box dp_block">
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <div class="container">
          <div class="ellipsis">Đây là một chuỗi văn bản rất dài mà bạn muốn cắt bớt và hiển thị dấu ...</div>
        </div>
        <p>
          box 내에 영역 1개 일때
          <br/>
          메인에선 box_title
          <br/>
          서브에선 box_title_sm
        </p>
        <h1 style="font-size: 40px">Menu</h1>
        <nav>
          <router-link class="font_20 font_nightblue" to="/demo">Data fetch demo</router-link>
          <br/>
          <a class="font_20 font_nightblue" href="#section13">Card Item</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section0">Mẫu Input</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section1">Box</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section2">Title</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section3">Content</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section4">Button</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section12">Tab + step</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section5">Checkbox + radio</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section6">Status component</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section7">Table</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section11">TModal</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section8">Grid</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section9">File upload</a>
          <br/>
          <a class="font_20 font_nightblue" href="#section10">pagination</a>
          <br/>
        </nav>
      </div>
    </div>
    <div id="export" class="box dp_block">
      <p>Note: Thực hiện export file pdf theo id</p>
      <p>Name file phải được thiết lập</p>
      <hr/>
      <p>Sẽ in ra pdf theo kích thước và css hiện tại</p>
      <p>Ngoài ra</p>
      <ul>
        <li>
          <p class="bold">1. Bóng đổ</p>
          <p>box-shadow: Hiệu ứng bóng đổ không được hiển thị.</p>
        </li>
        <li>
          <p class="bold">2. Đổ màu</p>
          <p>text-shadow: Bóng đổ cho văn bản cũng thường không được hỗ trợ.</p>
        </li>
        <li>
          <p class="bold">3. Vị trí tuyệt đối</p>
          <p>position: absolute: Một số vị trí tuyệt đối có thể không được tính toán chính xác khi xuất PDF.</p>
        </li>
        <li>
          <p class="bold">4. Hình nền</p>
          <p>background-image: Hình nền phức tạp đôi khi không được hiển thị chính xác.</p>
        </li>
        <li>
          <p class="bold">5. Phát động animation và transition</p>
          <p>Các thuộc tính liên quan đến animation và transition như transition, transform, animation không được hỗ trợ trong PDF.</p>
        </li>
        <li>
          <p class="bold">6. Pseudo-classes và Pseudo-elements</p>
          <p>:before, :after: Những phần tử giả này có thể không được render chính xác.</p>
        </li>
        <li>
          <p class="bold">7. Flexbox và Grid</p>
          <p>Một số thuộc tính của Flexbox và CSS Grid có thể không được hỗ trợ đầy đủ, dẫn đến bố cục không chính xác.</p>
        </li>
        <li>
          <p class="bold">8. Custom Properties</p>
          <p>CSS Variables (biến CSS) thường không được xử lý.</p>
        </li>
        <li>
          <p class="bold">9. Multi-column Layout</p>
          <p>column-count, column-gap: Các thuộc tính liên quan đến bố cục nhiều cột có thể không hoạt động.</p>
        </li>
        <li>
          <p class="bold">10. Media Queries</p>
          <p>Các media queries có thể không được áp dụng, dẫn đến việc không hiển thị đúng trong PDF.</p>
        </li>
        <li>
          <p class="bold">11. Hover Effects</p>
          <p>Các hiệu ứng hover như :hover không được hỗ trợ.</p>
        </li>
      </ul>
      <hr/>
      <p>Trong trường hợp tồn tại 1 hoặc nhiều tag có scroll thì sử dụng chung 1 class và truyền tên class thông qua props classScroll </p>
      <hr/>
      <p>Lưu ý nên test nhiều lần</p>
      <ButtonExporPDF id="export" name-export="id1"/>
    </div>

    <div class="box dp_flex flex_column" id="id1">
      <h1 class="dp_block">Status card</h1>
      <div class="search_box col_2">
        <ul>
          <template v-for="i in listStatus1">
            <li>
              <p>{{ i.title }}</p>
            </li>
            <li>
              <div class="card_content_status" style="border-radius: 100px;"><p :data-status="i.statusCode">{{ i.titleKo
                }}</p></div>
            </li>
          </template>
        </ul>
      </div>
      <div class="search_box col_2">
        <ul>
          <template v-for="i in listStatus2">
            <li>
              <p>{{ i.title }}</p>
            </li>
            <li>
              <div class="card_content_status" style="border-radius: 4px;"><p :data-status="i.statusCode">{{ i.titleKo
                }}</p></div>
            </li>
          </template>
        </ul>
      </div>
      <p class="validate_password">영문/숫자/특수기호 각 1개 포함, 8자리 이상</p>
    </div>
    <CollapBase
        :is-show="openCollapse"
        :title="'Collapse'"
        :is-check="true"
        :on-click="
        () => {
          openCollapse = !openCollapse;
        }
      "
    >
      <p class="box_title_sm">Collap</p>
      <div class="search_box col_3">
        <input/>
        <input/>
      </div>
    </CollapBase>
    <br/>
    <CollapBase
        :is-show="openCollapse1"
        :title="'Collapse'"
        :is-check="true"
        :on-click="
        () => {
          openCollapse1 = !openCollapse1;
        }
      "
    >
      <p class="box_title_sm">Collap</p>
      <div class="search_box col_3">
        <input/>
        <input/>
      </div>
    </CollapBase>
  </PageContainer>

  <PageContainer style="background-color: white">
    <TemplateItemCard id="section13" />
    <TemplateInput id="section0" />
    <TemplateBox id="section1" />
    <TemplateTitleSection id="section2" />
    <TemplateContent id="section3" />
    <TemplateButton id="section4" />
    <TemplateTab id="section12" />
    <TemplateCheck id="section5" />
    <TemplateStatus id="section6" />
    <TemplateTable id="section7" />
    <TemplateTmodal id="section11" />
    <TemplateGrid id="section8" />
    <TemplateFileUpload id="section9" />
    <TemplatePagination id="section10" />
    <div class="box dp_block">
      <MenuTab
        @handelRedirectToDetail="
          (id: string) => {
            console.log(id);
          }
        "
        @handelSeeMore="
          () => {
            console.log('redirect to route');
          }
        "
        :tabs="['학교공지', '공지사항', '이벤트', 'Q&A']"
        :items="[
          [
            { id: 1, day: '12', date: '2024.4', title: 'First Announcement' },
            { id: 2, day: '13', date: '2024.4', title: 'Second Announcement' },
          ],
          [{ id: 3, day: '14', date: '2024.5', title: 'Third Announcement' }],
          [{ id: 4, day: '15', date: '2024.6', title: 'Fourth Announcement' }],
          [
            { id: 5, day: '15', date: '2024.6', title: 'Fourth Announcement' },
            { id: 1, day: '15', date: '2024.6', title: 'Fourth Announcement' },
            { id: 1, day: '15', date: '2024.6', title: 'Fourth Announcement' },
            { id: 1, day: '15', date: '2024.6', title: 'Fourth Announcement' },
            { id: 1, day: '15', date: '2024.6', title: 'Fourth Announcement' },
          ],
        ]"
      ></MenuTab>
    </div>
    <button class="button btn_md btn_secondary" @click="submit">Submit + scroll to error</button>
  </PageContainer>
</template>
<script setup lang="ts">
import Breadcrumb from "@/components/common/BreadcrumbComponent.vue";
import { ref, getCurrentInstance } from "vue";
import { commonStore } from "@/stores/common";
import TemplateTable from "@/components/templateCommon/TemplateTable.vue";
import TemplateTitleSection from "@/components/templateCommon/TemplateTitleSection.vue";
import TemplateGrid from "@/components/templateCommon/TemplateGrid.vue";
import TemplateFileUpload from "@/components/templateCommon/TemplateFileUpload.vue";
import TemplateContent from "@/components/templateCommon/TemplateContent.vue";
import TemplateBox from "@/components/templateCommon/TemplateBox.vue";
import TemplateStatus from "@/components/templateCommon/TemplateStatus.vue";
import TemplateButton from "@/components/templateCommon/TemplateButton.vue";
import TemplateCheck from "@/components/templateCommon/TemplateCheck.vue";
import TemplatePagination from "@/components/templateCommon/TemplatePagination.vue";
import TemplateInput from "@/components/templateCommon/TemplateInput.vue";
import TemplateTmodal from "@/components/templateCommon/TemplateTmodal.vue";
import TemplateTab from "@/components/templateCommon/TemplateTab.vue";
import CollapBase from "@/components/common/CollapBase.vue";
import globalFile from "@/utils/global.ts";
import TemplateItemCard from "@/components/templateCommon/TemplateItemCard.vue";
import PageContainer from "@/components/common/PageContainer.vue";
import BannerComponent from "@/components/common/BannerComponent.vue";
import MenuTab from "@/components/common/MenuTab.vue";
import ButtonExporPDF from "@/components/common/ButtonExporPDF.vue";

const { appContext } = getCurrentInstance();
const global = appContext.config.globalProperties.$global;

const pageTitle = ref("TemplateUI");
const breadcrumbItems = ref([
  { label: "HOME", link: "/" },
  { label: "dept1", link: "#" },
  { label: "dept2", link: "/banner" },
]);
const listStatus1 = ref([
  {
    title: "Chờ duyệt",
    titleKo: "승인대기",
    statusCode: "1",
  },
  {
    title: "Hủy",
    titleKo: "취소",
    statusCode: "2",
  },
  {
    title: "Chưa duyệt",
    titleKo: "미승인",
    statusCode: "3",
  },
  {
    title: "Duyệt",
    titleKo: "승인",
    statusCode: "4",
  },
  {
    title: "Chưa ký kết",
    titleKo: "미체결",
    statusCode: "5",
  },
  {
    title: "Đang thực tập",
    titleKo: "실습중",
    statusCode: "6",
  },
  {
    title: "Hoàn thành thực tập",
    titleKo: "실습완료",
    statusCode: "7",
  },
  {
    title: "Hoàn thành",
    titleKo: "이수",
    statusCode: "8",
  },
  {
    title: "Chưa hoàn thành",
    titleKo: "미이수",
    statusCode: "9",
  },
  {
    title: "Bảo lưu",
    titleKo: "유보",
    statusCode: "10",
  },
]);
const listStatus2 = ref([
  {
    title: "Đang tiếp nhận",
    titleKo: "접수중",
    statusCode: "11",
  },
  {
    title: "Hoàn thành tiếp nhận",
    titleKo: "접수완료",
    statusCode: "12",
  },
]);
//store
const store = commonStore();
const openCollapse = ref(true);
const openCollapse1 = ref(false);

const submit = () => {
  if (store.check) {
    store.scrollToErrorRequired();
  }
};
</script>

<style lang="scss"></style>
