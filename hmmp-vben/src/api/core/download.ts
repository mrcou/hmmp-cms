import { message } from 'antdv-next';

import { requestClient } from '#/api/request';

/**
 * 调用后端 POST /export 类接口下载 Excel（响应为二进制流）
 */
export async function downloadExcel(url: string, params: Record<string, any> = {}, fileName = 'export.xlsx') {
  const hide = message.loading({ content: '正在下载数据，请稍候', duration: 0 });
  try {
    const blob = await requestClient.post<Blob>(url, params, {
      responseType: 'blob',
      responseReturn: 'body',
    });
    saveBlob(blob, fileName);
  } finally {
    hide();
  }
}

export function saveBlob(blob: Blob, fileName: string) {
  const url = window.URL.createObjectURL(blob);
  const link = document.createElement('a');
  link.href = url;
  link.download = fileName;
  document.body.append(link);
  link.click();
  link.remove();
  window.URL.revokeObjectURL(url);
}
