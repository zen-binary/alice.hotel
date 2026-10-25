<template>
  <PageContainer>
    <BreadcrumbComponent :pageTitle="'Tài khoản khách'" />

    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Tài khoản / tên / email</p>
              <InputBase id="guestKw" v-model="keyword" placeholder="Tìm khách đã đăng ký" isSearch @search="load" />
            </li>
            <li>
              <p>Trạng thái</p>
              <SelectBoxBase id="guestLock" name="guestLock" v-model="lockFilter" :data="lockItems" />
            </li>
          </ul>
          <div class="btn_group btn_end mg_t10">
            <button type="button" class="button btn_md btn_lightgray" @click="reset">Đặt lại</button>
            <button type="button" class="button btn_md btn_primary" @click="load">Tìm</button>
          </div>
        </div>
      </div>
    </div>

    <div class="box dp_block">
      <div class="box_section">
        <div class="pms_list_head">
          <h2>Khách đã đăng ký ({{ filtered.length }})</h2>
        </div>
        <div class="table_wrap">
          <table class="table_base">
            <thead>
              <tr>
                <th>Tài khoản</th>
                <th>Họ tên</th>
                <th>Email</th>
                <th>Điện thoại</th>
                <th>Đăng ký</th>
                <th>Trạng thái</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="g in filtered" :key="g.userId">
                <td><strong>{{ g.userId }}</strong></td>
                <td>{{ g.userName || "—" }}</td>
                <td>{{ g.email || "—" }}</td>
                <td>{{ g.phone || "—" }}</td>
                <td>{{ when(g.regDate) }}</td>
                <td>
                  <span :class="g.lockedYn === 'Y' ? 'tag lock' : 'tag open'">
                    {{ g.lockedYn === "Y" ? "Đã khóa" : "Đang dùng" }}
                  </span>
                </td>
                <td class="actions">
                  <button type="button" class="button btn_sm btn_primary" @click="openEdit(g)">Sửa</button>
                  <button type="button" class="button btn_sm btn_lightgray" @click="toggleLock(g)">
                    {{ g.lockedYn === "Y" ? "Mở khóa" : "Khóa" }}
                  </button>
                </td>
              </tr>
              <tr v-if="!filtered.length">
                <td colspan="7" class="empty">Chưa có tài khoản khách phù hợp.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <TModal :is-open="open" :is-show-header="true" :is-show-footer="true" modal-id="guestEdit" class-size="small" @close-modal="open = false">
      <template #title><strong>Hồ sơ {{ form.userId }}</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup>
            <col style="width: 34%" />
            <col style="width: 66%" />
          </colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Họ tên</p></th>
              <td>{{ form.userName || "—" }}</td>
            </tr>
            <tr>
              <th scope="row"><p>Email</p></th>
              <td><InputBase id="gEmail" v-model="form.email" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Điện thoại</p></th>
              <td><InputBase id="gPhone" v-model="form.phone" /></td>
            </tr>
            <tr>
              <th scope="row"><p>CCCD / hộ chiếu</p></th>
              <td><InputBase id="gId" v-model="form.idNumber" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Địa chỉ</p></th>
              <td><InputBase id="gAddr" v-model="form.address" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Ghi chú nội bộ</p></th>
              <td><InputBase id="gNote" v-model="form.internalNote" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Khóa đăng nhập</p></th>
              <td>
                <SelectBoxBase id="gLock" name="gLock" v-model="form.lockedYn" :data="lockEditItems" />
              </td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="open = false">Đóng</button>
        <button type="button" class="button btn_md btn_primary" :disabled="saving" @click="save">Lưu</button>
      </template>
    </TModal>
  </PageContainer>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import PageContainer from "@/components/common/PageContainer.vue";
import BreadcrumbComponent from "@/components/common/BreadcrumbComponent.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import TModal from "@/components/common/excel/TModal.vue";
import { aliceBookingStore } from "@/stores/aliceBooking";

const store = aliceBookingStore();
const rows = ref<any[]>([]);
const keyword = ref("");
const lockFilter = ref("");
const open = ref(false);
const saving = ref(false);
const form = ref<any>({});

const lockItems = [
  { cdId: "", cdNm: "Tất cả" },
  { cdId: "N", cdNm: "Đang dùng" },
  { cdId: "Y", cdNm: "Đã khóa" },
];
const lockEditItems = [
  { cdId: "N", cdNm: "Cho đăng nhập" },
  { cdId: "Y", cdNm: "Khóa tài khoản" },
];

const filtered = computed(() => {
  const q = keyword.value.trim().toLowerCase();
  return rows.value.filter((g) => {
    if (lockFilter.value && (g.lockedYn || "N") !== lockFilter.value) return false;
    if (!q) return true;
    return [g.userId, g.userName, g.email, g.phone].join(" ").toLowerCase().includes(q);
  });
});

const when = (v: any) => {
  if (!v) return "—";
  if (Array.isArray(v)) {
    const [y, m, d, h = 0, mi = 0] = v;
    const pad = (n: number) => String(n).padStart(2, "0");
    return `${y}-${pad(m)}-${pad(d)} ${pad(h)}:${pad(mi)}`;
  }
  return String(v).replace("T", " ").slice(0, 16);
};

const load = async () => {
  rows.value = await store.loadGuests();
};

const reset = () => {
  keyword.value = "";
  lockFilter.value = "";
  load();
};

const openEdit = (g: any) => {
  form.value = { ...g, lockedYn: g.lockedYn || "N" };
  open.value = true;
};

const save = async () => {
  saving.value = true;
  try {
    await store.saveGuest(form.value);
    open.value = false;
    await load();
  } finally {
    saving.value = false;
  }
};

const toggleLock = async (g: any) => {
  await store.saveGuest({ ...g, lockedYn: g.lockedYn === "Y" ? "N" : "Y" });
  await load();
};

onMounted(load);
</script>

<style scoped>
.pms_list_head h2 {
  margin: 0 0 12px;
  font-size: 16px;
}
.table_wrap {
  overflow-x: auto;
}
.empty {
  text-align: center;
  color: #6b7280;
  padding: 18px;
}
.actions {
  white-space: nowrap;
  display: flex;
  gap: 6px;
}
.tag {
  display: inline-flex;
  border-radius: 999px;
  padding: 2px 8px;
  font-size: 12px;
  font-weight: 700;
}
.tag.open {
  background: #e8f6ee;
  color: #157347;
}
.tag.lock {
  background: #fdecec;
  color: #b42318;
}
</style>
