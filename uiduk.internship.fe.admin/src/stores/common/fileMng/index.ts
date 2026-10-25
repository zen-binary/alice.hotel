import { createFile, getFile, getFileUploaded, deleteFile, getFileOrigin , getMultipleFile} from "./fileMng.service";
import { defineStore } from "pinia";
import { fileUploadedInfo } from "./fileMng.type";
import { ref } from "vue";
export const fileMngStore = defineStore("fileMng", () => {
  const status = ref<number>();
  const fileUploadedInfoList = ref<fileUploadedInfo[]>([]);
  const fileDowload = ref<any>();
  const type = ref<any>();
  const imgInfo = ref<any>();
  const fileDowloadMuitl = ref<any>();
  const addfile = async (params: any) => {
    try {
      const response = await createFile(params);
      status.value = response.status;
    } catch (error) {
      console.log("API 호출 시 오류가 있습니다");
    }
  };
  const getFileDowloadOrigin = async (params: any) => {
    try {
      const response = await getFileOrigin(params);
      fileDowload.value = response.data;
      type.value = response.headers["content-type"];
      status.value = response.status;
    } catch (error) {
      console.log("API 호출 시 오류가 있습니다");
    }
  };
  const getFileDowload = async (params: any) => {
    try {
      const response = await getFile(params);
      fileDowload.value = response.data;
      type.value = response.headers["content-type"];
      status.value = response.status;
    } catch (error) {
      console.log("API 호출 시 오류가 있습니다");
    }
  };
  const getListFileUpload = async (params: any) => {
    try {
      const response = await getFileUploaded(params);
      fileUploadedInfoList.value = response.data.data;
      status.value = response.status;
    } catch (error) {
      console.log("API 호출 시 오류가 있습니다");
    }
  };
  const deleteFileMng = async (id: number) => {
    try {
      const response = await deleteFile(id);
      status.value = response.status;
    } catch (error) {
      console.log("API 호출 시 오류가 있습니다");
    }
  };
  const dowLoadMultipleFile = async (params : any)=> {
    try{
      const response = await getMultipleFile(params);
      status.value = response.status;
      type.value = response.headers["content-type"];
      fileDowloadMuitl.value = response.data
      if (status.value && status.value == 200) {
        let data = fileDowloadMuitl.value;
        const url = window.URL.createObjectURL(new Blob([data], { type: type.value }));
        let fileNameFromServer = "";
    
          fileNameFromServer = "downloadedFile.zip";
    
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", fileNameFromServer);
        document.body.appendChild(link);
        link.click();
        link.remove();
        window.URL.revokeObjectURL(url);
      }
    } catch{
      
    }
  }

  return {
    status,
    addfile,
    getFileDowload,
    getListFileUpload,
    fileUploadedInfoList,
    fileDowload,
    type,
    deleteFileMng,
    getFileDowloadOrigin,
    dowLoadMultipleFile,
    fileDowloadMuitl
  };
});
