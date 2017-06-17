FROM centos:centos6

ENV auther luolibing
ENV email 397911353@qq.com
ENV workdir="/home"
ENV relpath="$workdir/luo"
ENV currentUser root

# 缺省变量替换
MAINTAINER ${auther-luolibing} $email

WORKDIR $workdir

USER $currentUser

# 当auther变量存在时，使用默认的配置liuxiaoling
RUN touch ${auther+liuxiaoling}_1.txt

# 当auther变量不存在时，使用默认的配置luolibing
RUN echo ${auther-luolibing}_${email} >> ${auther+liuxiaoling}_1.txt


RUN mkdir -p $relpath

WORKDIR $relpath

# 这里copy整个上下文，即dockerfile所在目录， 所以说上下文目录特别重要，不能太大，避免发送过多的文件到daemon，同时避免因为copy或者add将其打包进image中
# 可以使用.dockerignore来忽略掉那些不想被加紧context上下文的文件
#COPY . .

# 这个时候没办法解析参数$HOME，需要使用exec方式
# RUN ["source", "$HOME/.bashrc"]

RUN /bin/bash -c 'source $HOME/.bashrc'

# CMD param1作为ENTRYPOINT的默认参数
#CMD ["bash"]
LABEL multi.label1="value1" multi.label2="value2" other="value3"

LABEL maintainer "397911353@qq.com"

# ADD指令 添加文件到指定目录
ADD *.jsp /home

# CMD echo "hello,world"
CMD echo "hello,world"


