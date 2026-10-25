<template>
  <PageContainer>
    <div class="box dp_block">
      <div class="box_section">
        <p class="box_title_sm">{{ modeScreen }} menu</p>
        <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
          <table>
            <colgroup>
              <col style="width: 10%" />
              <col style="width: 40%" />
              <col style="width: 10%" />
              <col style="width: 40%" />
            </colgroup>

            <!-- form data -->
            <tbody>
              <!-- Row 1 -->
              <tr>
                <th scope="row">
                  <p>Site Type</p>
                </th>
                <td colspan="1">
                  <SelectBoxBase
                    :disabled="disabled"
                    :id="'siteId'"
                    :name="'siteId'"
                    v-model="selectSiteValue"
                    :data="listSelectSite"
                  ></SelectBoxBase>
                </td>

                <th scope="row">
                  <p>Parent</p>
                </th>
                <td colspan="3">
                  <SelectBoxBaseSearch
                    :id="'parentId'"
                    :disabled="disabled"
                    :name="'parentId'"
                    v-model="selectParentValue"
                    :data="listSelectParent"
                    v-if="(selectParentValue && listSelectParent.length > 0) || modeScreen === 'create'"
                  ></SelectBoxBaseSearch>
                </td>
              </tr>

              <!-- Row 2 -->
              <tr>
                <th scope="row">
                  <p class="required">Name</p>
                </th>
                <td colspan="1">
                  <InputBase v-if="modeScreen != 'detail'" :id="'name'" :name="'name'" v-model:model-value="menuResModel.name"></InputBase>

                  <span v-else :id="'name'" :name="'name'">
                    {{ menuResModel.name }}
                  </span>
                </td>

                <th scope="row">
                  <p class="required">VN Name</p>
                </th>
                <td colspan="3">
                  <InputBase v-if="modeScreen != 'detail'" :id="'vnName'" :name="'vnName'" v-model:model-value="menuResModel.vnName"></InputBase>

                  <span v-else :id="'vnName'" :name="'vnName'">
                    {{ menuResModel.vnName }}
                  </span>
                </td>
              </tr>
              <tr>
                <th scope="row">
                  <p class="required">Screen Name</p>
                </th>
                <td colspan="1">
                  <InputBase v-if="modeScreen != 'detail'" :id="'name'" :name="'name'" v-model:model-value="menuResModel.screenName"></InputBase>

                  <span v-else :id="'name'" :name="'name'">
                    {{ menuResModel.screenName }}
                  </span>
                </td>

                <th scope="row">
                  <p class="required">Display Order</p>
                </th>
                <td colspan="3">
                  <SelectBoxBase
                    v-if="modeScreen == 'create'"
                    :id="'displayOrderCreate'"
                    :disabled="disabled"
                    :name="'displayOrderCreate'"
                    v-model="displayOrderCreateValue"
                    :data="listDisplayOrderCreate"
                  ></SelectBoxBase>
                  <p v-if="modeScreen == 'detail'">{{ displayOrderEditValue }}</p>
                  <SelectBoxBase
                    v-if="modeScreen == 'edit'"
                    :id="'displayOrderEdit'"
                    :disabled="disabled"
                    :name="'displayOrderEdit'"
                    v-model="displayOrderEditValue"
                    :data="listDisplayOrderEdit"
                  ></SelectBoxBase>
                </td>
              </tr>
              <!-- Row 3 -->
              <tr>
                <th scope="row">
                  <p class="required">Route</p>
                </th>
                <td colspan="3">
                  <InputBase
                    v-if="modeScreen != 'detail'"
                    :id="'route'"
                    :name="'route'"
                    :class-name="'wd_p100'"
                    v-model:model-value="menuResModel.route"
                  ></InputBase>

                  <span v-else :id="'route'" :name="'route'" :class-name="'wd_p100'">
                    {{ menuResModel.route }}
                  </span>
                </td>
              </tr>

              <!-- Row 4 -->
              <tr v-if="modeScreen != 'create'">
                <th scope="row">
                  <p>Create By</p>
                </th>
                <td colspan="3">
                  <span :id="'createBy'" :name="'createBy'" :class-name="'wd_p100'">
                    {{ menuResModel.regId }}
                  </span>
                </td>
              </tr>

              <!-- Row 5 -->
              <tr v-if="modeScreen != 'create'">
                <th scope="row">
                  <p>Create Date</p>
                </th>
                <td colspan="3">
                  <span :id="'createDate'" :name="'createDate'" :class-name="'wd_p100'">
                    {{ menuResModel.regDate }}
                  </span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- button action -->
        <div class="box_section">
          <!-- create -->
          <div v-if="modeScreen == 'create'" class="btn_group btn_end mg_t15">
            <button type="button" :disabled="!isCheck" class="btn_md btn_primary" @click="confirmAlert()">Create</button>
            <button type="button" class="btn_md btn_lightgray" @click="back()">Back</button>
          </div>

          <!-- detail -->
          <div v-if="modeScreen == 'detail'" class="btn_group btn_end mg_t15">
            <button type="button" class="btn_md btn_primary" @click="goEditData()">Detail</button>
            <button type="button" class="btn_md btn_lightgray" @click="back()">Back</button>
          </div>

          <!-- edit -->
          <div v-if="modeScreen == 'edit'" class="btn_group btn_end mg_t15">
            <button type="button" class="btn_md btn_primary" @click="handActionDelete()">Delete</button>

            <button type="button" :disabled="!isCheck" class="btn_md btn_primary" @click="confirmAlert()">Edit</button>

            <button type="button" class="btn_md btn_lightgray" @click="back()">Back</button>
          </div>
        </div>
      </div>
    </div>
  </PageContainer>
