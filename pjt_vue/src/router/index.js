import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("@/views/HomeView.vue"),
  },
  {
    path: "/kakao",
    name: "kakao",
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
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "create",
        name: "QnaCreate",
        component: () => import("@/components/board/BoardCreate.vue"),
      },
      {
        path: "modify",
        name: "QnaModify",
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
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "create",
        name: "NoticeCreate",
        component: () => import("@/components/board/BoardCreate.vue"),
      },
      {
        path: "modify",
        name: "NoticeModify",
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
            component: () => import("@/components/user/UserModify.vue"),
          },
          {
            path: "detail",
            name: "UserDetail",
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
