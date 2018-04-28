/**
 * Created by luolibing on 2018/2/7.
 */
var data = {a: 1};
// Object.freeze(data);
var app = new Vue({
    el: '#app1',
    data: function () {
        return data;
    }
});

// 等同于 data.a
// app.a = 2;


// data.a = 50;


var dataB = {a: 1};
var app2 = new Vue({
    el: "#app2",
    data: dataB
});

// vue实例上的实例属性与方法，使用前缀$来区分代替，与用户定义的属性区分开
alert(app2.$el);
alert(app2.$data);
app2.$watch('a', function(newVal, oldVal){
    // 更改
});