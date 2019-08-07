#!/usr/bin/env bash

kill `cat process.pid`

tail -f ./stdout.log
