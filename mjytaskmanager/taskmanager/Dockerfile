# 使用 Nginx 官方基础镜像
FROM nginx:alpine

# 将构建好的前端静态文件复制到 Nginx 默认的静态文件目录
COPY dist/ /usr/share/nginx/html

# 可选：自定义 Nginx 配置
# COPY nginx.conf /etc/nginx/nginx.conf