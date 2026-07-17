import { apiRequest, buildPageQuery } from './client'
import type {
  OwnWarehouseCreateRequest,
  OwnWarehouseResponse,
  PageParams,
  SpringPage,
} from '../types/api'

const basePath = '/api/own-warehouses'

export const ownWarehousesApi = {
  list: (params?: PageParams) =>
    apiRequest<SpringPage<OwnWarehouseResponse>>(
      `${basePath}${buildPageQuery(params)}`,
    ),
  get: (id: number) => apiRequest<OwnWarehouseResponse>(`${basePath}/${id}`),
  create: (payload: OwnWarehouseCreateRequest) =>
    apiRequest<OwnWarehouseResponse>(basePath, { method: 'POST', body: payload }),
  update: (id: number, payload: OwnWarehouseCreateRequest) =>
    apiRequest<OwnWarehouseResponse>(`${basePath}/${id}`, {
      method: 'PUT',
      body: payload,
    }),
  delete: (id: number) => apiRequest<void>(`${basePath}/${id}`, { method: 'DELETE' }),
}
