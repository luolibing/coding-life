package cn.tim.groovy.closure

import org.junit.Test

/**
 * Created by TIM on 2015/8/12.
 */
class DelegateClosure {

    @Test
    void run() {
        def cl = { getDelegate() }
        def cl2 = { delegate }
        assert cl() == cl2()
        assert cl() == this
        def enclosed = {
            { -> delegate}.call()
        }
        assert enclosed() == enclosed
    }

    class Person {
        String name
    }

    class Thing {
        String name
    }

    @Test
    void run1() {
        def p = new Person(name: 'Tim')
        def t = new Thing(name: "Bing")
        def upperCasedName = { delegate.name.toUpperCase() }
        upperCasedName.delegate = p
        println upperCasedName()
        upperCasedName.delegate = t
        println upperCasedName()

        def target = p
        def upperCasedNameUsingVar = { target.name.toUpperCase() }
        assert upperCasedNameUsingVar() == 'TIM'
    }

    @Test
    void delegationStrategy() {
        def p = new Person(name: 'Liu')
        def cl = { name.toUpperCase() }
        cl.delegate = p
        println cl()
    }

    class Person1 {
        String name
        def pretty = { "my name is $name" }
        String toString() {
            pretty()
        }
    }

    class Thing1 {
        String name
    }

    @Test
    void run2() {
        def p = new Person1(name: 'Luominghao')
        def t = new Thing1(name: 'Heguixiang')
        println p.toString()
        p.pretty.delegate = t
        println p.toString()

        p.pretty.resolveStrategy = Closure.DELEGATE_FIRST
        println p.toString()
    }

    class Person2 {
        String name
        int age
        def fetchAge = { age }
    }

    class Thing2 {
        String name
    }

    @Test
    void run3() {
        def p = new Person2(name: 'Jessica', age: 42)
        def t = new Thing2(name: 'Tim')
        def cl = p.fetchAge
        cl.delegate = p
        println cl()

        cl.delegate = t
        println cl()

        cl.resolveStrategy = Closure.DELEGATE_ONLY
        cl.delegate = p
        println cl()

        cl.delegate = t
        try {
            cl()
            assert false
        } catch (MissingPropertyException ex) {

        }
    }


