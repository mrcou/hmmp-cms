import { requestClient } from '#/api/request';

export namespace SystemPostApi {
  export interface SysPost {
    createTime?: string;
    postCode: string;
    postId?: number;
    postName: string;
    postSort: number;
    remark?: string;
    status: string;
  }

  export interface PostListParams {
    pageNum?: number;
    pageSize?: number;
    postCode?: string;
    postName?: string;
    status?: string;
  }
}

/** 岗位分页列表 */
export async function getPostList(params: SystemPostApi.PostListParams) {
  return requestClient.get<{ rows: SystemPostApi.SysPost[]; total: number }>(
    '/system/post/list',
    { params },
  );
}

/** 岗位详情 */
export async function getPost(postId: number) {
  return requestClient.get<{ data: SystemPostApi.SysPost }>(
    `/system/post/${postId}`,
  );
}

export async function createPost(data: SystemPostApi.SysPost) {
  return requestClient.post('/system/post', data);
}

export async function updatePost(data: SystemPostApi.SysPost) {
  return requestClient.put('/system/post', data);
}

export async function deletePost(postId: number | number[]) {
  const ids = Array.isArray(postId) ? postId.join(',') : postId;
  return requestClient.delete(`/system/post/${ids}`);
}

/** 导出岗位数据 */
export async function exportPost(params: SystemPostApi.PostListParams) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/system/post/export', params, '岗位数据.xlsx');
}
