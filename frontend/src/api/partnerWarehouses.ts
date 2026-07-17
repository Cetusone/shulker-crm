import { apiRequest, buildPageQuery } from './client'
import type {
  PageParams,
  PartnerWarehouseCreateRequest,
  PartnerWarehouseResponse,
  SpringPage,
} from '../types/api'

const basePath = (partnerId: number) => `/api/partners/${partnerId}/warehouses`

export const partnerWarehousesApi = {
  list: (partnerId: number, params?: PageParams) =>
    apiRequest<SpringPage<PartnerWarehouseResponse>>(
      `${basePath(partnerId)}${buildPageQuery(params)}`,
    ),
  get: (partnerId: number, warehouseId: number) =>
    apiRequest<PartnerWarehouseResponse>(`${basePath(partnerId)}/${warehouseId}`),
  create: (partnerId: number, payload: PartnerWarehouseCreateRequest) =>
    apiRequest<PartnerWarehouseResponse>(basePath(partnerId), {
      method: 'POST',
      body: payload,
    }),
  update: (
    partnerId: number,
    warehouseId: number,
    payload: PartnerWarehouseCreateRequest,
  ) =>
    apiRequest<PartnerWarehouseResponse>(`${basePath(partnerId)}/${warehouseId}`, {
      method: 'PUT',
      body: payload,
    }),
  delete: (partnerId: number, warehouseId: number) =>
    apiRequest<void>(`${basePath(partnerId)}/${warehouseId}`, { method: 'DELETE' }),
}
