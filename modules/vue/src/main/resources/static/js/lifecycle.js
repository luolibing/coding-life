/**
 * Created by luolibing on 2018/2/8.
 */

var app = new Vue({
    el: '#app1',
    data: {
        message: "hello"
    },
    beforeCreate: function() {
        console.log("[beforeCreate]")
    },
    created: function () {
        console.log("[created] message is : " + this.message)
    },
    beforeMount: function () {
        console.log("[beforeMount]")
    },
    mounted: function () {
        console.log("[mounted]")
    },
    beforeUpdate: function() {
        console.log("[beforeUpdate]")
    },
    // 调用app.$destroy();的时候会调用
    beforeDestroy: function() {
        alert("[beforeDestroy]");
        console.log("[beforeDestroy]")
    },
    destroyed: function () {
        alert("[destroyed]");
        console.log("[destroyed]")
    }
});