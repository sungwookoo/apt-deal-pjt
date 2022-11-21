import Vue from "vue";
import VueRouter from "vue-router";

import store from "@/store";

Vue.use(VueRouter);

const authUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo != null && token) {
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (
    !checkToken ||
    checkUserInfo === null ||
    (to.name === "UserList" && checkUserInfo.userType == 0)
  ) {
    alert("접근 권한이 없습니다.");
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("@/views/HomeView.vue"),
  },
  {
    path: "/kakao",
    name: "kakao",
    beforeEnter: authUser,
    component: () => import("@/views/KakaoTest.vue"),
  },
  {
    path: "/deal",
    name: "Deal",
    component: () => import("@/views/HouseDealView.vue"),
  },
  {
    path: "/qna",
    name: "Qna",
    component: () => import("@/views/QnaView.vue"),
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "QnaList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "detail",
        name: "QnaDetail",
        beforeEnter: authUser,
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "create",
        name: "QnaCreate",
        beforeEnter: authUser,
        component: () => import("@/components/board/BoardCreate.vue"),
      },
      {
        path: "modify",
        name: "QnaModify",
        beforeEnter: authUser,
        component: () => import("@/components/board/BoardModify.vue"),
      },
    ],
  },
  {
    path: "/notice",
    name: "Notice",
    component: () => import("@/views/NoticeView.vue"),
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "NoticeList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "detail",
        name: "NoticeDetail",
        beforeEnter: authUser,
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "create",
        name: "NoticeCreate",
        beforeEnter: authUser,
        component: () => import("@/components/board/BoardCreate.vue"),
      },
      {
        path: "modify",
        name: "NoticeModify",
        beforeEnter: authUser,
        component: () => import("@/components/board/BoardModify.vue"),
      },
    ],
  },
  {
    path: "/search",
    name: "Search",
    component: () => import("@/views/SearchView.vue"),
  },
  {
    path: "/user",
    name: "User",
    redirect: "/",
    component: () => import("@/views/UserView.vue"),
    children: [
      {
        path: "management",
        name: "UserManagement",
        redirect: "/",
        component: () => import("@/components/user/UserManagement.vue"),
        children: [
          {
            path: "login",
            name: "UserLogin",
            component: () => import("@/components/user/UserLogin.vue"),
          },
          {
            path: "create",
            name: "UserCreate",
            component: () => import("@/components/user/UserCreate.vue"),
          },
          {
            path: "modify",
            name: "UserModify",
            beforeEnter: authUser,
            component: () => import("@/components/user/UserModify.vue"),
          },
          {
            path: "detail",
            name: "UserDetail",
            beforeEnter: authUser,
            component: () => import("@/components/user/UserDetail.vue"),
          },
          {
            path: "find",
            name: "UserFind",
            component: () => import("@/components/user/UserFind.vue"),
          },
        ],
      },
      {
        path: "list",
        name: "UserList",
        beforeEnter: authUser,
        component: () => import("@/components/user/UserList.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
