/**
 * Created by luolibing on 2018/2/7.
 */
// 全局组件
Vue.component('my_component', {
    template: '<h3>my component!</h3>'
});

Vue.component('counter', {
    template: '<button @click="counter += 1">{{counter}}</button>',
    // 组件数据，必须是一个function
    data: function () {
        return {counter: 0};
    }
});

Vue.component('child', {
    // 注册数据属性
    props: ['message', 'myAuthor', 'myMessage', 'age'],
    //
    template: '<p>标题：<span>{{message}}</span>, 作者：<span>{{myAuthor}}</span>, 年龄：<span>{{age}}</span>{{myMessage}}</p>'
});

Vue.component('money', {
    props: {
        money: {
            type: Number,
            required: true
        }
    },
    template: '<input type="text" :value="money" />'
});

Vue.component('button-increment', {
    // 双击调用incrementCounter
    template: '<button v-on:click="incrementCounter">{{counter}}</button>',
    // 组件数据，必须是一个function
    data: function() {
        return { counter: 0 }
    },
    methods: {
        // 自定义事件 $emit()触发increment事件, 于是在button上绑定自定义事件 v-bind:increment="incrementTotal"即可
        incrementCounter: function() {
            this.counter += 1;
            this.$emit('increment');
        }
    }
});

// 异步组件
Vue.component('async-component', function(resolve, reject) {
    setTimeout(function() {
        resolve({
            template: '<div>I am async component !</div>'
        })
    }, 1000);
});

var bye = { template: '<h3>GoodBye!</h3>'};

var app = new Vue({
    el: '#app1',
    data: {
        myMessage: '',
        total: 0,
        someValue: ''
    },
    // 局部组件
    components: {
        'bye': bye
    },
    methods: {
        // 子组件的事件传递到了父组件当中
        incrementTotal: function() {
            this.total += 1;
        }
    }
});