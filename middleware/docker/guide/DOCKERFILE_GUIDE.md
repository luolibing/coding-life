### DockerFile的build
> 指令: docker build .
> 这个地方并不是将指令发给cli，而是直接发给了daemon，将PATH或者URL上下文发给daemon，然后根据Dockerfile中的内容来构建image
> tips: 一般情况下context应该为空的，只有一个dockerFile，严禁在一个大的目录下使用docker build，因为会将整个目录发送给daemon，
虽然不是将其打包进image,但是讲整个目录发给daemon会造成很大的问题。要将本地文件打进image,需要使用COPY指令，如果想忽略一些文件，
可以使用.dockerignore来进行声明忽略。

> docker build -f /your/path/ . 在指定的目录下构建image
> docker build -t tim/centos:3.6 . 指定构建好的image的名称有tag
> docker daemon会一步一步的执行Dockerfile, 如有必要每一步都会提交到一个新的image中，直到最终的image，中间步骤的image都会被删除掉
> 如果有可能，docker build会重用中间的image缓存，来加快构建的过程，所以当构建完一次image之后，再次构建，几乎是秒级构建，因为可以使用缓存。 指定某个image的构建缓存，可以使用--cache-from

-**注释**
> 只有以#开头的才是注释，要是#在句中，不算注释。

-**解析器指令**
> 解析器指令必须位于Dockerfile的顶行，即使是顶行有注释或者空行都不行， PS:没搞清楚这个解析器指令用来干嘛的.

-**ENV**
> ENV workdir "/home" 指定环境变量
> 使用的时候使用 ${workdir} 或 $workdir，还可以标注当存在或者不存在时的默认值。${workdir-luolibing}不存在时默认，${workdir+luolibing}存在时默认。
一个ENV可以指定多个环境变量，也可以使用多个ENV指令，但是使用多个指令会创建多个中间层缓存，建议将多个合并为一个。
可以在启动时修改ENV环境变量 docker run --env key=value

-**FROM**
> 来自于某个基础image，1 必须位于第一行非注释指令 2 允许在一个dockerfile钟出现多次，为了创建多个image
> tag和digest都是可选的，如果不指定tag，默然使用latest

-**RUN**
> RUN指令的两种方式：(linux默认是使用/bin/sh)
> 1 RUN <command> 例如 RUN mkdir -p $workdir
> 2 RUN ["mkdir", "-p", $workdir]

> 说明： RUN指令每执行一次，将在当前镜像的基础上创建出一个新的层，并且提交结果，这个结果image将作为下一步的基础image.
如果调用的指令不是/bin/sh指令，在需要使用exec的方式，例如RUN /bin/bash -c "echo $NAME"这样可以避免参数不替换的情况.
docker run会产生一些cache，这些cache并不会自动失效，导致某些指令在下一次build时被跳过，例如apt-get dist -upgrade -y这种更新下次调用也不会发生。
可以使用docker build --no-cache来关闭掉缓存。


-**CMD**
> CMD有三种使用方式：
> 1 CMD ["executable", "param1", "param2"] 以执行表单的方式执行，这也是推荐的方式. 当run的时候，自动执行这些指令
    exec form执行方式，会
> 2 CMD ["param1", "param2"] 作为ENTRYPOINT的默认参数
> 3 CMD command param1 param2 作为shell脚本表单，不会正常执行shell,例如CND ["echo", "$HOME"]，参数兵不能正常替换，需要使用sh执行.
CMD在dockerFile中只能有一个，如果声明了多个，只有最后一个起作用.
CMD的主要目的是为执行容器提供默认值，这些默认值可以包括执行一个可执行文件，或者他们可以省略可执行指令，只是一些参数，这种情况下，必须声明一个ENTRYPOINT
docker run -i -t hello /bin/bash。 如果这个地方不声明/bin/bash的时候，就会执行默认的CMD中可执行的参数
CMD和RUN的区别，CMD在创建镜像时并不会执行，而RUN在创建镜像时会执行。

-**LABEL**
> 1 LABEL可以给image镜像添加一些源数据，然后label以key=value形式组成.
LABEL version="1.0"
LABEL description="this text illustrates \
that label-values can span."
> 2 可以将多个label组合到一个单独的LABEL中，推荐这样使用，因为一个LABEL会创建一个层，多个层会导致效率低下。
LABEL multi.label1="value1" multi.label2="value2" other="value3"，会将这些label添加到image的labels信息中，使用docker inspect imageName

-**MAINTAINER**
> 该属性已经过期弃用，建议使用LABEL来代替LABEL maintainer "SvenDowideit@home.org.au"

-**EXPOSE**
> EXPOSE指令通知Docker容器在运行时监听的端口，要使得expose生效，的使用-p或者—P来将docker容器中的端口映射到本机端口
例如，将docker容器上的3306端口映射到本机的3307,docker run -t -i -p 3307:3306 tim/mysql /bin/bash

-**ADD**
> ADD有两种方式：
1 ADD <src>... <dest>
2 ADD ["<src>",... "<dest>"]
ADD指令用于拷贝新文件目录或者远端URL到image的目标位置
