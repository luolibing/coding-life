package cn.tim.groovy.collection

import org.junit.Test

/**
 * Created by TIM on 2015/7/26.
 */
class List {

    @Test
    void listLiterals1() {
        def list = [1,2,3,4,5]
        assert list.get(2) == 3
        assert list[2] == 3
        assert list instanceof  java.util.List

        def emptyList = []
        assert emptyList.size() == 0
        emptyList.add(5)
        assert emptyList.size() == 1
    }

    @Test
    void listLiterals2() {
        def list1 = ['a', 'b', 'c']
        def list2 = new ArrayList<String>(list1)
        assert list1 == list2

        def list3 = list1.clone()
        assert list3 == list1
    }

    @Test
    void collectionObject1() {
        def list = [5, 6, 7, 8]
        assert list.size() == 4
        assert list.class == ArrayList

        assert list[2] == 7
        assert list.getAt(2) == 7
        assert list.get(2) == 7

        list[2] = 9
        assert list == [5, 6, 9, 8]

        list.putAt(2, 10)
        assert list == [5, 6, 10, 8]
        assert list.set(2, 11) == 10
        assert list == [5, 6, 11, 8]

        assert ['a', 1, 2, boolean, 2.5, 2.5d, null, 9 as byte]

        assert[1,2,3,4,5][-1] == 5
        assert[1,2,3,4,5][-2] == 4

        assert[1,2,3,4,5].getAt(-2) == 4
        try{
            [1,2,3,4,5].get(-1)==4
            assert false
        } catch (e) {
            assert e instanceof ArrayIndexOutOfBoundsException
        }
    }

    @Test
    void listAsBoolean() {
        assert ![]
    }

    @Test
    void listIterating1() {
        [1,2,3].each {
            println "item:${it}"
        }

        ['a', 'b', 'z', 'hello'].eachWithIndex{ it,  i ->
            println("$i:$it")
        }
    }

    @Test
    void listOperate1() {
        assert [1, 2, 3].collect { it*2 } == [2, 4, 6]
        assert [1, 2, 3]*.multiply(2) == [2, 4, 6]

        def list = [0]
        assert [1, 2, 3].collect(list) { it*2 } == [0, 2, 4, 6]
    }

    @Test
    public void maniPulatingList() {
        assert [1,2,3].find{ it > 2 } == 3
        assert [1, 2, 3].findAll { it > 1} == [2, 3]
        assert ['a', 'b', 'c', 'd', 'e'].findIndexOf {
            it in ['c', 'e', 'g']
        } == 2

        assert ['a','b','c','d','e'].indexOf("c") == 2
        assert ['a','b','c','d','e'].indexOf("z") == -1
        assert ['a','b','c','d','e'].lastIndexOf('c') == 2

        assert [1, 2, 3].every { it<5 }
        assert ![1, 2, 3].every { it<3 }
        assert ![1, 2, 3].any { it>3 }

        println ([1,2,3,4,5,6,7,8,9,10].sum())
        println (['a',1,2,3,'c'].sum())

        println (['a',1,2,3,'c'].sum {
            it == 'a'?20:it=='b'?21:it=='c'?23:it
        })

        assert ['a', 'b', 'c', 'd', 'e'].sum {
            ((char)it) - ((char)'a')
        } == 10
        assert ['a', 'b', 'c'].sum() == "abc"
        assert [['a', 'c', 'd'], ['z', 'y', 't']].sum() == ['a', 'c', 'd', 'z', 'y', 't']
        assert [].sum(100) == 100
        assert [1, 2, 3].sum(100) == 106

        assert ([1, 2, 3].join('-')) == '1-2-3'
        println ([1, 2, 3, 4, 5, 6, 7, 7, 8].inject('counting:'){ str, item ->
            str + item
        })
        assert [1, 2, 3].inject(1) { str, item ->
            str + item
        } == 7
    }

    @Test
    public void sort() {
        assert [1, 2, 3, 10,6].max() == 10
        assert [2, 3, 1, 6, 10].min() == 1
        println ([1, 2, 'a', 1 ].max())
        def list1 = ['zbcd', 'y', '1234', 'zfd'];
        assert list1.max{ it.size()} == 'zbcd'
        assert list1.min { it.size()} == 'y'

        def list2 = [1, 4, 2, 0]
        Comparator mc = { a, b ->
            a == b ? 0: (a< b? -1: 1)
        }
        assert list2.max(mc) == 4
        assert list2.min(mc) == 0

        Comparator mc1 = { a, b ->
            a == b ? 0 : (Math.abs(a)<Math.abs(b) ? -1 : 1)
        }
        def list3 = [7, 4, 9, -6, -1, 11, 2, 3, -9, 5, -13]
        assert list3.max(mc1) == -13
        assert list3.min(mc1) == -1

        assert list3.max { a, b ->
            a.equals(b) ? 0 : Math.abs(a) < Math.abs(b) ? -1 : 1
        } == -13
    }

