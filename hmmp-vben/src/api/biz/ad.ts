import { requestClient } from '#/api/request';

export namespace AdApi {
  export interface Area {
    areaId?: number;
    areaName: string;
    parentId?: number;
    areaCode?: string;
    orderNum?: number;
    status?: string;
  }

  export interface PositionType {
    typeId?: number;
    typeName: string;
    typeCode?: string;
    description?: string;
    status?: string;
  }

  export interface Position {
    positionId?: number;
    positionName: string;
    typeId?: number;
    typeName?: string;
    width?: number;
    height?: number;
    positionCode?: string;
    pageLocation?: string;
    description?: string;
    status?: string;
    sortOrder?: number;
  }

  export interface AdType {
    typeId?: number;
    typeName: string;
    typeCode?: string;
    description?: string;
    status?: string;
  }

  export interface Customer {
    customerId?: number;
    customerName: string;
    contactPerson?: string;
    phone?: string;
    mobile?: string;
    email?: string;
    address?: string;
    areaId?: number;
    companyName?: string;
    companyType?: string;
    taxId?: string;
    bankName?: string;
    bankAccount?: string;
    status?: string;
  }

  export interface Bargain {
    bargainId?: number;
    bargainNo?: string;
    customerId: number;
    customerName?: string;
    bargainName?: string;
    totalAmount?: number;
    signDate?: string;
    startDate?: string;
    endDate?: string;
    bargainContent?: string;
    bargainFile?: string;
    applyStatus?: string;
    auditTime?: string;
    auditBy?: string;
    auditOpinion?: string;
    printStatus?: string;
    printTime?: string;
    status?: string;
  }

  export interface AdInfo {
    adId?: number;
    bargainId?: number;
    customerId?: number;
    adName: string;
    typeId?: number;
    typeName?: string;
    positionId?: number;
    positionName?: string;
    adContent?: string;
    adImage?: string;
    adLink?: string;
    startDate?: string;
    endDate?: string;
    price?: number;
    issueIds?: string;
    applyStatus?: string;
    auditTime?: string;
    auditBy?: string;
    auditOpinion?: string;
    makeStatus?: string;
    status?: string;
    viewCount?: number;
    clickCount?: number;
  }

  export interface Payment {
    paymentId?: number;
    bargainId?: number;
    adId?: number;
    paymentNo?: string;
    paymentAmount?: number;
    paymentMethod?: string;
    paymentStatus?: string;
    paymentTime?: string;
    receiptFile?: string;
    remark?: string;
  }

