import http from "@/utils/http";

class CommonService {
  login(data: any): Promise<any> {
    return http.post("/auth/login", data);
  }

  logout(): Promise<any> {
    return http.get("/auth/logout");
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
    const { id, screenName } = info;
    return http.get(`/api/files/file/${id}/${screenName}`);
  }
}

export default new CommonService();
