import type { ApiValidationError, PageParams } from '../types/api'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? ''

export class ApiError extends Error {
  status: number
  payload?: ApiValidationError

  constructor(status: number, message: string, payload?: ApiValidationError) {
    super(message)
    this.name = 'ApiError'
    this.status = status
    this.payload = payload
  }
}

type RequestOptions = {
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
  body?: unknown
}

export function buildPageQuery(params: PageParams = {}) {
  const searchParams = new URLSearchParams()

  if (params.page !== undefined) {
    searchParams.set('page', String(params.page))
  }

  if (params.size !== undefined) {
    searchParams.set('size', String(params.size))
  }

  if (params.sort) {
    searchParams.set('sort', params.sort)
  }

  const query = searchParams.toString()
  return query ? `?${query}` : ''
}

export async function apiRequest<T>(path: string, options: RequestOptions = {}): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    method: options.method ?? 'GET',
    headers: options.body ? { 'Content-Type': 'application/json' } : undefined,
    body: options.body ? JSON.stringify(options.body) : undefined,
  })

  if (response.status === 204) {
    return undefined as T
  }

  const payload = await response.json().catch(() => undefined)

  if (!response.ok) {
    const message =
      typeof payload?.message === 'string' ? payload.message : 'Request failed'
    throw new ApiError(response.status, message, payload)
  }

  return payload as T
}
