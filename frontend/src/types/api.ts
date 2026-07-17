export type TransportType = 'AUTO' | 'RAILWAY' | 'AVIATION'

export type DeliveryPreference = 'FASTEST' | 'CHEAPEST'
export type DeliveryPriority = DeliveryPreference

export type PageParams = {
  page?: number
  size?: number
  sort?: string
}

export type SpringPage<T> = {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
  first: boolean
  last: boolean
  numberOfElements: number
  empty: boolean
}

export type ProductCharacteristic = {
  id?: number
  attributeName: string
  attributeValue: string
}

export type ProductCreateRequest = {
  name: string
  description?: string
  sku: string
  weightKg: number
  volumeM3: number
  characteristics: ProductCharacteristic[]
}

export type ProductResponse = ProductCreateRequest & {
  id: number
  createdAt?: string
  updatedAt?: string
}

export type TransportCreateRequest = {
  transportType: TransportType
  name: string
  maxWeightKg: number
  maxVolumeM3: number
  speedKmH: number
  costPerKm: number
}

export type TransportResponse = TransportCreateRequest & {
  id: number
  createdAt?: string
  updatedAt?: string
}

export type TransportShortDto = {
  id: number
  transportType: TransportType
  name: string
}

export type OwnWarehouseCreateRequest = {
  name: string
  address?: string
  latitude: number
  longitude: number
  transportIds: number[]
}

export type OwnWarehouseResponse = {
  id: number
  name: string
  address?: string
  latitude: number
  longitude: number
  isActive: boolean
  createdAt?: string
  updatedAt?: string
  transports: TransportShortDto[]
}

export type PartnerCreateRequest = {
  name: string
  apiKey: string
  contactEmail?: string
  isActive: boolean
}

export type PartnerResponse = Omit<PartnerCreateRequest, 'apiKey'> & {
  id: number
  createdAt?: string
  updatedAt?: string
}

export type PartnerWarehouseCreateRequest = {
  partnerId: number
  name: string
  address?: string
  latitude: number
  longitude: number
  acceptsLand: boolean
  acceptsSea: boolean
  acceptsAir: boolean
}

export type PartnerWarehouseResponse = PartnerWarehouseCreateRequest & {
  id: number
  isActive: boolean
  createdAt?: string
  updatedAt?: string
}

export type StockCreateRequest = {
  productId: number
  quantity: number
  reason?: string
}

export type StockResponse = {
  id: number
  ownWarehouseId: number
  productId: number
  quantity: number
  reservedQuantity: number
  updatedAt?: string
}

export type DeliveryOptionRequest = {
  productId: number
  quantity: number
  partnerId: number
  partnerWarehouseId: number
  preference: DeliveryPreference
}

export type DeliveryOptionResponse = {
  sourceWarehouseId: number
  sourceWarehouseName: string
  transportId: number
  transportName: string
  transportType: TransportType
  availableQuantity: number
  distanceKm: number
  totalWeightKg: number
  totalVolumeM3: number
  requiredTrips: number
  estimatedTimeHours: number
  estimatedCost: number
}

export type DeliveryCalculationResponse = {
  appliedPreference: DeliveryPreference
  partnerWarehouseId: number
  productId: number
  requestedQuantity: number
  options: DeliveryOptionResponse[]
}

export type ApiValidationError = {
  timestamp?: string
  status: number
  error: string
  message: string
  errors?: Record<string, string>
  path?: string
}
