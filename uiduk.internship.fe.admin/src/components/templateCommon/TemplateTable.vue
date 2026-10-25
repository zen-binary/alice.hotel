<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

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
        <caption>스크롤 테이블 제목</caption>
        <colgroup>
          <col style="width: auto" />
          <col style="width: 18%" />
          <col style="width: 18%" />
          <col style="width: 18%" />
        </colgroup>
        <thead>
          <tr>
            <th scope="row">과목명</th>
            <th scope="row">이수 학기</th>
            <th scope="row">학점</th>
            <th scope="row">선택</th>
          </tr>
        </thead>
      </table>
      <div class="tbl_scroll_body">
        <table>
          <caption>테이블 제목</caption>
          <colgroup>
            <col style="width: auto" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
          </colgroup>
          <tbody>
            <tr>
              <th scope="row">내용</th>
              <td>내용</td>
              <td>내용</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">선택</button>
              </td>
            </tr>
            <tr>
              <th scope="row">내용</th>
              <td>내용</td>
              <td>내용</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">선택</button>
              </td>
            </tr>
            <tr>
              <th scope="row">내용</th>
              <td>내용</td>
              <td>내용</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">선택</button>
              </td>
            </tr>
            <tr>
              <th scope="row">내용</th>
              <td>내용</td>
              <td>내용</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">선택</button>
              </td>
            </tr>
            <tr>
              <th scope="row">내용</th>
              <td>내용</td>
              <td>내용</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">선택</button>
              </td>
            </tr>
            <tr>
              <th scope="row">내용</th>
              <td>내용</td>
              <td>내용</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">선택</button>
              </td>
            </tr>
            <tr>
              <th scope="row">내용</th>
              <td>내용</td>
              <td>내용</td>
              <td class="td_input">
                <button type="button" class="btn_round btn_sm btn_white">선택</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="section_tit_wrap mt_8">
      <h4 class="section_tit_xs">2.교육과정 자체평가 목적</h4>
    </div>
    <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
      <table>
        <caption>테이블제목</caption>
        <colgroup>
          <col style="width: 22%" />
          <col style="width: auto" />
        </colgroup>

        <tbody>
          <tr>
            <th scope="row">교육과정CQI 시행목적</th>
            <td>
              <ul class="list_ul">
                <li>대학과 학과의 교육목표에 따라 양질의 교육과정을 제공하기 위한 학과 차원의 지속적인 모니터링 시행</li>
                <li>교육과정의 체계적인 진단과 평가를 통해 학생의 역량(학습성과)을 향상시키고, 수업을 개선하는 등 교육의 선순환 체계 구축</li>
                <li>차년도 교육과정 개발 개편 및 운영을 위한 개선사항 도출</li>
              </ul>
            </td>
          </tr>
          <tr>
            <th scope="row">교육과정CQI 시행목적</th>
            <td>
              <ul class="list_ul">
                <li>대학과 학과의 교육목표에 따라 양질의 교육과정을 제공하기 위한 학과 차원의 지속적인 모니터링 시행</li>
                <li>교육과정의 체계적인 진단과 평가를 통해 학생의 역량(학습성과)을 향상시키고, 수업을 개선하는 등 교육의 선순환 체계 구축</li>
                <li>차년도 교육과정 개발 개편 및 운영을 위한 개선사항 도출</li>
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
