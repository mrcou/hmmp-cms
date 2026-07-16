import { requestClient } from '#/api/request';

export namespace PublisherApi {
  export interface Year {
    yearId?: number;
    year: number;
    status?: string;
    remark?: string;
  }

  export interface Issue {
    issueId?: number;
    yearId: number;
    year: number;
    period: number;
    issueName?: string;
    publishDate?: string;
    status?: string;
    publishTime?: string;
    articleCount?: number;
    pageCount?: number;
    coverImage?: string;
  }

  export interface Column {
    columnId?: number;
    columnName: string;
    parentId?: number;
    orderNum?: number;
    columnCode?: string;
    columnType?: string;
    status?: string;
    description?: string;
  }

  export interface Article {
    articleId?: number;
    manuscriptId?: number;
    issueId?: number;
    columnId?: number;
    title: string;
    subtitle?: string;
    authorNames?: string;
    abstract?: string;
    keywords?: string;
    content?: string;
    pageStart?: number;
    pageEnd?: number;
    wordCount?: number;
    status?: string;
    publishTime?: string;
    viewCount?: number;
    cnkiTitle?: string;
    cnkiMatchStatus?: string;
  }

  export interface Reader {
    readerId?: number;
    userId?: number;
    realName?: string;
    nickName?: string;
    gender?: string;
    birthday?: string;
    email?: string;
    phone?: string;
    mobile?: string;
    address?: string;
    zipCode?: string;
    registerTime?: string;
    lastLoginTime?: string;
    status?: string;
  }

  export interface Subscriber {
    subscriberId?: number;
    readerId?: number;
    realName: string;
    email?: string;
    phone?: string;
    mobile?: string;
    address?: string;
    zipCode?: string;
    company?: string;
    subscribeStartDate?: string;
    subscribeEndDate?: string;
    status?: string;
  }

  export interface Price {
    priceId?: number;
    year: number;
    periodStart: number;
    periodEnd: number;
    unitPrice?: number;
    annualPrice?: number;
    status?: string;
  }

  export interface Order {
    orderId?: number;
    orderNo?: string;
    subscriberId?: number;
    subscriberName?: string;
    totalAmount?: number;
    payStatus?: string;
    orderStatus?: string;
    orderType?: string;
    shippingName?: string;
    shippingPhone?: string;
    shippingAddress?: string;
    createTime?: string;
  }

  export interface Invoice {
    invoiceId?: number;
    orderId?: number;
    invoiceNo?: string;
    invoiceType?: string;
    amount?: number;
    taxAmount?: number;
    totalAmount?: number;
    title?: string;
    taxId?: string;
    applyStatus?: string;
    auditTime?: string;
    auditBy?: string;
    makeStatus?: string;
    makeTime?: string;
  }

  export interface PushKeyword {
    keywordId?: number;
    keyword?: string;
    pushContent?: string;
    pushTime?: string;
    status?: string;
  }

  export interface PushSubscriber {
    pushSubId?: number;
    userId?: number;
    userName?: string;
    email?: string;
    phone?: string;
    keywords?: string;
    status?: string;
  }

  export interface Inventory {
    inventoryId?: number;
    year: number;
    period: number;
    totalCount?: number;
    stockCount?: number;
    soldCount?: number;
    giftCount?: number;
    lossCount?: number;
    status?: string;
    remark?: string;
  }

  export interface Post {
    postId?: number;
    orderId?: number;
    subscriberName?: string;
    shippingName?: string;
    shippingPhone?: string;
    shippingAddress?: string;
    year?: number;
    period?: number;
    postTrackingNo?: string;
    postCompany?: string;
    postStatus?: string;
    postTime?: string;
    quantity?: number;
    remark?: string;
  }
}

