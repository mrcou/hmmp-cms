import { requestClient } from '#/api/request';

export namespace MeetingApi {
  export interface Meeting {
    meetingId?: number;
    meetingName: string;
    meetingCode?: string;
    meetingType?: string;
    startDate?: string;
    endDate?: string;
    location?: string;
    host?: string;
    contactPerson?: string;
    contactPhone?: string;
    description?: string;
    status?: string;
    maxParticipants?: number;
    actualParticipants?: number;
  }

  export interface Hotel {
    hotelId?: number;
    meetingId?: number;
    hotelName: string;
    address?: string;
    phone?: string;
    starLevel?: number;
    description?: string;
    status?: string;
  }

  export interface HotelRoom {
    roomId?: number;
    hotelId: number;
    roomType: string;
    price?: number;
    maxPeople?: number;
    totalQty?: number;
    availableQty?: number;
    description?: string;
  }

  export interface UserGroup {
    groupId?: number;
    meetingId?: number;
    groupName: string;
    description?: string;
  }

  export interface FeesPrice {
    priceId?: number;
    meetingId?: number;
    userGroupId?: number;
    priceName: string;
    price?: number;
    startDate?: string;
    endDate?: string;
  }

  export interface MeetingUser {
    userId?: number;
    meetingId?: number;
    realName: string;
    gender?: string;
    affiliation?: string;
    title?: string;
    email?: string;
    phone?: string;
    userGroupId?: number;
    isPaid?: string;
    registerTime?: string;
  }

  export interface Tickets {
    ticketId?: number;
    meetingId?: number;
    userId?: number;
    ticketType?: string;
    ticketNo?: string;
    startStation?: string;
    endStation?: string;
    departureTime?: string;
    arrivalTime?: string;
    status?: string;
  }

  export interface TravelWay {
    wayId?: number;
    meetingId?: number;
    wayName: string;
    description?: string;
    price?: number;
    maxPeople?: number;
    availablePeople?: number;
  }

  export interface TravelBooked {
    bookedId?: number;
    wayId?: number;
    userId?: number;
    userName?: string;
    peopleCount?: number;
    totalPrice?: number;
    bookTime?: string;
    status?: string;
  }

  export interface HotelBooked {
    bookedId?: number;
    roomId?: number;
    userId?: number;
    userName?: string;
    checkInDate?: string;
    checkOutDate?: string;
    roomCount?: number;
    totalPrice?: number;
    bookTime?: string;
    status?: string;
  }

  export interface Fees {
    feesId?: number;
    meetingId?: number;
    userId?: number;
    userName?: string;
    feesType?: string;
    amount?: number;
    payStatus?: string;
    payTime?: string;
    payMethod?: string;
  }
}

/** 会议管理 */
export async function getMeetingList(params: any) {
  return requestClient.get('/meeting/meeting/list', { params });
}
export async function getMeeting(meetingId: number) {
  return requestClient.get(`/meeting/meeting/${meetingId}`);
}
export async function createMeeting(data: MeetingApi.Meeting) {
  return requestClient.post('/meeting/meeting', data);
}
export async function updateMeeting(data: MeetingApi.Meeting) {
  return requestClient.put('/meeting/meeting', data);
}
export async function deleteMeeting(meetingIds: number[]) {
  return requestClient.delete(`/meeting/meeting/${meetingIds.join(',')}`);
}

/** 酒店管理 */
export async function getHotelList(params: any) {
  return requestClient.get('/meeting/hotel/list', { params });
}
export async function getHotel(hotelId: number) {
  return requestClient.get(`/meeting/hotel/${hotelId}`);
}
export async function createHotel(data: MeetingApi.Hotel) {
  return requestClient.post('/meeting/hotel', data);
}
export async function updateHotel(data: MeetingApi.Hotel) {
  return requestClient.put('/meeting/hotel', data);
}
export async function deleteHotel(hotelIds: number[]) {
  return requestClient.delete(`/meeting/hotel/${hotelIds.join(',')}`);
}

