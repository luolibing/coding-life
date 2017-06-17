package cn.tim.groovy.actor.service
/**
 * Created by LuoLiBing on 15/12/29.
 */
/*@Grab(group = "cn.tim.groovy.actor.service", module = "twitter4j", version = "2.0.10")
def recentTweets(Twitter api, String queryStr) {
    query = new Query(queryStr)
    query.rpp = 5
    query.lang = "en"
    tweets = api.search(query).tweets
    threadName = Thread.currentThread().name

    tweets.collect {
        "[${threadName}-${queryStr}] @${it.fromUser}: ${it.text}"
    }
}

def api = new TwitterImpl()
['#erlang','#scala','#clojure'].each {
    tweets = recentTweets(api, it)
    tweets.each {
        println "$it"
    }
}*/

