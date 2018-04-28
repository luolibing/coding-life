/**
 * Created by luolibing on 2018/2/8.
 */

var app1 = new Vue({
    el: '#app1',
    data: {
        msg: "初始化信息",
        html: '<a href="http://www.baidu.com">测试连接</a>',
        dynamicId: 'dynamicId_' + new Date().getTime(),
        isButtonDisabled: new Date().getTime() % 2 === 0,
        show: false,
        dynamicUrl: 'http://www.baidu.com'
    },
    methods: {
        sayHello: function () {
            alert("hello")
        },
        sayGood: function() {
            alert("good")
        }
    }
});