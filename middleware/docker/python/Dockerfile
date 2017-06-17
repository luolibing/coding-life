# Use an official Python runtime as a base image
FROM python:2.7-slim

MAINTAINER luolibing 397911353@qq.com

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

# Install any needed packages specified in requirements.txt
# 使用pip进行安装依赖
RUN pip install -r requirements.txt

# Make port 80 available to the world outside this container
# 暴露出80端口给外面的主机
EXPOSE 80

# Define environment variable
# 定义环境变量
ENV NAME World

# Run app.py when the container launches
CMD ["python", "app.py"]  # 这个地方，两个参数作为可执行程序执行了