/** 年份管理 */
export async function getYearList(params: any) {
  return requestClient.get('/publisher/year/list', { params });
}
export async function getYear(yearId: number) {
  return requestClient.get(`/publisher/year/${yearId}`);
}
export async function createYear(data: PublisherApi.Year) {
  return requestClient.post('/publisher/year', data);
}
export async function updateYear(data: PublisherApi.Year) {
  return requestClient.put('/publisher/year', data);
}
export async function deleteYear(yearIds: number[]) {
  return requestClient.delete(`/publisher/year/${yearIds.join(',')}`);
}

/** 刊期管理 */
export async function getIssueList(params: any) {
  return requestClient.get('/publisher/issue/list', { params });
}
export async function getIssue(issueId: number) {
  return requestClient.get(`/publisher/issue/${issueId}`);
}
export async function createIssue(data: PublisherApi.Issue) {
  return requestClient.post('/publisher/issue', data);
}
export async function updateIssue(data: PublisherApi.Issue) {
  return requestClient.put('/publisher/issue', data);
}
export async function deleteIssue(issueIds: number[]) {
  return requestClient.delete(`/publisher/issue/${issueIds.join(',')}`);
}

/** 栏目管理 */
export async function getColumnList(params: any) {
  return requestClient.get('/publisher/column/list', { params });
}
export async function getColumn(columnId: number) {
  return requestClient.get(`/publisher/column/${columnId}`);
}
export async function createColumn(data: PublisherApi.Column) {
  return requestClient.post('/publisher/column', data);
}
export async function updateColumn(data: PublisherApi.Column) {
  return requestClient.put('/publisher/column', data);
}
export async function deleteColumn(columnIds: number[]) {
  return requestClient.delete(`/publisher/column/${columnIds.join(',')}`);
}

/** 文章管理 */
export async function getArticleList(params: any) {
  return requestClient.get('/publisher/article/list', { params });
}
export async function getArticle(articleId: number) {
  return requestClient.get(`/publisher/article/${articleId}`);
}
export async function createArticle(data: PublisherApi.Article) {
  return requestClient.post('/publisher/article', data);
}
export async function updateArticle(data: PublisherApi.Article) {
  return requestClient.put('/publisher/article', data);
}
export async function deleteArticle(articleIds: number[]) {
  return requestClient.delete(`/publisher/article/${articleIds.join(',')}`);
}

/** 读者管理 */
export async function getReaderList(params: any) {
  return requestClient.get('/publisher/reader/list', { params });
}
export async function getReader(readerId: number) {
  return requestClient.get(`/publisher/reader/${readerId}`);
}
export async function createReader(data: PublisherApi.Reader) {
  return requestClient.post('/publisher/reader', data);
}
export async function updateReader(data: PublisherApi.Reader) {
  return requestClient.put('/publisher/reader', data);
}
export async function deleteReader(readerIds: number[]) {
  return requestClient.delete(`/publisher/reader/${readerIds.join(',')}`);
}

/** 订户管理 */
export async function getSubscriberList(params: any) {
  return requestClient.get('/publisher/subscriber/list', { params });
}
export async function getSubscriber(subscriberId: number) {
  return requestClient.get(`/publisher/subscriber/${subscriberId}`);
}
export async function createSubscriber(data: PublisherApi.Subscriber) {
  return requestClient.post('/publisher/subscriber', data);
}
export async function updateSubscriber(data: PublisherApi.Subscriber) {
  return requestClient.put('/publisher/subscriber', data);
}
export async function deleteSubscriber(subscriberIds: number[]) {
  return requestClient.delete(`/publisher/subscriber/${subscriberIds.join(',')}`);
}

/** 定价管理 */
export async function getPriceList(params: any) {
  return requestClient.get('/publisher/price/list', { params });
}
export async function getPrice(priceId: number) {
  return requestClient.get(`/publisher/price/${priceId}`);
}
export async function createPrice(data: PublisherApi.Price) {
  return requestClient.post('/publisher/price', data);
}
export async function updatePrice(data: PublisherApi.Price) {
  return requestClient.put('/publisher/price', data);
}
export async function deletePrice(priceIds: number[]) {
  return requestClient.delete(`/publisher/price/${priceIds.join(',')}`);
}

