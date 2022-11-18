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

export { sidoNames, gugunNames, dongNames };
