/**
 * Created by luolibing on 2018/2/7.
 */
var app = new Vue({
    el: '#app',
    data: {
        message: 'hello, world'
    }
});

var app1 = new Vue({
    el: '#app-1',
    data: {
        message: '页面加载于 ' + new Date().toLocaleString()
    }
});

var app2 = new Vue({
    el: '#app-2',
    data: {
        seen: true
    }
});