</template>

<script lang="ts" setup>
import { ref, watch, onBeforeMount, onMounted } from "vue";
import { FORMAT_YYY_MM_DD, MODE_CREATE, MODE_DETAIL, MODE_EDIT, MODE_SHOW, SUCCSESS_STATUS } from "../../constants/screen.const";
import { SCREEN } from "../../router/screen";
import { menuStore } from "@/stores/menu";
import type { MenuRequestModel, MenuResponseModel } from "../../stores/menu/menu.type";
import Swal from "sweetalert2";
import SelectBoxBaseSearch from "@/components/common/input/SelectBoxBaseSearch.vue";
import { useRouter, useRoute } from "vue-router";
import { format } from "date-fns";

const router = useRouter();
const storeMenu = menuStore();
const route = useRoute();

const id = ref("");
const mode = ref(MODE_SHOW);
const disabled = ref(false);
const selectParentValue = ref("");
const displayOrderCreateValue = ref("");
const displayOrderEditValue = ref("");
const selectSiteValue = ref("SITE_TYPE_01");
const isCheck = ref(false);
const modeScreen = ref("");
const listSelectParent = ref([] as Array<any>);
const listDisplayOrderCreate = ref([] as Array<any>);
const listDisplayOrderEdit = ref([] as Array<any>);
const menuResModel = ref({} as MenuResponseModel);
const menuReqModel = ref({} as MenuRequestModel);

const listSelectSite = [
  { cdId: "SITE_TYPE_01", cdNm: "Admin" },
  { cdId: "SITE_TYPE_02", cdNm: "Professor" },
  { cdId: "SITE_TYPE_03", cdNm: "Student" },
  { cdId: "SITE_TYPE_04", cdNm: "Corporate member" },
];

function checkValid() {
  isCheck.value = !!(menuResModel.value.name && menuResModel.value.vnName && menuResModel.value.screenName && menuResModel.value.route);
  if (modeScreen.value === MODE_CREATE) {
    isCheck.value = isCheck.value && displayOrderCreateValue.value;
  }
}

function back() {
  router.replace({ path: SCREEN.pathMenu.path });
}

function goEditData() {
  mode.value = MODE_SHOW;
  disabled.value = false;
  modeScreen.value = MODE_EDIT;
  countOrder();
}

function handActionDelete() {
  Swal.fire({
    title: "Are you sure delete?",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes",
    cancelButtonText: "No",
  }).then((result) => {
    if (result.isConfirmed) {
      deleteData();
    }
  });
}

async function listParent() {
  await storeMenu.getSelectBoxParent({
    method: "POST",
    body: selectSiteValue.value,
  });
  if (storeMenu.status === SUCCSESS_STATUS) {
    listSelectParent.value = storeMenu.resultData.map((item) => ({
      cdId: item.id,
      cdNm: item.name,
    }));
    listSelectParent.value.unshift({ cdId: "", cdNm: "None" });
  }
}

