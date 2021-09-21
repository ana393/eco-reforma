#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_key_ssh \
  target/eco-reforma-1.0.0-SNAPSHOT.jar \
  root@localhost:/home/usuario/

echo 'Restart server...'

ssh -i ~/.ssh/id_key_ssh eroot@localhost << EOF
pgrep java | xargs kill -9
nohup java -jar eco-reforma-1.0.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'



