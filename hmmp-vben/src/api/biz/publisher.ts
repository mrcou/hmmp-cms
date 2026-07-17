import { requestClient } from '#/api/request';

export namespace PublisherApi {
  export interface Year {
    yearId?: number;
    /** 杂志编号 */
    journalCode?: string;
    year: number;
    /** 卷号 */
    volume?: number;
    /** 中文名 */
    nameCn?: string;
    /** 英文名 */
    nameEn?: string;
    /** 目录文件 */
    catalogFile?: string;
    /** 是否免费(0否 1是) */
    isFree?: string;
    /** 会议ID */
    meetingId?: number;
    status?: string;
    remark?: string;
  }

  export interface Issue {
    issueId?: number;
    yearId?: number;
    /** 杂志编号 */
    journalCode?: string;
    year: number;
    volume?: number;
    period: string;
    issueName?: string;
    /** 中文名 */
    nameCn?: string;
    /** 英文名 */
    nameEn?: string;
    /** 顺序 */
    sequence?: number;
    /** 专辑中文名 */
    specialName?: string;
    /** 专辑英文名 */
    specialEnName?: string;
    /** 重点(中文) */
    important?: string;
    /** 重点(英文) */
    enImportant?: string;
    /** 封面(小图) */
    imageUrl?: string;
    /** 封面(大图) */
    largerCoverUrl?: string;
    /** 目录文件 */
    contentsFile?: string;
    /** 封面说明(中文) */
    cnImageExplain?: string;
    /** 封面说明(英文) */
    enImageExplain?: string;
    /** 免费下载？(旧字段 request_money，0否 1是) */
    requestMoney?: string;
    /** 是否免费(0否 1是) */
    isFree?: string;
    /** 是否最新一期文章(0否 1是) */
    isLatest?: string;
    /** 会议ID */
    meetingId?: number;
    /** 订阅发送状态(0未发送 1已发送) */
    subscribeStatus?: string;
    /** DOI注册状态(0未注册 1已注册) */
    doiStatus?: string;
    /** CSTR注册状态(0未注册 1已注册) */
    cstrStatus?: string;
    /** 百度发布状态(0未发送 1已发送) */
    baiduStatus?: string;
    publishDate?: string;
    status?: string;
    publishTime?: string;
    articleCount?: number;
    pageCount?: number;
    coverImage?: string;
    printMoney?: number;
    publishMoney?: number;
    postMoney?: number;
    startPage?: string;
    endPage?: string;
    columnsInfo?: string;
    remark?: string;
  }

  export interface Column {
    columnId?: number;
    /** 杂志编号 */
    journalCode?: string;
    /** 栏目名称 */
    columnName: string;
    /** 英文名 */
    nameEn?: string;
    /** 英文栏目（旧字段 column_en_name，对应 name_en） */
    columnEnName?: string;
    parentId?: number;
    /** 顺序 */
    orderNum?: number;
    /** 顺序（旧字段 sequence，对应 order_num） */
    sequence?: number;
    /** 是否需要推荐人(0否 1是) */
    needRecommender?: string;
    /** 使用范围，顿号分隔：投稿、内部查阅、网刊发布 */
    usageScope?: string;
    /** 使用范围(1投稿、内部查询、网刊发布 0网刊发布 2内部查询、网刊发布) */
    isPostColumn?: string;
    /** 稿件编号前缀 */
    fileNoPrefix?: string;
    /** 英文摘要加工费 */
    enAbstractMoney?: number;
    /** 编辑账号 */
    editName?: string;
    /** 栏目ID/编码（业务键，如 lcst001） */
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
    journalCode?: string;
    fileNo?: string;
    title: string;
    titleEn?: string;
    subtitle?: string;
    authorNames?: string;
    authorNamesEn?: string;
    authorOrgZh?: string;
    authorOrgEn?: string;
    doi?: string;
    /** 摘要（后端 PublisherArticle.abstractText） */
    abstractText?: string;
    /** @deprecated 兼容旧页面字段，新代码请使用 abstractText */
    abstract?: string;
    abstractEn?: string;
    keywords?: string;
    keywordsEn?: string;
    fundZh?: string;
    fundEn?: string;
    content?: string;
    referenceText?: string;
    citationText?: string;
    pageStart?: number;
    pageEnd?: number;
    wordCount?: number;
    submitTime?: string;
    lastModifyTime?: string;
    volume?: number;
    period?: number;
    yearNo?: number;
    status?: string;
    publishTime?: string;
    viewCount?: number;
    downloadCount?: number;
    htmlViewCount?: number;
    citedCount?: number;
    cnkiCitedCount?: number;
    cnkiTitle?: string;
    cnkiMatchStatus?: string;
    articleUrl?: string;
    pdfPath?: string;
    createTime?: string;
    updateTime?: string;
    remark?: string;
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