function noitificationAlert() {
  Swal.fire({
    title: "Successfully!!!",
    icon: "success",
  }).then(() => {
    back();
  });
}

function getFormData() {
  menuReqModel.value.id = menuResModel.value.id;
  menuReqModel.value.siteType = selectSiteValue.value;
  menuReqModel.value.name = menuResModel.value.name;
  menuReqModel.value.route = menuResModel.value.route;
  menuReqModel.value.parentId = selectParentValue.value;
  menuReqModel.value.vnName = menuResModel.value.vnName;
  menuReqModel.value.screenName = menuResModel.value.screenName;
  menuReqModel.value.displayOrder = modeScreen.value === MODE_CREATE ? displayOrderCreateValue.value : displayOrderEditValue.value;
}

function confirmAlert() {
  const title = modeScreen.value === MODE_CREATE ? "Are you sure save (create)?" : "Are you sure save (edit)?";

  Swal.fire({
    title: title,
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes",
    cancelButtonText: "No",
  }).then((result) => {
    if (result.isConfirmed) {
      if (modeScreen.value === MODE_CREATE) {
        saveData();
      } else if (modeScreen.value === MODE_EDIT) {
        editData();
      }
    }
  });
}

async function saveData() {
  getFormData();
  await storeMenu
    .createMenu({
      method: "POST",
      body: menuReqModel.value,
    })
    .then(() => {
      if (!storeMenu.detailData) {
        Swal.fire({ title: "Route name is duplicated!!!", icon: "error" });
      } else {
        noitificationAlert();
      }
    });
}

async function editData() {
  getFormData();
  await storeMenu.updateMenu({
    method: "PUT",
    body: menuReqModel.value,
  });
  noitificationAlert();
}

async function deleteData() {
  await storeMenu.deleteMenu({
    method: "PUT",
    body: id.value,
  });
  noitificationAlert();
}

async function countOrder() {
  if (!selectParentValue.value) {
    selectParentValue.value = "";
  }
  if (modeScreen.value == MODE_CREATE) {
    listDisplayOrderCreate.value = [];
    storeMenu.getCountOrder({
      method: "POST",
      body: selectParentValue.value,
    });
    const maxNumber = storeMenu.resultData;
    const startNumber = 1;
    for (let i = startNumber; i <= maxNumber + 1; i++) {
      listDisplayOrderCreate.value.push({
        cdId: i,
        cdNm: i,
      });
    }
  } else if (modeScreen.value == MODE_EDIT) {
    listDisplayOrderEdit.value = [];
    storeMenu.getCountOrder({
      method: "POST",
      body: selectParentValue.value,
    });
    const maxNumber = storeMenu.resultData;
    const startNumber = 1;
    for (let i = startNumber; i <= maxNumber; i++) {
      listDisplayOrderEdit.value.push({
        cdId: i,
        cdNm: i,
      });
    }
  }
}

onBeforeMount(() => {
  modeScreen.value = Array.isArray(route.params.mode) ? route.params.mode[0] : route.params.mode;
  id.value = window.history.state.id;
  if (modeScreen.value === MODE_DETAIL) {
    mode.value = MODE_DETAIL;
    disabled.value = true;
  }
});

onMounted(async () => {
  if (modeScreen.value === MODE_CREATE) {
    countOrder();
  } else {
    await storeMenu.getDetailById({
      method: "POST",
      body: id.value,
    });
    if (storeMenu.status === SUCCSESS_STATUS && storeMenu.detailData) {
      menuResModel.value = storeMenu.detailData;
      selectSiteValue.value = menuResModel.value.siteType;
      selectParentValue.value = menuResModel.value.parentId;
      displayOrderEditValue.value = menuResModel.value.displayOrder;
      menuResModel.value.regDate = format(menuResModel.value.regDate, FORMAT_YYY_MM_DD);
    }
  }
  listParent();
});

watch(() => menuResModel, checkValid, { deep: true });
watch(displayOrderCreateValue, checkValid);
// watch(selectSiteValue, listParent);
watch(selectParentValue, countOrder);
</script>
