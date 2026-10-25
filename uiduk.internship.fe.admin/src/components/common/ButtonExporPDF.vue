<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <button type="button" class="button btn_white" @click="downloadPdf">PDF 다운로드</button>
</template>

<script setup lang="ts">
import html2canvas from "html2canvas";
import jsPDF from "jspdf";
import { defineProps } from "vue";
import { commonStore } from "@/stores/common";

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
  nameExport: {
    type: String,
    required: true,
  },
  classScroll: {
    type: String,
    default: ".is_scroll",
  },
});
const store = commonStore();

const downloadPdf = () => {
  const content = document.getElementById(props.id);
  store.setLoading(true);

  const clone = content?.cloneNode(true) as HTMLElement;

  const scrollElements = clone.querySelectorAll(props.classScroll);
  scrollElements.forEach((el) => {
    const element = el as HTMLElement;
    element.style.maxHeight = "max-content";
  });

  const tempContainer = document.createElement("div");
  tempContainer.style.position = "absolute";
  tempContainer.style.top = "-9999px";
  tempContainer.appendChild(clone);
  document.body.appendChild(tempContainer);

  html2canvas(clone, { scale: 2 }).then((canvas) => {
    const pdf = new jsPDF();

    const imgWidth = 190;
    const pageHeight = pdf.internal.pageSize.height;
    const headerHeight = 10;
    const footerHeight = 10;
    const availableHeight = pageHeight - headerHeight - footerHeight;

    const imgHeight = (canvas.height * imgWidth) / canvas.width;
    let heightLeft = imgHeight;
    let pageNumber = 1;
    let position = 10 + headerHeight;
    let sourceY = 0;

    const addHeader = () => {
      pdf.setFontSize(12);
      pdf.text("Header Text - Page " + pageNumber, 10, 10);
    };

    const addFooter = () => {
      pdf.setFontSize(10);
      pdf.text("Footer Text - Page " + pageNumber, 10, pageHeight - 10);
    };

    while (heightLeft > 0) {
      let currentHeight = Math.min(availableHeight, heightLeft);

      if (currentHeight < heightLeft && currentHeight % 20 !== 0) {
        currentHeight = Math.floor(currentHeight / 20) * 20; // Điều chỉnh chiều cao xuống một dòng gần nhất
      }

      const tempCanvas = document.createElement("canvas");
      tempCanvas.width = canvas.width;
      tempCanvas.height = (currentHeight * canvas.width) / imgWidth;

      const ctx = tempCanvas.getContext("2d");
      ctx.drawImage(canvas, 0, sourceY, canvas.width, tempCanvas.height, 0, 0, canvas.width, tempCanvas.height);

      const imgData = tempCanvas.toDataURL("image/png");

      if (pageNumber > 1) pdf.addPage();
      addHeader();
      pdf.addImage(imgData, "PNG", 10, position, imgWidth, currentHeight);
      addFooter();

      heightLeft -= currentHeight;
      sourceY += tempCanvas.height;
      pageNumber++;
    }
    store.setLoading(false);

    pdf.save(`${props.nameExport}.pdf`);
  });
};
</script>

<style scoped lang="scss"></style>
