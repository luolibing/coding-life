/**
 * Created by luolibing on 2018/2/26.
 */
Vue.component('my-component', {
    template: '<div class="title template error">Hi, My component</div>'
});

var app1 = new Vue({
    el: '#app1',
    data: {
        originData: 'hello',
        isActive: true,
        hasError: false,
        activeClass: 'active',
        errorClass: 'error',
        activeColor: 'red',
        fontSize: 20,
        showTitle: true,
        showTemplate: true,
        loginType: 'username'
        // 也可以是一个计算属性
        // objectClass: {
        //     isActive: false,
        //     hasError: true
        // }
    },

    // 计算属性，将数据的计算逻辑从表达式中抽取. 计算属性会将结果放入到缓存中，再次获取的时候，会不变，直到关联的值发生变化，或者重新渲染页面
    // 计算属性，默认只有get方法，也可以手动添加set方法
    computed: {
        // reversedData计算属性也是响应式的，当originData变化时，也会跟着变化
        // reversedData: function() {
        //     return this.originData.split("").reverse().join('');
        // },
        reversedData: {
            get: function() {
                return this.originData.split("").reverse().join('');
            },
            set: function(val) {
                this.originData = val;
                console.log('set new val[' + val + ']')
            }
        },
        nowTime: function() {
            return Date.now();
        },
        objectClass: function() {
            var time = Date.now();
            return {
                active: time % 2 === 0,
                error: time % 2 !== 0
            };
        }
    },
    // 监控
    watch: {
        originData: function(val) {
            console.log("new originData = " + val)
        }
    }
});