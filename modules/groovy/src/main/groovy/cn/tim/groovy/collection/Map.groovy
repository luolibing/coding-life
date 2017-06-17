package cn.tim.groovy.collection

import org.junit.Test

/**
 * Created by TIM on 2015/7/31.
 */
class Map {

    @Test
    public void iterals() {
        def map = [name: 'luolibing', likes: 'basketball', age: 28]
        println map
        assert map.get('name') == 'luolibing'
        assert map['age'] == 28
        assert map instanceof java.util.Map

        def emptyMap = [:]
        assert emptyMap.size() == 0
        emptyMap.put('name', 'liuxiaoling')
        assert emptyMap.size() == 1
        assert emptyMap.get('name') == 'liuxiaoling'

        def a = 'tim'
        def ages = [a: 34]
        assert ages['tim'] == null
        assert ages['a'] == 34

        ages = [(a): 34]
        assert ages['tim'] == 34

        def map1 = [
                simple : 123,
                complex: [a: 1, b: 2]
        ]
        def map2 = map1.clone()
        assert map1.get('simple') == map2.get('simple')
        map2.get('complex').put('c', 3)
        map2.put('simple', 456)
        assert map1.get('complex').get('c') == 3

        println map1.simple
        println map1.complex.c

        map2.list = 1
        println map2.list
    }

    @Test
    void properties() {
        def map = [name: 'Gromit', likes: 'cheese', id: 1234]
        assert map.class == null
        assert map.get('class') == null
        assert map.getClass() == LinkedHashMap

        def map1 = [1      : 'a',
               (true) : 'p',
               (false): 'q',
               (null) : 'x',
               'null' : 'z']
        assert map1.containsKey(1)
        assert map1.containsValue('a')
        assert map1.true == null
        assert map.get(true) == 'p'
    }

    @Test
    void iteratMap() {
        def map = [
                Bob: 33,
                Tim: 54,
                Liu: 28
        ]
        map.each { entry ->
            println "name: ${entry.key},Age:${entry.value}"
        }

        map.eachWithIndex { entry, i ->
            println "$i - Name：$entry.key , age: $entry.value"
        }

        map.each { key, value ->
            println "name: ${key},Age:${value}"
        }

        map.eachWithIndex { key, value, i ->
            println "$i - Name：$key , age: $value"
        }
    }

    @Test
    void manipulatMap() {
        def defaults = [1: 'a', 2: 'b', 3: 'c', 4: 'd']
        def overrides = [2: 'z', 5: 'x', 13: 'x']
        def result = new LinkedHashMap<>(defaults)
        result.put(15, 't')
        result[17] = 'u'
        result.putAll(overrides)
        println result

        assert defaults.get(1) == 'a'
        defaults.clear()
        assert defaults == [:]
    }

}
