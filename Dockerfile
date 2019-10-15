# 起動コマンド
# docker build -t <YOUR_NAME>/<APP_NAME> .
# 例 docker build -t hoge/todo_app_server .
# docker run -p 8080:3000 -it <IMAGE_ID>

# hub.docker.comから公式で用意されたイメージをベースとする
FROM node:12.11.1
# カレントディレクトリを app に
WORKDIR /app

COPY package*.json ./

RUN npm install

# アプリケーションのソースをバンドルする
COPY . .

EXPOSE 8080

# デフォルトで node が起動するので sh を代わりに起動
CMD ["sh"]