/** 房型管理 */
export async function getHotelRoomList(params: any) {
  return requestClient.get('/meeting/hotel/room/list', { params });
}
export async function createHotelRoom(data: MeetingApi.HotelRoom) {
  return requestClient.post('/meeting/hotel/room', data);
}
export async function updateHotelRoom(data: MeetingApi.HotelRoom) {
  return requestClient.put('/meeting/hotel/room', data);
}
export async function deleteHotelRoom(roomIds: number[]) {
  return requestClient.delete(`/meeting/hotel/room/${roomIds.join(',')}`);
}

/** 用户类型 */
export async function getUserGroupList(params: any) {
  return requestClient.get('/meeting/user/group/list', { params });
}
export async function createUserGroup(data: MeetingApi.UserGroup) {
  return requestClient.post('/meeting/user/group', data);
}
export async function updateUserGroup(data: MeetingApi.UserGroup) {
  return requestClient.put('/meeting/user/group', data);
}
export async function deleteUserGroup(groupIds: number[]) {
  return requestClient.delete(`/meeting/user/group/${groupIds.join(',')}`);
}

/** 注册费价格 */
export async function getFeesPriceList(params: any) {
  return requestClient.get('/meeting/fees/price/list', { params });
}
export async function createFeesPrice(data: MeetingApi.FeesPrice) {
  return requestClient.post('/meeting/fees/price', data);
}
export async function updateFeesPrice(data: MeetingApi.FeesPrice) {
  return requestClient.put('/meeting/fees/price', data);
}
export async function deleteFeesPrice(priceIds: number[]) {
  return requestClient.delete(`/meeting/fees/price/${priceIds.join(',')}`);
}

/** 参会代表 */
export async function getMeetingUserList(params: any) {
  return requestClient.get('/meeting/user/list', { params });
}
export async function createMeetingUser(data: MeetingApi.MeetingUser) {
  return requestClient.post('/meeting/user', data);
}
export async function updateMeetingUser(data: MeetingApi.MeetingUser) {
  return requestClient.put('/meeting/user', data);
}
export async function deleteMeetingUser(userIds: number[]) {
  return requestClient.delete(`/meeting/user/${userIds.join(',')}`);
}

/** 票务管理 */
export async function getTicketsList(params: any) {
  return requestClient.get('/meeting/tickets/list', { params });
}
export async function createTickets(data: MeetingApi.Tickets) {
  return requestClient.post('/meeting/tickets', data);
}
export async function updateTickets(data: MeetingApi.Tickets) {
  return requestClient.put('/meeting/tickets', data);
}
export async function deleteTickets(ticketIds: number[]) {
  return requestClient.delete(`/meeting/tickets/${ticketIds.join(',')}`);
}

/** 旅游线路 */
export async function getTravelWayList(params: any) {
  return requestClient.get('/meeting/travel/way/list', { params });
}
export async function createTravelWay(data: MeetingApi.TravelWay) {
  return requestClient.post('/meeting/travel/way', data);
}
export async function updateTravelWay(data: MeetingApi.TravelWay) {
  return requestClient.put('/meeting/travel/way', data);
}
export async function deleteTravelWay(wayIds: number[]) {
  return requestClient.delete(`/meeting/travel/way/${wayIds.join(',')}`);
}

/** 线路预订 */
export async function getTravelBookedList(params: any) {
  return requestClient.get('/meeting/travel/booked/list', { params });
}

/** 酒店预订 */
export async function getHotelBookedList(params: any) {
  return requestClient.get('/meeting/hotel/booked/list', { params });
}

/** 会议财务 */
export async function getFeesList(params: any) {
  return requestClient.get('/meeting/fees/list', { params });
}
export async function createFees(data: MeetingApi.Fees) {
  return requestClient.post('/meeting/fees', data);
}
export async function updateFees(data: MeetingApi.Fees) {
  return requestClient.put('/meeting/fees', data);
}