  export interface AdInvoice {
    invoiceId?: number;
    bargainId?: number;
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
}

/** 区域管理 */
export async function getAreaList(params: any) {
  return requestClient.get('/ad/dict/area/list', { params });
}
export async function getArea(areaId: number) {
  return requestClient.get(`/ad/dict/area/${areaId}`);
}
export async function createArea(data: AdApi.Area) {
  return requestClient.post('/ad/dict/area', data);
}
export async function updateArea(data: AdApi.Area) {
  return requestClient.put('/ad/dict/area', data);
}
export async function deleteArea(areaIds: number[]) {
  return requestClient.delete(`/ad/dict/area/${areaIds.join(',')}`);
}

/** 广告位类型管理 */
export async function getPositionTypeList(params: any) {
  return requestClient.get('/ad/dict/positionType/list', { params });
}
export async function getPositionType(typeId: number) {
  return requestClient.get(`/ad/dict/positionType/${typeId}`);
}
export async function createPositionType(data: AdApi.PositionType) {
  return requestClient.post('/ad/dict/positionType', data);
}
export async function updatePositionType(data: AdApi.PositionType) {
  return requestClient.put('/ad/dict/positionType', data);
}
export async function deletePositionType(typeIds: number[]) {
  return requestClient.delete(`/ad/dict/positionType/${typeIds.join(',')}`);
}

/** 广告位管理 */
export async function getPositionList(params: any) {
  return requestClient.get('/ad/dict/position/list', { params });
}
export async function getPosition(positionId: number) {
  return requestClient.get(`/ad/dict/position/${positionId}`);
}
export async function createPosition(data: AdApi.Position) {
  return requestClient.post('/ad/dict/position', data);
}
export async function updatePosition(data: AdApi.Position) {
  return requestClient.put('/ad/dict/position', data);
}
export async function deletePosition(positionIds: number[]) {
  return requestClient.delete(`/ad/dict/position/${positionIds.join(',')}`);
}

/** 广告类型管理 */
export async function getAdTypeList(params: any) {
  return requestClient.get('/ad/dict/type/list', { params });
}
export async function getAdType(typeId: number) {
  return requestClient.get(`/ad/dict/type/${typeId}`);
}
export async function createAdType(data: AdApi.AdType) {
  return requestClient.post('/ad/dict/type', data);
}
export async function updateAdType(data: AdApi.AdType) {
  return requestClient.put('/ad/dict/type', data);
}
export async function deleteAdType(typeIds: number[]) {
  return requestClient.delete(`/ad/dict/type/${typeIds.join(',')}`);
}

/** 客户管理 */
export async function getCustomerList(params: any) {
  return requestClient.get('/ad/book/customer/list', { params });
}
export async function getCustomer(customerId: number) {
  return requestClient.get(`/ad/book/customer/${customerId}`);
}
export async function createCustomer(data: AdApi.Customer) {
  return requestClient.post('/ad/book/customer', data);
}
export async function updateCustomer(data: AdApi.Customer) {
  return requestClient.put('/ad/book/customer', data);
}
export async function deleteCustomer(customerIds: number[]) {
  return requestClient.delete(`/ad/book/customer/${customerIds.join(',')}`);
}

/** 合同管理 */
export async function getBargainList(params: any) {
  return requestClient.get('/ad/book/bargain/list', { params });
}
export async function createBargain(data: AdApi.Bargain) {
  return requestClient.post('/ad/bargain', data);
}
export async function updateBargain(data: AdApi.Bargain) {
  return requestClient.put('/ad/bargain', data);
}
export async function deleteBargain(bargainIds: number[]) {
  return requestClient.delete(`/ad/bargain/${bargainIds.join(',')}`);
}
export async function applyBargain(data: AdApi.Bargain) {
  return requestClient.post('/ad/apply/bargain', data);
}
export async function auditBargain(data: AdApi.Bargain) {
  return requestClient.put('/ad/audit/bargain', data);
}
export async function makeBargain(data: AdApi.Bargain) {
  return requestClient.put('/ad/make/bargain', data);
}

/** 广告管理 */
export async function getAdInfoList(params: any) {
  return requestClient.get('/ad/book/ad/list', { params });
}
export async function createAdInfo(data: AdApi.AdInfo) {
  return requestClient.post('/ad/ad', data);
}
export async function updateAdInfo(data: AdApi.AdInfo) {
  return requestClient.put('/ad/ad', data);
}
export async function deleteAdInfo(adIds: number[]) {
  return requestClient.delete(`/ad/ad/${adIds.join(',')}`);
}
export async function applyAdInfo(data: AdApi.AdInfo) {
  return requestClient.post('/ad/apply/ad', data);
}
export async function auditAdInfo(data: AdApi.AdInfo) {
  return requestClient.put('/ad/audit/ad', data);
}
export async function makeAdInfo(data: AdApi.AdInfo) {
  return requestClient.put('/ad/make/ad', data);
}

/** 支付管理 */
export async function getPaymentList(params: any) {
  return requestClient.get('/ad/book/cost/list', { params });
}
export async function createPayment(data: AdApi.Payment) {
  return requestClient.post('/ad/book/cost', data);
}
export async function updatePayment(data: AdApi.Payment) {
  return requestClient.put('/ad/cost', data);
}
export async function deletePayment(paymentIds: number[]) {
  return requestClient.delete(`/ad/cost/${paymentIds.join(',')}`);
}

/** 广告发票管理 */
export async function getAdInvoiceList(params: any) {
  return requestClient.get('/ad/apply/invoice/list', { params });
}
export async function createAdInvoice(data: AdApi.AdInvoice) {
  return requestClient.post('/ad/invoice', data);
}
export async function updateAdInvoice(data: AdApi.AdInvoice) {
  return requestClient.put('/ad/invoice', data);
}
export async function deleteAdInvoice(invoiceIds: number[]) {
  return requestClient.delete(`/ad/invoice/${invoiceIds.join(',')}`);
}
export async function applyAdInvoice(data: AdApi.AdInvoice) {
  return requestClient.post('/ad/apply/invoice', data);
}
export async function auditAdInvoice(data: AdApi.AdInvoice) {
  return requestClient.put('/ad/audit/invoice', data);
}
export async function makeAdInvoice(data: AdApi.AdInvoice) {
  return requestClient.put('/ad/make/invoice', data);
}

/** 广告统计 */
export async function getAdStat() {
  return requestClient.get('/ad/book/stat');
}

/** 版位统计 */
export async function getAdPositionStat() {
  return requestClient.get('/ad/book/position');
}