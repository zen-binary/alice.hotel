import {getUserInfo, removeUserInfo, setUserInfo} from "@/utils/storage";
import commonService from "@/service/common/CommonService";
import {PostType} from "@/stores/common/demoFetchData/demo.type";
import {useFetchData} from "@/utils/useFetchData";
import {END_POINTS_LOGIN} from "@/stores/common/commonServices/commonServices.service";
import {SUCCSESS_STATUS} from "@/constants/screen.const";

export default async function authMiddleware({ to, from, next }) {
  console.log("auth middleware");
  // Call loading on store

  //check token
  const router = useRouter()
  const token = to.query.token
  const role = to.query.role

  if(token){
    const obj = {
      userId: token,
      loginDiv: role
    }

    const {
      dataFetch,
      statusFetch,
      error,
      isLoading,
      fetchData
    } = await useFetchData<PostType[]>(`${END_POINTS_LOGIN}`, {
      method: "POST",
      body: obj
    });
    if (statusFetch.value === SUCCSESS_STATUS) {
      setUserInfo(dataFetch.value)
      await router.replace('/')
    }

  }

  // Check userInfo
  const userInfo = getUserInfo();

  // If userInfo is undefined and the target route is not 'Login', redirect to 'Login'
  if (!userInfo && to.name !== "Login") {
    await commonService.logout();
    await removeUserInfo()
    window.location.href = `${import.meta.env.VITE_DOMAIN_SITE_CLIENT}`;
  }
  // If userInfo is present and the target route is 'Login', redirect to 'Home'
  if (userInfo && to.name === "Login") {
    return next({ name: "Home" });
  }

  return next();
}
