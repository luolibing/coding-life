#### 查找文件并且重新命名
find . -type f ! -name '*.txt' -exec sh -c 'x="{}";mv "$x" "${x}_renamed"' \;