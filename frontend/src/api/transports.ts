import { apiRequest, buildPageQuery } from './client'
import type {
  PageParams,
  SpringPage,
  TransportCreateRequest,
  TransportResponse,
} from '../types/api'

const basePath = '/api/transports'

export const transportsApi = {
  list: (params?: PageParams) =>
    apiRequest<SpringPage<TransportResponse>>(
      `${basePath}${buildPageQuery(params)}`,
    ),
  get: (id: number) => apiRequest<TransportResponse>(`${basePath}/${id}`),
  create: (payload: TransportCreateRequest) =>
    apiRequest<TransportResponse>(basePath, { method: 'POST', body: payload }),
  update: (id: number, payload: TransportCreateRequest) =>
    apiRequest<TransportResponse>(`${basePath}/${id}`, { method: 'PUT', body: payload }),
  delete: (id: number) => apiRequest<void>(`${basePath}/${id}`, { method: 'DELETE' }),
  linkWarehouse: (transportId: number, warehouseId: number) =>
    apiRequest<void>(`${basePath}/${transportId}/warehouses/${warehouseId}`, {
      method: 'POST',
    }),
  unlinkWarehouse: (transportId: number, warehouseId: number) =>
    apiRequest<void>(`${basePath}/${transportId}/warehouses/${warehouseId}`, {
      method: 'DELETE',
    }),
}
