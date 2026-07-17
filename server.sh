#!/bin/sh

APP_NAME=hmmp-admin.jar
APP_MODULE=hmmp-admin
APP_PORT=${APP_PORT:-8080}
APP_ARGS=${APP_ARGS:---server.port=$APP_PORT}
MAVEN_CMD=${MAVEN_CMD:-mvn}
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
LOG_DIR="$APP_HOME/logs"
LOG_PATH="$LOG_DIR/$APP_NAME.log"
DEV_LOG_PATH="$LOG_DIR/dev.log"
GC_LOG_PATH="$LOG_DIR/gc.log"

JVM_OPTS="-Dname=$APP_NAME -Duser.timezone=Asia/Shanghai -Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -Xlog:gc*:file=$GC_LOG_PATH:time,uptime,level,tags:filecount=5,filesize=20M -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC"

usage() {
    echo "Usage: $0 {start|dev|stop|restart|status|logs}"
}

is_java17() {
    [ -x "$1" ] || return 1
    "$1" -version 2>&1 | awk -F '"' '/version/ {found = 1; ok = ($2 ~ /^17(\.|$)/)} END {exit (found && ok ? 0 : 1)}'
}

find_java17() {
    if [ -n "$JAVA_CMD" ] && is_java17 "$JAVA_CMD"; then
        echo "$JAVA_CMD"
        return 0
    fi

    if [ -n "$JAVA_HOME" ] && is_java17 "$JAVA_HOME/bin/java"; then
        echo "$JAVA_HOME/bin/java"
        return 0
    fi

    if command -v /usr/libexec/java_home >/dev/null 2>&1; then
        JAVA17_HOME=$(/usr/libexec/java_home -v 17 2>/dev/null)
        if [ -n "$JAVA17_HOME" ] && is_java17 "$JAVA17_HOME/bin/java"; then
            echo "$JAVA17_HOME/bin/java"
            return 0
        fi
    fi

    for CANDIDATE in \
        /usr/local/java/jdk-17/bin/java \
        /usr/local/jdk-17/bin/java \
        /usr/lib/jvm/java-17-openjdk/bin/java \
        /usr/lib/jvm/jdk-17/bin/java \
        /opt/java/jdk-17/bin/java
    do
        if is_java17 "$CANDIDATE"; then
            echo "$CANDIDATE"
            return 0
        fi
    done

    return 1
}

require_java17() {
    JAVA_BIN=$(find_java17)
    if [ -z "$JAVA_BIN" ]; then
        echo "JDK 17 not found. Set JAVA_HOME to JDK 17 or set JAVA_CMD to the JDK 17 java executable."
        exit 1
    fi
}

query_pid() {
    ps -ef | awk -v app="$APP_NAME" -v module="$APP_MODULE" '
        $0 !~ /awk/ && ($0 ~ app || ($0 ~ module && $0 ~ /spring-boot:run/)) {print $2}
    '
}

query_port_pid() {
    if command -v lsof >/dev/null 2>&1; then
        lsof -ti TCP:"$APP_PORT" -sTCP:LISTEN 2>/dev/null
        return 0
    fi

    if command -v ss >/dev/null 2>&1; then
        ss -ltnp "sport = :$APP_PORT" 2>/dev/null | awk -F 'pid=' 'NR > 1 && $2 {split($2, a, ","); print a[1]}'
        return 0
    fi

    if command -v netstat >/dev/null 2>&1; then
        netstat -ltnp 2>/dev/null | awk -v port=":$APP_PORT" '$4 ~ port "$" && $7 ~ /^[0-9]+\// {split($7, a, "/"); print a[1]}'
    fi
}

merge_pids() {
    printf '%s\n%s\n' "$1" "$2" | awk 'NF && !seen[$1]++'
}

wait_exit() {
    TIMEOUT=${2:-20}
    ELAPSED=0
    while [ "$ELAPSED" -lt "$TIMEOUT" ]; do
        RUNNING=""
        for PID in $1; do
            if kill -0 "$PID" 2>/dev/null; then
                RUNNING="$RUNNING $PID"
            fi
        done

        if [ -z "$RUNNING" ]; then
            return 0
        fi

        sleep 1
        ELAPSED=$((ELAPSED + 1))
    done

    echo "$RUNNING"
    return 1
}

cleanup_history_processes() {
    APP_PIDS=$(query_pid)
    PORT_PIDS=$(query_port_pid)
    PIDS=$(merge_pids "$APP_PIDS" "$PORT_PIDS")

    if [ -z "$PIDS" ]; then
        echo "No history process found for $APP_NAME or port $APP_PORT."
        return 0
    fi

    echo "Clean history process. pid: $(echo "$PIDS" | tr '\n' ' ')"
    kill -TERM $PIDS 2>/dev/null || true

    LEFT_PIDS=$(wait_exit "$PIDS" 20)
    if [ -n "$LEFT_PIDS" ]; then
        echo "Force kill remaining process. pid:$LEFT_PIDS"
        kill -KILL $LEFT_PIDS 2>/dev/null || true
    fi
}

dev() {
    require_java17
    cleanup_history_processes

    mkdir -p "$LOG_DIR"
    cd "$APP_HOME" || exit 1
    # Root pom also declares spring-boot-maven-plugin. Using -am with spring-boot:run
    # pulls the parent into the reactor and fails with "Unable to find a suitable main class".
    # Build+install deps first (-am install), then run only on hmmp-admin.
    # Must use install (not package): spring-boot:run resolves sibling modules from ~/.m2.
    nohup env JAVA_HOME="$(dirname "$(dirname "$JAVA_BIN")")" PATH="$(dirname "$JAVA_BIN"):$PATH" \
        sh -c "\"$MAVEN_CMD\" -pl \"$APP_MODULE\" -am -DskipTests install && \
        \"$MAVEN_CMD\" -pl \"$APP_MODULE\" spring-boot:run \
        -Dspring-boot.run.jvmArguments=\"$JVM_OPTS\" \
        -Dspring-boot.run.arguments=\"$APP_ARGS\"" \
        >> "$DEV_LOG_PATH" 2>&1 &
    echo "Start $APP_MODULE dev success. port: $APP_PORT java: $JAVA_BIN log: $DEV_LOG_PATH"
}

stop() {
    PID=$(merge_pids "$(query_pid)" "$(query_port_pid)")
    if [ -z "$PID" ]; then
        echo "$APP_NAME already stopped."
        return 0
    fi

    echo "Stop $APP_NAME. pid: $PID"
    kill -TERM $PID
    LEFT_PIDS=$(wait_exit "$PID" 20)
    if [ -n "$LEFT_PIDS" ]; then
        echo "Force kill remaining process. pid:$LEFT_PIDS"
        kill -KILL $LEFT_PIDS 2>/dev/null || true
    fi
    echo "$APP_NAME exited."
}

restart() {
    dev
}

status() {
    PID=$(merge_pids "$(query_pid)" "$(query_port_pid)")
    if [ -n "$PID" ]; then
        echo "$APP_NAME is running. pid: $PID"
    else
        echo "$APP_NAME is not running."
    fi
}

logs() {
    if [ ! -f "$DEV_LOG_PATH" ]; then
        echo "$DEV_LOG_PATH not found."
        exit 1
    fi
    echo "Tailing $DEV_LOG_PATH (Ctrl+C to stop)..."
    tail -n 80 -f "$DEV_LOG_PATH"
}

case "$1" in
    start) dev ;;
    dev) dev ;;
    stop) stop ;;
    restart) restart ;;
    status) status ;;
    logs) logs ;;
    *) usage; exit 1 ;;
esac
