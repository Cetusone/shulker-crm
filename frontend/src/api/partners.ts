import { apiRequest, buildPageQuery } from './client'
import type {
  PageParams,
  PartnerCreateRequest,
  PartnerResponse,
  SpringPage,
} from '../types/api'

const basePath = '/api/partners'

export const partnersApi = {
  list: (params?: PageParams) =>
    apiRequest<SpringPage<PartnerResponse>>(`${basePath}${buildPageQuery(params)}`),
  get: (id: number) => apiRequest<PartnerResponse>(`${basePath}/${id}`),
  create: (payload: PartnerCreateRequest) =>
    apiRequest<PartnerResponse>(basePath, { method: 'POST', body: payload }),
  update: (id: number, payload: PartnerCreateRequest) =>
    apiRequest<PartnerResponse>(`${basePath}/${id}`, { method: 'PUT', body: payload }),
  delete: (id: number) => apiRequest<void>(`${basePath}/${id}`, { method: 'DELETE' }),
}