  /** 邮寄样刊登记 */
  export interface SamplePost {
    sampleId?: number;
    articleId?: number;
    manuscriptId?: number;
    fileNo?: string;
    yearNo?: number;
    period?: number;
    submitAuthor?: string;
    firstAuthor?: string;
    correspondingAuthor?: string;
    firstAuthorAddress?: string;
    firstAuthorOrg?: string;
    firstAuthorZip?: string;
    firstAuthorPhone?: string;
    correspondingAddress?: string;
    correspondingOrg?: string;
    correspondingZip?: string;
    correspondingPhone?: string;
    submitAuthorAddress?: string;
    submitAuthorOrg?: string;
    submitAuthorZip?: string;
    submitAuthorPhone?: string;
    payeeName?: string;
    payeeAddress?: string;
    payeeOrg?: string;
    payeeZip?: string;
    payeePhone?: string;
    sampleQty?: number;
    journalCode?: string;
    meetingId?: number;
    remark?: string;
  }

  /** 邮寄合订本 */
  export interface BoundPost {
    boundId?: number;
    orderId?: number;
    orderNo?: string;
    journalCode?: string;
    category?: string;
    unitType?: string;
    distributeType?: string;
    sendMethodName?: string;
    yearId?: number;
    receiveUser?: string;
    receiveUnit?: string;
    receiveAddress?: string;
    receivePostcode?: string;
    orderTime?: string;
    orderBookNum?: number;
    sendBookTime?: string;
    sendUser?: string;
    rmbOrderCost?: number;
    canPost?: string;
    meetingId?: number;
    mailedOnly?: boolean;
    pendingOnly?: boolean;
    orderIds?: number[];
    remark?: string;
  }

  export interface Comment {
    commentId?: number;
    articleId?: number;
    userId?: number;
    userName?: string;
    content?: string;
    auditStatus?: string;
    auditTime?: string;
    auditBy?: string;
    isTop?: string;
    replyTo?: number;
    likeCount?: number;
    status?: string;
    createTime?: string;
    fileNo?: string;
    articleTitle?: string;
    yearNo?: number;
    period?: number;
    journalCode?: string;
    meetingId?: number;
    commentIds?: number[];
  }

