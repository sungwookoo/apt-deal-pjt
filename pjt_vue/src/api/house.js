import api from "@/api/http-common";

async function sidoNames(success, fail) {
  await api.get("/house/sido").then(success).catch(fail);
}

async function gugunNames(sidoCode, success, fail) {
  await api.get(`/house/gugun/${sidoCode}`).then(success).catch(fail);
}

async function dongNames(gugunCode, success, fail) {
  await api.get(`/house/dong/${gugunCode}`).then(success).catch(fail);
}

async function buildingInfo(dongCode, success, fail) {
  await api.get(`/house/searchBuilding/${dongCode}`).then(success).catch(fail);
}

async function buildingDetail(aptCode, success, fail) {
  await api.get(`/house/detail/${aptCode}`).then(success).catch(fail);
}

async function interestArea(userid, success, fail) {
  await api.get(`/house/interest/${userid}`).then(success).catch(fail);
}

async function insertInterest(info, success, fail) {
  await api.post(`/house/interest`, info).then(success).catch(fail);
}

async function deleteInterest(info, success, fail) {
  await api
    .delete(`/house/interest/${info.userId}/${info.code}`)
    .then(success)
    .catch(fail);
}

async function dealAvg(dongCode, success, fail) {
  await api.get(`/house/dongDealInfo/${dongCode}`).then(success).catch(fail);
}

export {
  sidoNames,
  gugunNames,
  dongNames,
  buildingInfo,
  buildingDetail,
  interestArea,
  insertInterest,
  deleteInterest,
  dealAvg,
};
