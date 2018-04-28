/**
 * Created by luolibing on 2018/2/8.
 */

var app1 = new Vue({
    el: '#app1',
    data: {
        counter: 0
    },
    methods: {
        greet: function(event) {
            alert(this.counter);
            if(event) {
                alert(event.target.tagName)
            }
        }
    }
});