  export interface Album {
    albumId?: number;
    /** 杂志名称/编号（旧表单字段 journal_id） */
    journal_id?: string;
    /** 杂志编号 */
    journalCode?: string;
    /** 专辑中文名（旧表单字段 cn_name） */
    cn_name?: string;
    /** 中文名 */
    nameCn: string;
    /** 专辑英文名（旧表单字段 en_name） */
    en_name?: string;
    /** 英文名 */
    nameEn?: string;
    /** 完成日期 */
    finishDate?: string;
    /** 顺序（旧表单字段 sequence） */
    sequence?: number;
    /** 顺序 */
    orderNum?: number;
    /** 类型（旧表单字段 type） */
    type?: string;
    /** 类型 */
    albumType?: string;
    /** 会议ID */
    meetingId?: number;
    /** 中文封面（旧表单字段 image_url） */
    image_url?: string;
    /** 英文封面（旧表单字段 en_image_url） */
    en_image_url?: string;
    coverImage?: string;
    /** 中文描述（旧表单字段 CKEDITOR_cn_description） */
    CKEDITOR_cn_description?: string;
    /** 英文描述（旧表单字段 CKEDITOR_en_description） */
    CKEDITOR_en_description?: string;
    /** 组稿编辑中文名（旧表单字段 CKEDITOR_acquisition_editor） */
    CKEDITOR_acquisition_editor?: string;
    /** 组稿编辑英文名（旧表单字段 CKEDITOR_en_acquisition_editor） */
    CKEDITOR_en_acquisition_editor?: string;
    description?: string;
    status?: string;
    createBy?: string;
    createTime?: string;
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
export async function publishIssue(data: PublisherApi.Issue) {
  return requestClient.put('/publisher/issue/publish', data);
}
export async function uploadPublisherFile(file: File) {
  return requestClient.upload('/common/upload', { file });
}
/** 刊期业务动作：subscribe / doi / cstr / baidu */
export async function executeIssueAction(
  action: 'subscribe' | 'doi' | 'cstr' | 'baidu',
  issueId: number,
) {
  return requestClient.put(`/publisher/issue/action/${action}/${issueId}`);
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

/** 文章评论管理 */
export async function getCommentList(params: any) {
  return requestClient.get('/publisher/comment/list', { params });
}
export async function auditComment(data: PublisherApi.Comment) {
  return requestClient.put('/publisher/comment/audit', data);
}
export async function deleteComment(commentIds: number[]) {
  return requestClient.delete(`/publisher/comment/${commentIds.join(',')}`);
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

/** 知网标题核对 */
export async function cnkiCheckArticle(data: PublisherApi.Article) {
  return requestClient.post('/publisher/article/cnkiCheck', data);
}

/** 引用次数同步：将知网引用次数写入被引次数 */
export async function syncCnkiCited(articleIds: number[]) {
  return requestClient.put(`/publisher/article/syncCnkiCited/${articleIds.join(',')}`);
}

/** 批量发布：导入过刊 Excel 或 ZIP 全文 */
export async function batchImportArticles(file: File, updateSupport = false) {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('updateSupport', String(updateSupport));
  return requestClient.post<{ msg: string }>('/publisher/article/batchImport', formData);
}

/** 下载过刊导入模板 */
export async function downloadArticleImportTemplate() {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/publisher/article/importTemplate', {}, '过刊稿件明细导入模板.xlsx');
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

/** 邮寄样刊登记 */
export async function getSamplePostList(params: any) {
  return requestClient.get('/publisher/post/sample/list', { params });
}
export async function saveSamplePost(data: PublisherApi.SamplePost) {
  return requestClient.put('/publisher/post/sample', data);
}
export async function exportSamplePost(params: Record<string, any> = {}) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/publisher/post/sample/export', params, '邮寄样刊登记.xlsx');
}

/** 邮寄合订本 */
export async function getBoundPostList(params: any) {
  return requestClient.get('/publisher/post/bound/list', { params });
}
export async function saveBoundPost(data: PublisherApi.BoundPost) {
  return requestClient.put('/publisher/post/bound', data);
}
export async function clearInvalidBoundPostAddress() {
  return requestClient.put('/publisher/post/bound/clearInvalidAddress');
}
export async function exportBoundPost(params: Record<string, any> = {}) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/publisher/post/bound/export', params, '邮寄合订本.xlsx');
}

/** 虚拟专辑管理 */
export async function getAlbumList(params: any) {
  return requestClient.get('/publisher/album/list', { params });
}
export async function getAlbum(albumId: number) {
  return requestClient.get(`/publisher/album/${albumId}`);
}
export async function createAlbum(data: PublisherApi.Album) {
  return requestClient.post('/publisher/album', data);
}
export async function updateAlbum(data: PublisherApi.Album) {
  return requestClient.put('/publisher/album', data);
}
export async function deleteAlbum(albumIds: number[]) {
  return requestClient.delete(`/publisher/album/${albumIds.join(',')}`);
}
