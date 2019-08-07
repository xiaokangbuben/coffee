#!/usr/bin/env bash

nohup java -jar coffee.jar --spring.config.location=classpath:application.yml,./conf.yml > stdout.log 2>&1 &
echo $! > process.pid

sleep 1
tail -f ./stdout.log
