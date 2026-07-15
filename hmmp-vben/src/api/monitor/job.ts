import { requestClient } from '#/api/request';

export namespace MonitorJobApi {
  export interface SysJob {
    concurrent?: string;
    createBy?: string;
    createTime?: string;
    cronExpression: string;
    invokeTarget: string;
    jobGroup: string;
    jobId?: number;
    jobName: string;
    misfirePolicy?: string;
    nextValidTime?: string;
    remark?: string;
    status?: string;
    updateBy?: string;
    updateTime?: string;
  }

  export interface SysJobLog {
    createTime?: string;
    endTime?: string;
    exceptionInfo?: string;
    invokeTarget?: string;
    jobGroup?: string;
    jobLogId?: number;
    jobMessage?: string;
    jobName?: string;
    startTime?: string;
    status?: string;
  }

  export interface JobListParams {
    invokeTarget?: string;
    jobGroup?: string;
    jobName?: string;
    pageNum?: number;
    pageSize?: number;
    status?: string;
  }

  export interface JobLogListParams extends JobListParams {
    params?: {
      beginTime?: string;
      endTime?: string;
    };
  }
}

/** 定时任务分页列表 */
export async function getJobList(params: MonitorJobApi.JobListParams) {
  return requestClient.get<{
    rows: MonitorJobApi.SysJob[];
    total: number;
  }>('/monitor/job/list', { params });
}

/** 定时任务详情 */
export async function getJob(jobId: number) {
  return requestClient.get<{ data: MonitorJobApi.SysJob }>(
    `/monitor/job/${jobId}`,
  );
}

export async function createJob(data: MonitorJobApi.SysJob) {
  return requestClient.post('/monitor/job', data);
}

export async function updateJob(data: MonitorJobApi.SysJob) {
  return requestClient.put('/monitor/job', data);
}

export async function deleteJob(jobIds: number | number[]) {
  const ids = Array.isArray(jobIds) ? jobIds.join(',') : jobIds;
  return requestClient.delete(`/monitor/job/${ids}`);
}

/** 修改定时任务状态 */
export async function changeJobStatus(jobId: number, status: string) {
  return requestClient.put('/monitor/job/changeStatus', { jobId, status });
}

/** 立即执行一次 */
export async function runJob(jobId: number, jobGroup: string) {
  return requestClient.put('/monitor/job/run', { jobGroup, jobId });
}

/** 导出定时任务 */
export async function exportJob(params: MonitorJobApi.JobListParams) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/monitor/job/export', params, '定时任务.xlsx');
}

/** 调度日志分页列表 */
export async function getJobLogList(params: MonitorJobApi.JobLogListParams) {
  return requestClient.get<{
    rows: MonitorJobApi.SysJobLog[];
    total: number;
  }>('/monitor/jobLog/list', { params });
}

/** 调度日志详情 */
export async function getJobLog(jobLogId: number) {
  return requestClient.get<{ data: MonitorJobApi.SysJobLog }>(
    `/monitor/jobLog/${jobLogId}`,
  );
}

/** 删除调度日志（支持批量） */
export async function deleteJobLog(jobLogIds: number | number[]) {
  const ids = Array.isArray(jobLogIds) ? jobLogIds.join(',') : jobLogIds;
  return requestClient.delete(`/monitor/jobLog/${ids}`);
}

/** 清空调度日志 */
export async function cleanJobLog() {
  return requestClient.delete('/monitor/jobLog/clean');
}

/** 导出调度日志 */
export async function exportJobLog(params: MonitorJobApi.JobLogListParams) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/monitor/jobLog/export', params, '调度日志.xlsx');
}
