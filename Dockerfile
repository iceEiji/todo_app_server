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
