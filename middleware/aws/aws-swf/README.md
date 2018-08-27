## AWS SWF
https://docs.aws.amazon.com/zh_cn/amazonswf/latest/developerguide/swf-dg-dev-amzn-swf.html
> 亚马逊的simple workflow使用了平台模式，封装了工作流程驱动，任务驱动等功能。
我们只需要关注自己要实现的功能与业务逻辑，将流程的驱动，任务的重试都交给工作流平台来管理。
swf定义几个概念
工作流域定义：
流程执行：
流程决策：

> swf的实现思想还是值得借鉴，但是具体使用过于依赖aws平台，所有的任务或者流程都需要注册到swf平台上，而且很多功能依赖于aws-sdk，如果项目使用aws的很多技术栈可能会比较合适。
