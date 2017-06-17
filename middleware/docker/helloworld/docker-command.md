构建image镜像
docker build -t friendlyname .  # Create image using this directory's Dockerfile

启动容器
docker run -p 4000:80 friendlyname  # Run "friendlyname" mapping port 4000 to 80

将容器以后台方式启动
docker run -d -p 4000:80 friendlyname         # Same thing, but in detached mode

所有已经运行的容器
docker ps                                 # See a list of all running containers

停止指定的容器
docker stop <hash>                     # Gracefully stop the specified container

查看所有的容器，包括没有启动的
docker ps -a           # See a list of all containers, even the ones not running

shutdown指定的容器
docker kill <hash>                   # Force shutdown of the specified container

删除指定的容器
docker rm <hash>              # Remove the specified container from this machine

删除所有的容器
docker rm $(docker ps -a -q)           # Remove all containers from this machine

查看所有的镜像
docker images -a                               # Show all images on this machine

删除指定的镜像
docker rmi <imagename>            # Remove the specified image from this machine

删除所有已经退出的镜像
docker rmi $(docker images -q)             # Remove all images from this machine

登录docker registry
docker login             # Log in this CLI session using your Docker credentials

上传镜像到registry
docker tag <image> username/repository:tag  # Tag <image> for upload to registry

上传镜像
docker push username/repository:tag            # Upload tagged image to registry

从registry中启动镜像
docker run username/repository:tag                   # Run image from a registry