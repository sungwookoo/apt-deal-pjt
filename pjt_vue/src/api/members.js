import api from "@/api/http-common";

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  api.defaults.headers["refresh-token"] =
    sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await api.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await api.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function searchUserList(userid, success, fail) {
  await api.get(`/user/search?userid=${userid}`).then(success).catch(fail);
}

async function getUserDetail(userid, success, fail) {
  await api.get(`/user/${userid}`).then(success).catch(fail);
}

async function createUser(user, success, fail) {
  await api.post(`/user/register`, user).then(success).then(fail);
}

export {
  login,
  findById,
  tokenRegeneration,
  logout,
  searchUserList,
  getUserDetail,
  createUser,
};
