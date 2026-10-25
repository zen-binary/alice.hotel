<template>
  <PageContainer>
    <BreadcrumbComponent :pageTitle="'Phòng khách sạn'" />

    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Từ khóa</p>
              <InputBase v-model="searchModel.keyword" placeholder="Mã / Tên" />
            </li>
            <li>
              <p>Loại</p>
              <SelectBoxBase id="roomType" name="roomType" v-model="searchModel.roomType" :data="typeOptions" />
            </li>
            <li>
              <p>Trạng thái</p>
              <SelectBoxBase id="status" name="status" v-model="searchModel.status" :data="statusOptions" />
            </li>
          </ul>
          <div class="btn_group btn_end mg_t10">
            <button type="button" class="button btn_md btn_lightgray" @click="reset">Đặt lại</button>
            <button type="button" class="button btn_md btn_primary" @click="search">Tìm kiếm</button>
          </div>
        </div>
      </div>
    </div>

    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
          <h2 class="title-search">Danh sách phòng Alice Hotel</h2>
          <div class="btn_group btn_end">
            <button type="button" class="btn_md btn_primary" @click="openCreate">Thêm mới</button>
          </div>
        </div>

        <table class="table_base">
          <thead>
            <tr>
              <th>Mã</th>
              <th>Tên</th>
              <th>Loại</th>
              <th>Giá</th>
              <th>Sức chứa</th>
              <th>Trạng thái</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in store.list" :key="item.roomId">
              <td>{{ item.roomCode }}</td>
              <td>{{ item.roomName }}</td>
              <td>{{ typeOptions.find((o) => o.cdId === item.roomType)?.cdNm || item.roomType }}</td>
              <td>{{ formatPrice(item.price) }}</td>
              <td>{{ item.capacity }}</td>
              <td>{{ statusOptions.find((o) => o.cdId === item.status)?.cdNm || item.status }}</td>
              <td class="actions">
                <button type="button" class="button btn_sm btn_primary" @click="openEdit(item)">Sửa</button>
                <button type="button" class="button btn_sm btn_lightgray" @click="onDelete(item)">Xóa</button>
              </td>
            </tr>
            <tr v-if="!store.list.length">
              <td colspan="7" class="empty">Không có phòng</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <TModal :is-open="modalOpen" :is-show-header="true" :is-show-footer="true" modal-id="hotelRoomForm" class-size="small" @close-modal="modalOpen = false">
      <template #title><strong>{{ form.roomId ? "Sửa phòng" : "Thêm phòng" }}</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup><col style="width: 30%" /><col style="width: 70%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p class="required">Mã</p></th>
              <td><InputBase v-model="form.roomCode" /></td>
            </tr>
            <tr>
              <th scope="row"><p class="required">Tên</p></th>
              <td><InputBase v-model="form.roomName" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Loại</p></th>
              <td><SelectBoxBase id="formType" name="formType" v-model="form.roomType" :data="typeOptions.filter((i) => i.cdId)" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Giá</p></th>
              <td><InputBase v-model="form.price" isNumber /></td>
            </tr>
            <tr>
              <th scope="row"><p>Sức chứa</p></th>
              <td><InputBase v-model="form.capacity" isNumber /></td>
            </tr>
            <tr>
              <th scope="row"><p>Trạng thái</p></th>
              <td><SelectBoxBase id="formStatus" name="formStatus" v-model="form.status" :data="statusOptions.filter((i) => i.cdId)" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Mô tả</p></th>
              <td><InputBase v-model="form.description" /></td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="modalOpen = false">Hủy</button>
        <button type="button" class="button btn_md btn_primary" @click="save">Lưu</button>
      </template>
    </TModal>
  </PageContainer>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import PageContainer from "@/components/common/PageContainer.vue";
import BreadcrumbComponent from "@/components/common/BreadcrumbComponent.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import TModal from "@/components/common/excel/TModal.vue";
import { hotelRoomStore } from "@/stores/hotelRoom";
import type { HotelRoomModel } from "@/stores/hotelRoom/hotelRoom.type";
import Swal from "sweetalert2";

const store = hotelRoomStore();
const modalOpen = ref(false);

const typeOptions = [
  { cdId: "", cdNm: "Tất cả" },
  { cdId: "STANDARD", cdNm: "Tiêu chuẩn" },
  { cdId: "DELUXE", cdNm: "Cao cấp" },
  { cdId: "SUITE", cdNm: "Hạng sang" },
];
const statusOptions = [
  { cdId: "", cdNm: "Tất cả" },
  { cdId: "AVAILABLE", cdNm: "Còn trống" },
  { cdId: "BOOKED", cdNm: "Đã đặt" },
  { cdId: "MAINTENANCE", cdNm: "Bảo trì" },
];

const searchModel = reactive({
  keyword: "",
  roomType: "",
  status: "",
  page: 1,
  size: 20,
});

const emptyForm = (): HotelRoomModel => ({
  roomCode: "",
  roomName: "",
  roomType: "STANDARD",
  price: 0,
  capacity: 2,
  status: "AVAILABLE",
  description: "",
});

const form = reactive<HotelRoomModel>(emptyForm());

const formatPrice = (price?: number) =>
  new Intl.NumberFormat("vi-VN").format(Number(price || 0));

const search = async () => {
  await store.search({ method: "POST", body: { ...searchModel } });
};

const reset = async () => {
  searchModel.keyword = "";
  searchModel.roomType = "";
  searchModel.status = "";
  await search();
};

const openCreate = () => {
  Object.assign(form, emptyForm());
  modalOpen.value = true;
};

const openEdit = (item: HotelRoomModel) => {
  Object.assign(form, { ...item });
  modalOpen.value = true;
};

const save = async () => {
  if (!form.roomCode || !form.roomName) {
    await Swal.fire({ text: "Vui lòng nhập Mã và Tên", confirmButtonText: "Xác nhận" });
    return;
  }
  try {
    if (form.roomId) {
      await store.update(form.roomId, { ...form, price: Number(form.price), capacity: Number(form.capacity) });
    } else {
      await store.create({ ...form, price: Number(form.price), capacity: Number(form.capacity) });
    }
    modalOpen.value = false;
    await search();
  } catch (e: any) {
    await Swal.fire({ text: e?.message || "Lưu thất bại", confirmButtonText: "Xác nhận" });
  }
};

const onDelete = async (item: HotelRoomModel) => {
  const result = await Swal.fire({
    text: `Xóa phòng ${item.roomCode}?`,
    showCancelButton: true,
    confirmButtonText: "Xóa",
    cancelButtonText: "Hủy",
  });
  if (!result.isConfirmed || !item.roomId) return;
  await store.remove(item.roomId);
  await search();
};

onMounted(search);
</script>

<style scoped lang="scss">
.table_base {
  width: 100%;
  border-collapse: collapse;
  th,
  td {
    border-bottom: 1px solid #e4e4e4;
    padding: 1.2rem;
    text-align: left;
    font-size: 1.4rem;
  }
  .empty {
    text-align: center;
    color: #777;
  }
  .actions {
    display: flex;
    gap: 0.8rem;
  }
}
.page_toparea {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.6rem;
}
</style>