    @Test
    void call() {

        def text = "curl 'https://sms.console.aliyun.com/telecom/operator/listStatisticsDetail.json?__preventCache=1486606212371&pageNo=1&pageSize=20&queryTime=2017-02-09&recNum=13664231769' -H 'pragma: no-cache' -H 'accept-encoding: gzip, deflate, sdch, br' -H 'accept-language: zh-CN,zh;q=0.8,en;q=0.6' -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36' -H 'accept: application/json, text/plain, */*' -H 'cache-control: no-cache' -H 'authority: sms.console.aliyun.com' -H 'cookie: cna=TJmoDvtl2C4CAdOdr9K5vqgW; aliyun_choice=CN; industry-tip=true; consoleNavVersion=1.4.43; JSESSIONID=FN566VA1-WPIHAROSRGSEH34LFIW63-I6ZLPXYI-GR31; login_aliyunid=\"unda****@aliyun.com\"; login_aliyunid_ticket=*0vCmV8s*MT5tJl3_1\$$wsfxDZHFC0kbpmTIYR3k97wn8XGOM4fBGgCwnrCjD0wf_kNpoU_BOTwChTBoNM1ZJeedfK9zxYnbN5hossqIZCr6t7SGxRigm2Cb4fGaCdBZWIzmgdHq6sXXZQg4KFWufyep0; login_aliyunid_csrf=_csrf_tk_1270786604020490; login_aliyunid_pk=1296825402699921; hssid=1ZrJd7rMyUiPdIP1Mm90nyA1; hsite=6; aliyun_country=CN; aliyun_site=CN; TELECOM_JSESSIONID=G1666P81-6LRG0T3YLSEW50GZYE773-LJHNPXYI-BR6; _telecom_session0=qEN%2B4%2F9FDRR46%2FNmKAZORVpi2bGMkxN9GWAgFhJ1pjrWkvi349R76tbD4QWogUhLp2OjJuucUu%2B64SCINtsXPVRQrDhBazOg%2FGl4wYRXgDybH9Mj%2FXzTJSfIN4oE60ptFwruWumCdHIPLoTvJcUFJqw3HR54xC6QalPkbZCE%2F61v%2FZuHmNjZgPK%2Fj6qic%2B%2FKJl%2B9Cd%2BHxOVlp0Vz2%2BkX5WQid7x8DNcyXfl1w%2BbPDpKC8QSbeGZ7EQiqF0sxeScQ6jhTxXJ1RW5M7HiaV2DVHkrPBG%2B%2FWCm5%2FLLFO2fQn3Cutcj3GC6yH0OF4wzx3xlJ617S88a5TDPoz7muHtCx5A%3D%3D; _HOME_JSESSIONID=WM566N91-JPMH8QJ3RLWMSWT45IRP1-C2LNPXYI-AWE4; _home_session0=qEN%2B4%2F9FDRR46%2FNmKAZORWaDgTqefFG8g5w6kdflmKider3IUvOrgDpEGXSNsFqlSGgPSOCQRIYiwSfqxRkOcQoYH4TXZ2XHu8mu40E44WxMLVBG6b4v5i8IIZy6bjUUMr%2FF8288UXEOoRsRnDVqIKRf3d5vJCPHITgeiCNbFMfbnHfs5zrUc4Muoqikg7GjFHCfApTiK1zEn8%2BYdl4Urj90Q2%2Bk50cENylGtJSG7ku4EQKca6IcdmGR3Tlt4ASKyCEYTEYhzCoMK4pbg%2BoP7XXaZ2aSKRQ0ZqTXQSh6eFh%2Funmig7ELK%2Fq8WsysehHuifHNZeEJ%2FetjSy7%2BPD%2Bg7A%3D%3D; _ga=GA1.2.1747182080.1445238237; _yundun_session0=cn6Yh07HBG9vUa26Mq1w7OvfDI3Ipa3JLJNwN93xqCt1%2F3cBl4eRDZz5nRvAbbhcJR8ow6T3dU33LJHekBic4LyudxSBEX%2BP9OE1ZKhh%2B8hnCt0vGMfnlzNVs3EwsOZNMTfg7koFifL5tGT0wUoa%2Fb4L3a%2B%2FWPtIaZaWEsIy5iJ0z3Xzd%2BGUdM9cWUtVYSERaSJn%2FzxKsEiEIumr67qMJaUdIVqft6LnyVqIWes5A8y7rpXrt6niK6KUf82LS0DjcWdrAW%2BQ3kd8DOE03FsMCW6VH0mFJNBcRESOZmBbTU5t0NoujCWwNxneYEwJCVRuDCB0Um7EnujEqTbnn4%2F0d3NdlQ8grCjbtGRmRpRnBM%2BS%2Be9q5BSXr0F4KtQ3NM6SSYKYXbkA5egmeRmIAibdGOfmaPLw1%2FQ5gKeeptPMmpNP%2B%2B%2FkO1Zoc7XxN1wqXR3c9RO8m6tikC8MUR6lBmKqzATRVjS6rOnBDlm1znhoPx0ybdbB84%2FGyttV4c9bvDIuNLlIvO4H0um2TPUcPhNFsZ9nOG1qyPhw2PRNB%2FpCh8HdfNQtexegx2r5YHWEbPsaFelOz%2BjYeQVtsTibgMaGKn0JD1tVEsITENVz5l6HJPz%2F9Q8IJiZUWtJeq4hwipThHSqqSpecwSvG7qJ63oBr46dSrt8wzI0ptbwpfqw89sIoJ93PuOnFcbTu8pnhlrJ1mGCJaYCnnk04LAKfqYNgu0p4Qyfavz0nO%2Fo0RSBMIr4c8xjGafr8z1PFQsaUdA85; activeRegionId=; _umdata=BA335E4DD2FD504F54800CFEEEAD169E6B62E49BFF22419D257BE5382EC85FDFA817D8F30561DCEBCD43AD3E795C914C8794CA1182D9543A3D690C1FB72B8C97; l=Av7-IgYz3Xioi5hZSthCKqM/zh5Pu8LB; isg=AoiIQ9rJRDCxAadQtvzgxDRUWfDWHOhLcEFSEEI2UIOOHV2H8UMey5Vb40KX' -H 'referer: https://sms.console.aliyun.com/' --compressed"
                .execute().text
        println text
    }

}
