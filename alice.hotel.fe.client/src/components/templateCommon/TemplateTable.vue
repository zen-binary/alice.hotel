<template>
  <div class="box dp_block">
    <div class="section_tit_wrap">
      <h4 class="section_tit_xs required">Table</h4>
      <strong class="required">Note</strong>
      <span>Show scroll when</span>
      <hr />
      <p>Show scroll X when width table</p>
      <ul>
        <li>scrollx_tbl_xs: 500px</li>
        <li>scrollx_tbl_sm: 600px</li>
        <li>scrollx_tbl_md: 800px</li>
        <li>scrollx_tbl_lg: 1000px</li>
        <li>scrollx_tbl_xl: 1216px</li>
        <li>scrollx_tbl_xxl: 1600px</li>
      </ul>
      <hr />
      <p>Show scroll Y when height table</p>
      <ul>
        <li>scrolly_tbl_xs: 320px</li>
        <li>scrolly_tbl_sm: 400px</li>
        <li>scrolly_tbl_md: 500px</li>
        <li>scrolly_tbl_lg: 600px</li>
        <li>scrolly_tbl_xl: 700px</li>
        <li>scrolly_tbl_xxl: 800px</li>
      </ul>
    </div>
    <div class="tbl tbl_col tbl_scroll scrollx_tbl_xs scrolly_tbl_xs">
      <table class="table_head">
        <caption>Tiêu đề bảng cuộn</caption>
        <colgroup>
          <col style="width: auto" />
          <col style="width: 18%" />
          <col style="width: 18%" />
          <col style="width: 18%" />
        </colgroup>
        <thead>
          <tr>
            <th scope="row">Tên môn</th>
            <th scope="row">Hoàn thành Học kỳ</th>
            <th scope="row">Tín chỉ</th>
            <th scope="row">Chọn</th>
          </tr>
        </thead>
      </table>
      <div class="tbl_scroll_body">
        <table>
          <caption>Tiêu đề bảng</caption>
          <colgroup>
            <col style="width: auto" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
          </colgroup>
          <tbody>
            <tr>
              <th scope="row">Nội dung</th>
              <td>Nội dung</td>
              <td>Nội dung</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">Chọn</button>
              </td>
            </tr>
            <tr>
              <th scope="row">Nội dung</th>
              <td>Nội dung</td>
              <td>Nội dung</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">Chọn</button>
              </td>
            </tr>
            <tr>
              <th scope="row">Nội dung</th>
              <td>Nội dung</td>
              <td>Nội dung</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">Chọn</button>
              </td>
            </tr>
            <tr>
              <th scope="row">Nội dung</th>
              <td>Nội dung</td>
              <td>Nội dung</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">Chọn</button>
              </td>
            </tr>
            <tr>
              <th scope="row">Nội dung</th>
              <td>Nội dung</td>
              <td>Nội dung</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">Chọn</button>
              </td>
            </tr>
            <tr>
              <th scope="row">Nội dung</th>
              <td>Nội dung</td>
              <td>Nội dung</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">Chọn</button>
              </td>
            </tr>
            <tr>
              <th scope="row">Nội dung</th>
              <td>Nội dung</td>
              <td>Nội dung</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">Chọn</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="section_tit_wrap mt_8">
      <h4 class="section_tit_xs">2. Mục đích tự đánh giá chương trình</h4>
    </div>
    <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
      <table>
        <caption>Tiêu đề bảng</caption>
        <colgroup>
          <col style="width: 22%" />
          <col style="width: auto" />
        </colgroup>

        <tbody>
          <tr>
            <th scope="row">Mục đích thực hiện CQI chương trình</th>
            <td>
              <ul class="list_ul">
                <li>Thực hiện giám sát liên tục ở cấp khoa để cung cấp chương trình đào tạo chất lượng theo mục tiêu của trường và khoa</li>
                <li>Xây dựng vòng lặp cải tiến giáo dục bằng chẩn đoán/đánh giá hệ thống, nâng cao năng lực sinh viên và cải thiện lớp học</li>
                <li>Đề xuất cải tiến cho phát triển, điều chỉnh và vận hành chương trình năm sau</li>
              </ul>
            </td>
          </tr>
          <tr>
            <th scope="row">Mục đích thực hiện CQI chương trình</th>
            <td>
              <ul class="list_ul">
                <li>Thực hiện giám sát liên tục ở cấp khoa để cung cấp chương trình đào tạo chất lượng theo mục tiêu của trường và khoa</li>
                <li>Xây dựng vòng lặp cải tiến giáo dục bằng chẩn đoán/đánh giá hệ thống, nâng cao năng lực sinh viên và cải thiện lớp học</li>
                <li>Đề xuất cải tiến cho phát triển, điều chỉnh và vận hành chương trình năm sau</li>
              </ul>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style></style>
