#!/bin/sh
#
# 读取指定url的内容，直到该内容满足指定值或者超时

url=${1}
body=${2}
content=${3}
timeout=${4}

for (( i=0; i<${timeout}; i++ )); do
    result=`curl -s -d "${body}" ${url}`
    echo ${result}
    if [[ ${content} == ${result} ]]; then
        exit 0;
    fi;
    sleep 1
done;
echo "content [${result}] of url [${url}] does NOT equal ${content}";
exit 1