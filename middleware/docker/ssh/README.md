## SSH免密码登录
> 1、ssh的安装 yum install openssh-server
> 2、修改/etc/ssh/sshd_config去掉RSAAuthentication yes, PubkeyAuthentication yes的注释
> 3、重启service sshd restart
> 4、生成公钥私钥， ssh-keygen -t rsa. id_rsa私钥，id_rsa.pub公钥
> 5、导入公钥到认证文件，更改权限 cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
> 6、将客户端方的公钥发给服务端的authorized_keys里面