/** 订单管理 */
export async function getOrderList(params: any) {
  return requestClient.get('/publisher/order/list', { params });
}
export async function getOrder(orderId: number) {
  return requestClient.get(`/publisher/order/${orderId}`);
}
export async function createOrder(data: PublisherApi.Order) {
  return requestClient.post('/publisher/order', data);
}
export async function payOrder(data: PublisherApi.Order) {
  return requestClient.put('/publisher/order/pay', data);
}
export async function shipOrder(data: PublisherApi.Order) {
  return requestClient.put('/publisher/order/ship', data);
}
export async function updateOrder(data: PublisherApi.Order) {
  return requestClient.put('/publisher/order', data);
}
export async function deleteOrder(orderIds: number[]) {
  return requestClient.delete(`/publisher/order/${orderIds.join(',')}`);
}

/** 发票管理 */
export async function getInvoiceList(params: any) {
  return requestClient.get('/publisher/invoice/list', { params });
}
export async function applyInvoice(data: PublisherApi.Invoice) {
  return requestClient.post('/publisher/invoice/apply', data);
}
export async function auditInvoice(data: PublisherApi.Invoice) {
  return requestClient.put('/publisher/invoice/audit', data);
}
export async function makeInvoice(data: PublisherApi.Invoice) {
  return requestClient.put('/publisher/invoice/make', data);
}
export async function postInvoice(data: PublisherApi.Invoice) {
  return requestClient.put('/publisher/invoice/post', data);
}
export async function createInvoice(data: PublisherApi.Invoice) {
  return requestClient.post('/publisher/invoice', data);
}
export async function updateInvoice(data: PublisherApi.Invoice) {
  return requestClient.put('/publisher/invoice', data);
}
export async function deleteInvoice(invoiceIds: number[]) {
  return requestClient.delete(`/publisher/invoice/${invoiceIds.join(',')}`);
}

/** 关键词推送 */
export async function getPushKeywordList(params: any) {
  return requestClient.get('/publisher/push/keyword/list', { params });
}
export async function createPushKeyword(data: PublisherApi.PushKeyword) {
  return requestClient.post('/publisher/push/keyword', data);
}
export async function deletePushKeyword(keywordIds: number[]) {
  return requestClient.delete(`/publisher/push/keyword/${keywordIds.join(',')}`);
}

/** 订阅用户管理 */
export async function getPushSubscriberList(params: any) {
  return requestClient.get('/publisher/push/subscriber/list', { params });
}
export async function createPushSubscriber(data: PublisherApi.PushSubscriber) {
  return requestClient.post('/publisher/push/subscriber', data);
}
export async function deletePushSubscriber(pushSubIds: number[]) {
  return requestClient.delete(`/publisher/push/subscriber/${pushSubIds.join(',')}`);
}

/** 库存管理 */
export async function getInventoryList(params: any) {
  return requestClient.get('/publisher/inventory/list', { params });
}
export async function getInventory(inventoryId: number) {
  return requestClient.get(`/publisher/inventory/${inventoryId}`);
}
export async function createInventory(data: PublisherApi.Inventory) {
  return requestClient.post('/publisher/inventory', data);
}
export async function updateInventory(data: PublisherApi.Inventory) {
  return requestClient.put('/publisher/inventory', data);
}
export async function deleteInventory(inventoryIds: number[]) {
  return requestClient.delete(`/publisher/inventory/${inventoryIds.join(',')}`);
}

/** 寄书管理 */
export async function getPostList(params: any) {
  return requestClient.get('/publisher/post/list', { params });
}
export async function createPost(data: PublisherApi.Post) {
  return requestClient.post('/publisher/post', data);
}
export async function executePost(data: PublisherApi.Post) {
  return requestClient.put('/publisher/post/post', data);
}
export async function updatePost(data: PublisherApi.Post) {
  return requestClient.put('/publisher/post', data);
}
export async function deletePost(postIds: number[]) {
  return requestClient.delete(`/publisher/post/${postIds.join(',')}`);
}