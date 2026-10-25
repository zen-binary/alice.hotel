<template>
  <button class="button btn_white button_print" :disabled="disable" @click="printPreview">PDF 다운로드</button>
</template>

<script setup>
import { ref } from 'vue';
import {commonStore} from "@/stores/common";

const props = defineProps({
  id: {
    type: String,
    required: true
  },
  nameExport: {
    type: String,
    required: true
  }
});

const printableArea = ref(null);
const disable = ref(false)
const store = commonStore()

const printPreview = () => {
  store.setLoading(true)
  disable.value = true
  const printContent = document.getElementById(props.id);
  if (printContent) {
    const clonedContent = printContent.cloneNode(true);

    // Xử lý textarea
    const textareas = clonedContent.querySelectorAll('textarea');

    textareas.forEach(textarea => {
      const value = textarea.value;
      const div = document.createElement('div');

      const lines = value.split('\n');

      lines.forEach(line => {
        const p = document.createElement('p');
        p.textContent = line;
        div.appendChild(p);
      });

      div.classList.add('text-area')
      div.style.minHeight = textarea.rows * 16 + 20 + 'px';
      textarea.parentNode.replaceChild(div, textarea);
    });

    clonedContent.querySelectorAll('input, textarea, button, select, a, iframe, details, object, audio, video, [tabindex], [contenteditable]').forEach(element => {
      element.blur();
      element.placeholder = '';
    });

    clonedContent.querySelectorAll('input').forEach((input) => {
      if(input.type !== 'checkbox' && input.type !== "radio" && input.type !== "file") {
        const span = document.createElement('span');

        span.textContent = input.value;
        span.className = 'replaced-input';
        input.parentNode.replaceChild(span, input);
      }
    });

    const printContents = clonedContent.innerHTML;

    // Tạo iframe mới
    const iframe = document.createElement('iframe');
    iframe.style.position = 'absolute';
    iframe.style.top = '-10000px';
    document.body.appendChild(iframe);

    const iframeDoc = iframe.contentWindow.document;

    iframeDoc.open();
    iframeDoc.write('<html><head><title>' + (props.nameExport) + '</title>');

    const styles = document.querySelectorAll('style, link[rel="stylesheet"]');
    styles.forEach(style => {
      iframeDoc.write(style.outerHTML);
    });

    iframeDoc.write(`
      <style>
        body {
          margin: 0;
          font-family: Arial, sans-serif;
          -webkit-print-color-adjust: exact;
          print-color-adjust: exact;
          opacity: 0;
          padding: 2px;
        }
        .print-area {
          display: block;
        }

        @media print {
          body {
            opacity:1;
          }
          input, textarea, button, select, a, iframe, details, object, audio, video, [tabindex], [contenteditable] {
            outline: none;
            box-shadow: none;
          }
          .text-area {
            position: relative;
            padding: 10px 12px;
            width: 100%;
            font-size: 16px;
            background-repeat: no-repeat;
            border: 1px solid var(--input--bd);
            border-radius: 4px;
            display: block;
            text-align: left;
          }
          .ql-container.ql-snow {
            max-height: max-content;
          }
          table {
            width: 100% !important;
            overflow: visible !important;
            max-width: inherit !important;
            min-width: inherit !important;
            max-height: inherit !important;
            min-height: inherit !important;
            border-collapse: collapse !important;
          }

          input.datepick,
          input.select_component_custom{
            border: none !important;
            background: none !important;
            padding: 0 !important;
          }
          .hidden_print {
            display: none !important;
            opacity: 0 !important;
          }
          .add_photo:has(.btn_add_photo) {
            display: none !important;
          }
          .replaced-input {
            display: block;
            white-space: pre-wrap;
            word-wrap: break-word;
            word-break: break-word;
            overflow-wrap: break-word;
            margin-top: auto;
            margin-bottom: auto;
          }
        }
      </style>
    `);

    iframeDoc.write('</head><body style="margin: 0;">');
    iframeDoc.write(`
      <div class="print-area">
        ${printContents}
      </div>
    `);
    iframeDoc.write('</body></html>');
    iframeDoc.close();

    iframe.onload = async () => {
      await nextTick(() => {
        store.setLoading(false);
        disable.value = false;
      });

      iframe.contentWindow.focus();
      iframe.contentWindow.print();

      setTimeout(() => {
        document.body.removeChild(iframe);
      }, 500);
    };

    setTimeout(() => {
      if (store.loading) {
        store.setLoading(false)
        disable.value = false
        console.error('Print operation timed out');
      }
    }, 10000);
  }
};
</script>

<style scoped>
/* CSS đảm bảo không ảnh hưởng tới scroll và điều hướng */
html, body {
  overflow: auto; /* Đảm bảo trang có thể cuộn */
}
@media print {
  .button_print {
    display: none;
  }
}
</style>
<style>
@media print {
  body {
    margin: 0;
    padding: 0;
    font-size: 12pt; /* Đảm bảo văn bản không quá to */
  }

  .print-block {
    page-break-inside: avoid;
    break-inside: avoid;
  }

  /* Đảm bảo mỗi khối bắt đầu trên trang mới (nếu cần) */
  .new-page {
    page-break-before: always;
    break-before: page;
  }

  .table_col {
    align-items: stretch;
    height: auto !important;
  }

  .table_col div {
    height: 100% !important;
    max-height: none !important;
    display: flex;
    align-items: center;
    min-height: 40px;
  }
  .item {
    align-items: stretch;
    height: auto !important;
  }

  .label, .value {
    height: auto !important;
    max-height: none !important;
  }

  .bottom.preview_img .add_photo {
    width: 150px;
    height: 150px;
  }

  .bottom.preview_img .add_photo > img{
    border: none !important;
    max-width: 150px;
    max-height: 150px;
    object-fit: contain;
  }

  .bottom.preview_img .add_photo > p {
    display: none;
  }

  .previewMess{
    display: none;
  }
  .group_file__content .top > .file_form_style > button{
    display: none !important;
  }
}
</style>