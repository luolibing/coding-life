/**
 * Created by luolibing on 2018/2/8.
 */
var app = {};
app.index = {
    template: '#index'
};

app.first = {
    template: '#first'
};

app.second = {
    template: '#second'
};

var vueRouter = new VueRouter({
    routes: [
        { path: '/first', component: app.first },
        { path: '/second', component: app.second },
        { path: '/', component: app.index}
    ]
});

// 路由
const app1 = new Vue({
    router: vueRouter
}).$mount('#app1');