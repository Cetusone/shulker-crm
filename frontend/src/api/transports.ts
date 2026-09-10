import { apiRequest } from './client'
import type { TransportCreateRequest, TransportResponse } from '../types/api'

const basePath = '/api/transports'

export const transportsApi = {
  list: () => apiRequest<TransportResponse[]>(basePath),
  get: (id: number) => apiRequest<TransportResponse>(`${basePath}/${id}`),
  create: (payload: TransportCreateRequest) =>
    apiRequest<TransportResponse>(basePath, { method: 'POST', body: payload }),
  update: (id: number, payload: TransportCreateRequest) =>
    apiRequest<TransportResponse>(`${basePath}/${id}`, { method: 'PUT', body: payload }),
  delete: (id: number) => apiRequest<void>(`${basePath}/${id}`, { method: 'DELETE' }),
  linkWarehouse: (transportId: number, warehouseId: number) =>
    apiRequest<void>(`${basePath}/${transportId}/warehouse/${warehouseId}`, {
      method: 'POST',
    }),
  unlinkWarehouse: (transportId: number, warehouseId: number) =>
    apiRequest<void>(`${basePath}/${transportId}/warehouse/${warehouseId}`, {
      method: 'DELETE',
    }),
}
