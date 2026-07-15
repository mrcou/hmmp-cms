#!/bin/sh

FRONT_DIR=hmmp-vben
FRONT_PORT=${FRONT_PORT:-}
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
LOG_DIR="$APP_HOME/logs"
LOG_PATH="$LOG_DIR/front-dev.log"

usage() {
    echo "Usage: $0 {start|stop|restart|status|logs}"
}

load_port() {
    if [ -n "$FRONT_PORT" ]; then
        return 0
    fi

    ENV_FILE="$APP_HOME/$FRONT_DIR/.env.development"
    if [ -f "$ENV_FILE" ]; then
        FRONT_PORT=$(awk -F '=' '$1 == "VITE_PORT" {print $2; exit}' "$ENV_FILE" | tr -d '[:space:]')
    fi

    FRONT_PORT=${FRONT_PORT:-5999}
}

query_port_pid() {
    load_port

    if command -v lsof >/dev/null 2>&1; then
        lsof -ti TCP:"$FRONT_PORT" -sTCP:LISTEN 2>/dev/null
        return 0
    fi

    if command -v ss >/dev/null 2>&1; then
        ss -ltnp "sport = :$FRONT_PORT" 2>/dev/null | awk -F 'pid=' 'NR > 1 && $2 {split($2, a, ","); print a[1]}'
        return 0
    fi

    if command -v netstat >/dev/null 2>&1; then
        netstat -ltnp 2>/dev/null | awk -v port=":$FRONT_PORT" '$4 ~ port "$" && $7 ~ /^[0-9]+\// {split($7, a, "/"); print a[1]}'
    fi
}

query_dev_pid() {
    ps -ef 2>/dev/null | awk -v dir="$FRONT_DIR" '
        $0 !~ /awk/ && $0 ~ dir && ($0 ~ /pnpm/ || $0 ~ /vite/) {print $2}
    '
}

merge_pids() {
    printf '%s\n%s\n' "$1" "$2" | awk 'NF && !seen[$1]++'
}

wait_exit() {
    TIMEOUT=${2:-15}
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
    PIDS=$(merge_pids "$(query_dev_pid)" "$(query_port_pid)")

    if [ -z "$PIDS" ]; then
        echo "No frontend dev process found for $FRONT_DIR or port $FRONT_PORT."
        return 0
    fi

    echo "Clean frontend dev process. pid: $(echo "$PIDS" | tr '\n' ' ')"
    kill -TERM $PIDS 2>/dev/null || true

    LEFT_PIDS=$(wait_exit "$PIDS" 15)
    if [ -n "$LEFT_PIDS" ]; then
        echo "Force kill remaining process. pid:$LEFT_PIDS"
        kill -KILL $LEFT_PIDS 2>/dev/null || true
    fi
}

require_frontend_env() {
    if [ ! -f "$APP_HOME/$FRONT_DIR/package.json" ]; then
        echo "$FRONT_DIR/package.json not found."
        exit 1
    fi

    if [ ! -d "$APP_HOME/$FRONT_DIR/node_modules" ]; then
        echo "$FRONT_DIR/node_modules not found. Run dependency installation before starting dev server."
        exit 1
    fi

    if [ ! -x "$APP_HOME/$FRONT_DIR/node_modules/.bin/vite" ]; then
        echo "$FRONT_DIR/node_modules/.bin/vite not found or not executable."
        exit 1
    fi
}

start() {
    load_port
    require_frontend_env
    cleanup_history_processes

    mkdir -p "$LOG_DIR"
    cd "$APP_HOME/$FRONT_DIR" || exit 1
    nohup "$APP_HOME/$FRONT_DIR/node_modules/.bin/vite" --mode development --host 0.0.0.0 --port "$FRONT_PORT" >> "$LOG_PATH" 2>&1 &
    echo "Start $FRONT_DIR dev success. port: $FRONT_PORT log: $LOG_PATH"
}

stop() {
    load_port
    PIDS=$(merge_pids "$(query_dev_pid)" "$(query_port_pid)")
    if [ -z "$PIDS" ]; then
        echo "$FRONT_DIR dev already stopped."
        return 0
    fi

    echo "Stop $FRONT_DIR dev. pid: $(echo "$PIDS" | tr '\n' ' ')"
    kill -TERM $PIDS 2>/dev/null || true
    LEFT_PIDS=$(wait_exit "$PIDS" 15)
    if [ -n "$LEFT_PIDS" ]; then
        echo "Force kill remaining process. pid:$LEFT_PIDS"
        kill -KILL $LEFT_PIDS 2>/dev/null || true
    fi
    echo "$FRONT_DIR dev exited."
}

restart() {
    start
}

status() {
    load_port
    PIDS=$(merge_pids "$(query_dev_pid)" "$(query_port_pid)")
    if [ -n "$PIDS" ]; then
        echo "$FRONT_DIR dev is running. pid: $(echo "$PIDS" | tr '\n' ' ') port: $FRONT_PORT"
    else
        echo "$FRONT_DIR dev is not running. port: $FRONT_PORT"
    fi
}

logs() {
    if [ ! -f "$LOG_PATH" ]; then
        echo "$LOG_PATH not found."
        exit 1
    fi
    tail -f "$LOG_PATH"
}

case "$1" in
    start) start ;;
    stop) stop ;;
    restart) restart ;;
    status) status ;;
    logs) logs ;;
    *) usage; exit 1 ;;
esac