    @Test
    public void addOrRemove() {
        def list = []
        assert list.empty

        list << 5
        assert list == [5]

        list << 7 << 'i' << false
        assert list.size() == 4

        list << ['m', 'n']
        assert list.size() == 5

        assert ([1, 2] << 3 << [4, 5]) == [1, 2, 3, [4, 5]]
        assert ([1, 2, 3] << 4) == ([1, 2, 3].leftShift(4))

        assert [1, 2] + 3 + [4, 5] == [1, 2, 3, 4, 5]
    }

    @Test
    public void setOperations() {
        assert 'a' in ['a', 'b', 'c']

        assert [1, 2, 5, 4, 6, 10].count {
            it % 2 == 0
        } == 4
    }

    @Test
    public void saveOrUpdate() {
        assert ([] << 'abcd' << 'luolibing') == ['abcd', 'luolibing']
        assert ([1, 2].plus(3) == [1, 2, 3])

        def a = [1]
        a += 2
        a += 3
        assert a == [1, 2, 3]
        assert [1, *[2, 3]] == [1, 2, 3]

        def b = [1, 2]
        b.add(2, 3)
        assert b == [1, 2, 3]

        b.addAll(3, [4, 5])
        assert b == [1, 2, 3, 4, 5]

        def c = ['a', 'b', 'c']
        c[4] = 'd'
        assert c == ['a', 'b', 'c', null, 'd']

        assert (['a', 'b', 'c'] - 'c') == ['a', 'b']
        assert (['a', 'b', 'd', 'z', 'a', 'c'] - 'a' - 'b') == ['d', 'z', 'c']
        assert (['a', 'b', 'd', 'z', 'a', 'c'] - ['a' , 'b']) == ['d', 'z', 'c']

        def d = [1, 2, 3, 4, 5, 3, 2, 1]
        assert (d -= 3) == [1, 2, 4, 5, 2, 1]
        assert (d -= [2, 4]) == [1, 5, 1]

        def e = [1, 2, 3, 4, 5, 3, 2, 1]
        assert e.remove(5) == 3

        def f= ['a','b','c','b','b']
        assert !f.remove('e')
        assert f.remove('a')

        f.clear()
        assert f == []
    }

    @Test
    public void contain() {
        assert 'a' in ['a', 'b', 'c']
        assert ['a', 'b', 'c'].contains('a')

        assert ['a', 'b', 'c', 'b', 'c', 'b', 'c'].count('b') == 3
    }

     @Test
     public void setOptions() {
         assert [1, 2, 4, 6, 8, 10, 12].intersect([1, 3, 12]) == [1, 12]
         assert [1, 2, 3].disjoint([4, 5, 6])
         assert ![1, 2, 3].disjoint([1, 4, 5])
     }

    @Test
    public void sort1() {
        println ([6, 3, 9, 2, 7, 1, 5].sort())
        def list = ['abc', 'z', 'xyzuvw', 'Hello', '321']
        println list.sort {
            it.size()
        }

        def list2 = [7, 4, -6, -1, 11, 2, 3, -9, 5, -13]
        println (list2.sort { a,b ->
            a == b ? 0 :Math.abs(a) < Math.abs(b) ? -1 : 1
        })

        Comparator mc = { a, b ->
            a == b ? 0 :Math.abs(a) < Math.abs(b) ? -1 : 1
        }
        // JDK8��֧��
        //println list2.sort(mc)
        def list3 = [6, -3, 9, 2, -7, 1, 5]
        Collections.sort(list3)
        println list3

        Collections.sort(list3, mc)
        println list3

        assert [1, 2, 3] * 3 == [1, 2, 3, 1, 2, 3, 1, 2, 3]
        assert [1, 2].multiply(2) == [1, 2, 1, 2]

        assert Collections.nCopies(2, [1, 2]) == [[1, 2], [1, 2]]
    }

    @Test
    public void array() {
        def arr = [1, 2, 3, 4]
        println arr[0..2]
    }
}
