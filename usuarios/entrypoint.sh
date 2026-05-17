#!/bin/sh
JDBC_URL="jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DB}?serverTimezone=CET&useSSL=false&allowPublicKeyRetrieval=true"
export JDK_JAVA_OPTIONS="${JDK_JAVA_OPTIONS} -Djavax.persistence.jdbc.url=${JDBC_URL} -Djavax.persistence.jdbc.user=${MYSQL_USER} -Djavax.persistence.jdbc.password=${MYSQL_PASSWORD}"
exec "$@"
