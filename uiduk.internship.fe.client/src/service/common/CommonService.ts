import http from "@/utils/http";
import {useFetchData} from "@/utils/useFetchData";
import {PostType} from "@/stores/common/demoFetchData/demo.type";
import {END_POINTS_LOGOUT} from "@/stores/common/commonServices/commonServices.service";
import {removeUserInfo} from "@/utils/storage";

class CommonService {
  login(data: any): Promise<any> {
    return http.post("/auth/login", data);
  }

  async logout(): Promise<any> {
    await removeUserInfo()
    await useFetchData<PostType[]>(`${END_POINTS_LOGOUT}`, {
      method: "GET",
    });
  }

  menu(): Promise<any> {
    return http.get("/api/menu");
  }

  upload(formData): Promise<any> {
    return http.post("/api/files", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  }

  get(info): Promise<any> {
    const {id, screenName} = info;
    return http.get(`/api/files/file/${id}/${screenName}`);
  }
}

export default new CommonService();
