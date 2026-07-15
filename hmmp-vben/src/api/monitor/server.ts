import { requestClient } from '#/api/request';

export namespace MonitorServerApi {
  export interface Cpu {
    cpuNum: number;
    free: number;
    sys: number;
    total: number;
    used: number;
    wait: number;
  }

  export interface Mem {
    free: number;
    total: number;
    usage: number;
    used: number;
  }

  export interface Jvm {
    free: number;
    home: string;
    inputArgs: string;
    max: number;
    name: string;
    runTime: string;
    startTime: string;
    total: number;
    usage: number;
    used: number;
    version: string;
  }

  export interface Sys {
    computerIp: string;
    computerName: string;
    osArch: string;
    osName: string;
    userDir: string;
  }

  export interface SysFile {
    dirName: string;
    free: string;
    sysTypeName: string;
    total: string;
    typeName: string;
    usage: number;
    used: string;
  }

  export interface Server {
    cpu: Cpu;
    jvm: Jvm;
    mem: Mem;
    sys: Sys;
    sysFiles: SysFile[];
  }
}

/** 服务器信息 */
export async function getServerInfo() {
  return requestClient.get<{ data: MonitorServerApi.Server }>(
    '/monitor/server',
  );
}
