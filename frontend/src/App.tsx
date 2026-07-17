import {
  ApiOutlined,
  AppstoreOutlined,
  CarOutlined,
  DatabaseOutlined,
  NodeIndexOutlined,
  PlusOutlined,
  ReloadOutlined,
  ShopOutlined,
  TeamOutlined,
} from '@ant-design/icons'
import {
  Alert,
  Button,
  Checkbox,
  ConfigProvider,
  Empty,
  Form,
  Input,
  InputNumber,
  Layout,
  Menu,
  Row,
  Col,
  Select,
  Segmented,
  Space,
  Statistic,
  Table,
  Tag,
  Typography,
  message,
} from 'antd'
import type { TableProps } from 'antd'
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import { useMemo, useState } from 'react'
import {
  BrowserRouter,
  Link,
  Navigate,
  Route,
  Routes,
  useLocation,
} from 'react-router-dom'
import './App.css'
import { ApiError } from './api/client'
import { ownWarehousesApi } from './api/ownWarehouses'
import { partnerWarehousesApi } from './api/partnerWarehouses'
import { partnersApi } from './api/partners'
import { productsApi } from './api/products'
import { routingApi } from './api/routing'
import { stocksApi } from './api/stocks'
import { transportsApi } from './api/transports'
import type {
  DeliveryPriority,
  DeliveryCalculationResponse,
  OwnWarehouseCreateRequest,
  OwnWarehouseResponse,
  PartnerCreateRequest,
  PartnerResponse,
  PartnerWarehouseCreateRequest,
  PartnerWarehouseResponse,
  ProductCharacteristic,
  ProductCreateRequest,
  ProductResponse,
  SpringPage,
  StockResponse,
  TransportCreateRequest,
  TransportResponse,
  TransportType,
} from './types/api'

const { Content, Header, Sider } = Layout
const { Text, Title } = Typography

const defaultPageParams = { size: 100 }

type StockFormValues = {
  ownWarehouseId: number
  productId: number
  quantity: number
  reason?: string
}

type DeliveryOption = {
  sourceWarehouseId: number
  sourceWarehouseName: string
  partnerWarehouseId: number
  partnerWarehouseName: string
  transportId: number
  transportName: string
  transportType: TransportType
  distanceKm: number
  requiredWeightKg: number
  requiredVolumeM3: number
  tripsCount: number
  estimatedTimeHours: number
  estimatedCost: number
  availableQuantity: number
}

const initialProducts: ProductResponse[] = [
  {
    id: 1,
    name: 'Ноутбук Apple MacBook',
    description: 'Корпоративная партия ноутбуков',
    sku: 'APL-MAC-001',
    weightKg: 1.6,
    volumeM3: 0.012,
    characteristics: [
      { id: 1, attributeName: 'brand', attributeValue: 'Apple' },
      { id: 2, attributeName: 'screen', attributeValue: '14 inch' },
    ],
  },
  {
    id: 2,
    name: 'Монитор Dell UltraSharp',
    description: 'Офисный монитор 27 дюймов',
    sku: 'DEL-MON-027',
    weightKg: 5.2,
    volumeM3: 0.058,
    characteristics: [{ id: 3, attributeName: 'diagonal', attributeValue: '27' }],
  },
  {
    id: 3,
    name: 'Короб кабелей HDMI',
    description: 'Партия кабелей для рабочих мест',
    sku: 'HDMI-BOX-100',
    weightKg: 14,
    volumeM3: 0.04,
    characteristics: [{ id: 4, attributeName: 'pack', attributeValue: '100 pcs' }],
  },
]

const initialTransports: TransportResponse[] = [
  {
    id: 1,
    name: 'Авто 10 тонн',
    transportType: 'AUTO',
    maxWeightKg: 10_000,
    maxVolumeM3: 45,
    speedKmH: 80,
    costPerKm: 50,
  },
  {
    id: 2,
    name: 'Ж/Д контейнер',
    transportType: 'RAILWAY',
    maxWeightKg: 26_000,
    maxVolumeM3: 76,
    speedKmH: 60,
    costPerKm: 24,
  },
  {
    id: 3,
    name: 'Авиа срочная',
    transportType: 'AVIATION',
    maxWeightKg: 3_200,
    maxVolumeM3: 18,
    speedKmH: 680,
    costPerKm: 310,
  },
]

const initialOwnWarehouses: OwnWarehouseResponse[] = [
  {
    id: 1,
    name: 'Склад Москва',
    address: 'Москва, ул. Складская, 1',
    latitude: 55.7558,
    longitude: 37.6173,
    isActive: true,
    transports: [
      { id: 1, transportType: 'AUTO', name: 'Авто 10 тонн' },
      { id: 2, transportType: 'RAILWAY', name: 'Ж/Д контейнер' },
      { id: 3, transportType: 'AVIATION', name: 'Авиа срочная' },
    ],
  },
  {
    id: 2,
    name: 'Склад Санкт-Петербург',
    address: 'Санкт-Петербург, Московский пр., 5',
    latitude: 59.9311,
    longitude: 30.3609,
    isActive: true,
    transports: [
      { id: 1, transportType: 'AUTO', name: 'Авто 10 тонн' },
      { id: 2, transportType: 'RAILWAY', name: 'Ж/Д контейнер' },
    ],
  },
]

const initialPartners: PartnerResponse[] = [
  {
    id: 1,
    name: 'Kazan Logistics',
    contactEmail: 'ops@kazan-logistics.test',
    isActive: true,
  },
  {
    id: 2,
    name: 'Ural Trade Hub',
    contactEmail: 'dispatch@ural-hub.test',
    isActive: true,
  },
]

const initialPartnerWarehouses: PartnerWarehouseResponse[] = [
  {
    id: 1,
    partnerId: 1,
    name: 'ПВЗ Казань',
    address: 'Казань, ул. Логистическая, 8',
    latitude: 55.7961,
    longitude: 49.1064,
    acceptsLand: true,
    acceptsSea: false,
    acceptsAir: true,
    isActive: true,
  },
  {
    id: 2,
    partnerId: 2,
    name: 'Терминал Екатеринбург',
    address: 'Екатеринбург, ул. Транспортная, 17',
    latitude: 56.8389,
    longitude: 60.6057,
    acceptsLand: true,
    acceptsSea: false,
    acceptsAir: false,
    isActive: true,
  },
]

const initialStocks: StockResponse[] = [
  { id: 1, ownWarehouseId: 1, productId: 1, quantity: 54, reservedQuantity: 4 },
  { id: 2, ownWarehouseId: 1, productId: 2, quantity: 35, reservedQuantity: 0 },
  { id: 3, ownWarehouseId: 2, productId: 1, quantity: 18, reservedQuantity: 2 },
  { id: 4, ownWarehouseId: 2, productId: 3, quantity: 430, reservedQuantity: 10 },
]

const transportLabels: Record<TransportType, string> = {
  AUTO: 'Авто',
  RAILWAY: 'Ж/Д',
  AVIATION: 'Авиа',
}

const priorityLabels: Record<DeliveryPriority, string> = {
  CHEAPEST: 'Дешевле',
  FASTEST: 'Быстрее',
}

const endpointGroups = [
  { label: 'Products', value: 'GET/POST /api/products' },
  { label: 'Own warehouses', value: 'GET/POST /api/own-warehouses' },
  { label: 'Transports', value: 'GET/POST /api/transports' },
  { label: 'Partners', value: 'GET/POST /api/partners' },
  {
    label: 'Partner warehouses',
    value: 'GET/POST /api/partners/{partnerId}/warehouses',
  },
  { label: 'Stock', value: 'GET/POST /api/warehouse/{id}/stock' },
  { label: 'Routing', value: 'POST /api/routing/calculate' },
]

function nextId<T extends { id: number }>(items: T[]) {
  return items.length ? Math.max(...items.map((item) => item.id)) + 1 : 1
}

function pageContent<T>(page: SpringPage<T> | undefined, fallback: T[]) {
  return page?.content ?? fallback
}

function isBackendValidationError(error: unknown) {
  return error instanceof ApiError
}

function showApiError(error: unknown) {
  const fallbackMessage = 'Backend вернул ошибку. Проверьте данные формы.'

  if (error instanceof ApiError) {
    message.error(error.message || fallbackMessage)
    return
  }

  message.error(fallbackMessage)
}

function formatNumber(value: number, fractionDigits = 2) {
  return new Intl.NumberFormat('ru-RU', {
    maximumFractionDigits: fractionDigits,
  }).format(value)
}

function parseCharacteristics(value?: string): ProductCharacteristic[] {
  if (!value?.trim()) {
    return []
  }

  return value
    .split('\n')
    .map((line) => line.trim())
    .filter(Boolean)
    .map((line) => {
      const [attributeName, ...rest] = line.split('=')
      return {
        attributeName: attributeName.trim(),
        attributeValue: rest.join('=').trim() || '-',
      }
    })
}

function distanceKm(
  from: { latitude: number; longitude: number },
  to: { latitude: number; longitude: number },
) {
  const earthRadiusKm = 6371
  const latitudeDelta = ((to.latitude - from.latitude) * Math.PI) / 180
  const longitudeDelta = ((to.longitude - from.longitude) * Math.PI) / 180
  const a =
    Math.sin(latitudeDelta / 2) ** 2 +
    Math.cos((from.latitude * Math.PI) / 180) *
      Math.cos((to.latitude * Math.PI) / 180) *
      Math.sin(longitudeDelta / 2) ** 2
  return earthRadiusKm * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
}

function isTransportAccepted(
  transportType: TransportType,
  warehouse: PartnerWarehouseResponse,
) {
  if (transportType === 'AVIATION') {
    return warehouse.acceptsAir
  }

  return warehouse.acceptsLand
}

function EndpointTag({ value }: { value: string }) {
  return <Tag color="blue">{value}</Tag>
}

function PageHeader({
  title,
  endpoint,
  description,
}: {
  title: string
  endpoint: string
  description: string
}) {
  return (
    <div className="page-header">
      <div>
        <Title level={4}>{title}</Title>
        <Text type="secondary">{description}</Text>
      </div>
      <EndpointTag value={endpoint} />
    </div>
  )
}

function AppMenu() {
  const location = useLocation()

  return (
    <Menu
      className="side-menu"
      mode="inline"
      selectedKeys={[location.pathname]}
      items={[
        {
          key: '/',
          icon: <AppstoreOutlined />,
          label: <Link to="/">Сводка</Link>,
        },
        {
          key: '/products',
          icon: <DatabaseOutlined />,
          label: <Link to="/products">Товары</Link>,
        },
        {
          key: '/own-warehouses',
          icon: <ShopOutlined />,
          label: <Link to="/own-warehouses">Наши склады</Link>,
        },
        {
          key: '/partners',
          icon: <TeamOutlined />,
          label: <Link to="/partners">Партнеры</Link>,
        },
        {
          key: '/partner-warehouses',
          icon: <ShopOutlined />,
          label: <Link to="/partner-warehouses">Склады партнеров</Link>,
        },
        {
          key: '/transports',
          icon: <CarOutlined />,
          label: <Link to="/transports">Транспорт</Link>,
        },
        {
          key: '/stocks',
          icon: <DatabaseOutlined />,
          label: <Link to="/stocks">Остатки</Link>,
        },
        {
          key: '/delivery',
          icon: <NodeIndexOutlined />,
          label: <Link to="/delivery">Расчет доставки</Link>,
        },
      ]}
    />
  )
}

function Dashboard({
  products,
  transports,
  ownWarehouses,
  partners,
  partnerWarehouses,
  stocks,
}: {
  products: ProductResponse[]
  transports: TransportResponse[]
  ownWarehouses: OwnWarehouseResponse[]
  partners: PartnerResponse[]
  partnerWarehouses: PartnerWarehouseResponse[]
  stocks: StockResponse[]
}) {
  return (
    <>
      <div className="summary-grid">
        <Statistic title="Товары" value={products.length} />
        <Statistic title="Наши склады" value={ownWarehouses.length} />
        <Statistic title="Партнеры" value={partners.length} />
        <Statistic title="Склады партнеров" value={partnerWarehouses.length} />
        <Statistic title="Позиций на остатках" value={stocks.length} />
      </div>

      <div className="panel field-block">
        <Space direction="vertical" size={12} className="full-width">
          <Alert
            type="success"
            showIcon
            message="Backend API v0.0.4 pulled"
            description="Контракт стал больше: списки возвращают Spring Page, добавлен routing endpoint POST /api/routing/calculate."
          />
          <Alert
            type="info"
            showIcon
            message="Vite proxy настроен"
            description="Фронт может ходить на /api, а Vite прокинет запросы на http://localhost:8080. Это уменьшает риск CORS-блокировки в браузере."
          />
        </Space>
      </div>

      <div className="workspace">
        <div className="panel">
          <Title level={5}>Актуальный API-контур</Title>
          <Space direction="vertical" size={10}>
            {endpointGroups.map((item) => (
              <div key={item.value} className="endpoint-row">
                <Text strong>{item.label}</Text>
                <EndpointTag value={item.value} />
              </div>
            ))}
          </Space>
        </div>

        <div className="panel">
          <Title level={5}>Следующий полезный шаг</Title>
          <Text>
            После фикса backend-сборки можно заменить mock-данные на React Query и
            подключить списки GET: products, transports, own warehouses, partners.
          </Text>
          <div className="metric-row">
            <Statistic title="Транспортов" value={transports.length} />
            <Statistic
              title="Общий остаток"
              value={stocks.reduce((sum, item) => sum + item.quantity, 0)}
            />
          </div>
        </div>
      </div>
    </>
  )
}

function ProductsPage({
  products,
  addProduct,
}: {
  products: ProductResponse[]
  addProduct: (values: ProductCreateRequest) => Promise<boolean>
}) {
  const [form] = Form.useForm<ProductCreateRequest & { characteristicsText?: string }>()

  const columns: TableProps<ProductResponse>['columns'] = [
    { title: 'SKU', dataIndex: 'sku', width: 140 },
    { title: 'Название', dataIndex: 'name' },
    {
      title: 'Вес',
      dataIndex: 'weightKg',
      render: (value: number) => `${formatNumber(value)} кг`,
      width: 120,
    },
    {
      title: 'Объем',
      dataIndex: 'volumeM3',
      render: (value: number) => `${formatNumber(value, 3)} м³`,
      width: 120,
    },
    {
      title: 'Характеристики',
      dataIndex: 'characteristics',
      render: (value: ProductCharacteristic[]) => (
        <Space wrap>
          {value.map((item) => (
            <Tag key={`${item.attributeName}-${item.attributeValue}`}>
              {item.attributeName}: {item.attributeValue}
            </Tag>
          ))}
        </Space>
      ),
    },
  ]

  return (
    <>
      <PageHeader
        title="Товары"
        endpoint="GET/POST /api/products"
        description="Форма и таблица синхронизированы с ProductCreateRequest и ProductResponse."
      />
      <div className="workspace">
        <div className="panel form-panel">
          <Title level={5}>Создать товар</Title>
          <Form
            form={form}
            layout="vertical"
            onFinish={async (values) => {
              const saved = await addProduct({
                ...values,
                characteristics: parseCharacteristics(values.characteristicsText),
              })
              if (saved) {
                form.resetFields()
              }
            }}
          >
            <Form.Item name="name" label="Название" rules={[{ required: true }]}>
              <Input placeholder="Монитор Dell UltraSharp" />
            </Form.Item>
            <Form.Item name="sku" label="SKU" rules={[{ required: true }]}>
              <Input placeholder="DEL-MON-027" />
            </Form.Item>
            <Row gutter={12}>
              <Col span={12}>
                <Form.Item name="weightKg" label="Вес, кг" rules={[{ required: true }]}>
                  <InputNumber className="full-width" min={0.01} step={0.1} />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item name="volumeM3" label="Объем, м³" rules={[{ required: true }]}>
                  <InputNumber className="full-width" min={0.001} step={0.001} />
                </Form.Item>
              </Col>
            </Row>
            <Form.Item name="description" label="Описание">
              <Input.TextArea rows={3} />
            </Form.Item>
            <Form.Item name="characteristicsText" label="Характеристики">
              <Input.TextArea rows={4} placeholder={'brand=Apple\nscreen=14 inch'} />
            </Form.Item>
            <Button type="primary" htmlType="submit" icon={<PlusOutlined />} block>
              Создать товар
            </Button>
          </Form>
        </div>
        <Table
          rowKey="id"
          columns={columns}
          dataSource={products}
          pagination={false}
          scroll={{ x: 900 }}
        />
      </div>
    </>
  )
}

function TransportsPage({
  transports,
  addTransport,
}: {
  transports: TransportResponse[]
  addTransport: (values: TransportCreateRequest) => Promise<boolean>
}) {
  const [form] = Form.useForm<TransportCreateRequest>()

  const columns: TableProps<TransportResponse>['columns'] = [
    {
      title: 'Тип',
      dataIndex: 'transportType',
      render: (value: TransportType) => <Tag>{transportLabels[value]}</Tag>,
      width: 110,
    },
    { title: 'Название', dataIndex: 'name' },
    {
      title: 'Грузоподъемность',
      dataIndex: 'maxWeightKg',
      render: (value: number) => `${formatNumber(value, 0)} кг`,
    },
    {
      title: 'Объем',
      dataIndex: 'maxVolumeM3',
      render: (value: number) => `${formatNumber(value)} м³`,
    },
    {
      title: 'Скорость',
      dataIndex: 'speedKmH',
      render: (value: number) => `${formatNumber(value, 0)} км/ч`,
    },
    {
      title: 'Цена',
      dataIndex: 'costPerKm',
      render: (value: number) => `${formatNumber(value, 0)} / км`,
    },
  ]

  return (
    <>
      <PageHeader
        title="Транспорт"
        endpoint="GET/POST /api/transports"
        description="Вместимость теперь хранится как maxWeightKg и maxVolumeM3."
      />
      <div className="workspace">
        <div className="panel form-panel">
          <Title level={5}>Создать транспорт</Title>
          <Form
            form={form}
            layout="vertical"
            initialValues={{ transportType: 'AUTO' }}
            onFinish={async (values) => {
              if (await addTransport(values)) {
                form.resetFields()
              }
            }}
          >
            <Form.Item name="transportType" label="Тип" rules={[{ required: true }]}>
              <Select
                options={Object.entries(transportLabels).map(([value, label]) => ({
                  value,
                  label,
                }))}
              />
            </Form.Item>
            <Form.Item name="name" label="Название" rules={[{ required: true }]}>
              <Input placeholder="Авто 10 тонн" />
            </Form.Item>
            <Row gutter={12}>
              <Col span={12}>
                <Form.Item
                  name="maxWeightKg"
                  label="Макс. вес, кг"
                  rules={[{ required: true }]}
                >
                  <InputNumber className="full-width" min={1} />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item
                  name="maxVolumeM3"
                  label="Макс. объем, м³"
                  rules={[{ required: true }]}
                >
                  <InputNumber className="full-width" min={0.1} step={0.1} />
                </Form.Item>
              </Col>
            </Row>
            <Row gutter={12}>
              <Col span={12}>
                <Form.Item name="speedKmH" label="Скорость" rules={[{ required: true }]}>
                  <InputNumber className="full-width" min={1} />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item name="costPerKm" label="Цена за км" rules={[{ required: true }]}>
                  <InputNumber className="full-width" min={1} />
                </Form.Item>
              </Col>
            </Row>
            <Button type="primary" htmlType="submit" icon={<PlusOutlined />} block>
              Создать транспорт
            </Button>
          </Form>
        </div>
        <Table
          rowKey="id"
          columns={columns}
          dataSource={transports}
          pagination={false}
          scroll={{ x: 900 }}
        />
      </div>
    </>
  )
}

function OwnWarehousesPage({
  warehouses,
  transports,
  addWarehouse,
}: {
  warehouses: OwnWarehouseResponse[]
  transports: TransportResponse[]
  addWarehouse: (values: OwnWarehouseCreateRequest) => Promise<boolean>
}) {
  const [form] = Form.useForm<OwnWarehouseCreateRequest>()

  const columns: TableProps<OwnWarehouseResponse>['columns'] = [
    { title: 'Название', dataIndex: 'name' },
    { title: 'Адрес', dataIndex: 'address' },
    {
      title: 'Координаты',
      render: (_, item) => `${item.latitude}, ${item.longitude}`,
      width: 190,
    },
    {
      title: 'Транспорт',
      dataIndex: 'transports',
      render: (value: OwnWarehouseResponse['transports']) => (
        <Space wrap>
          {value.map((item) => (
            <Tag key={item.id}>{transportLabels[item.transportType]}</Tag>
          ))}
        </Space>
      ),
    },
    {
      title: 'Статус',
      dataIndex: 'isActive',
      render: (value: boolean) => (
        <Tag color={value ? 'green' : 'default'}>{value ? 'Активен' : 'Архив'}</Tag>
      ),
      width: 110,
    },
  ]

  return (
    <>
      <PageHeader
        title="Наши склады"
        endpoint="GET/POST /api/own-warehouses"
        description="Создание требует хотя бы один transportId, как в backend validation."
      />
      <div className="workspace">
        <div className="panel form-panel">
          <Title level={5}>Создать склад</Title>
          <Form
            form={form}
            layout="vertical"
            onFinish={async (values) => {
              if (await addWarehouse(values)) {
                form.resetFields()
              }
            }}
          >
            <Form.Item name="name" label="Название" rules={[{ required: true }]}>
              <Input placeholder="Склад Москва" />
            </Form.Item>
            <Form.Item name="address" label="Адрес">
              <Input placeholder="Москва, ул. Складская, 1" />
            </Form.Item>
            <Row gutter={12}>
              <Col span={12}>
                <Form.Item name="latitude" label="Широта" rules={[{ required: true }]}>
                  <InputNumber className="full-width" min={-90} max={90} step={0.0001} />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item name="longitude" label="Долгота" rules={[{ required: true }]}>
                  <InputNumber className="full-width" min={-180} max={180} step={0.0001} />
                </Form.Item>
              </Col>
            </Row>
            <Form.Item
              name="transportIds"
              label="Доступный транспорт"
              rules={[{ required: true }]}
            >
              <Select
                mode="multiple"
                options={transports.map((item) => ({
                  value: item.id,
                  label: `${transportLabels[item.transportType]} - ${item.name}`,
                }))}
              />
            </Form.Item>
            <Button type="primary" htmlType="submit" icon={<PlusOutlined />} block>
              Создать склад
            </Button>
          </Form>
        </div>
        <Table
          rowKey="id"
          columns={columns}
          dataSource={warehouses}
          pagination={false}
          scroll={{ x: 1000 }}
        />
      </div>
    </>
  )
}

function PartnersPage({
  partners,
  addPartner,
}: {
  partners: PartnerResponse[]
  addPartner: (values: PartnerCreateRequest) => Promise<boolean>
}) {
  const [form] = Form.useForm<PartnerCreateRequest>()

  const columns: TableProps<PartnerResponse>['columns'] = [
    { title: 'Компания', dataIndex: 'name' },
    { title: 'Email', dataIndex: 'contactEmail' },
    {
      title: 'Статус',
      dataIndex: 'isActive',
      render: (value: boolean) => (
        <Tag color={value ? 'green' : 'default'}>{value ? 'Активен' : 'Отключен'}</Tag>
      ),
      width: 120,
    },
  ]

  return (
    <>
      <PageHeader
        title="Партнеры"
        endpoint="GET/POST /api/partners"
        description="Новый backend-модуль. API key отправляется только в request."
      />
      <div className="workspace">
        <div className="panel form-panel">
          <Title level={5}>Создать партнера</Title>
          <Form
            form={form}
            layout="vertical"
            initialValues={{ isActive: true }}
            onFinish={async (values) => {
              if (await addPartner(values)) {
                form.resetFields()
              }
            }}
          >
            <Form.Item name="name" label="Компания" rules={[{ required: true }]}>
              <Input placeholder="Kazan Logistics" />
            </Form.Item>
            <Form.Item name="apiKey" label="API key" rules={[{ required: true }]}>
              <Input.Password placeholder="partner-api-key" />
            </Form.Item>
            <Form.Item name="contactEmail" label="Email">
              <Input placeholder="ops@example.com" />
            </Form.Item>
            <Form.Item name="isActive" valuePropName="checked">
              <Checkbox>Активен</Checkbox>
            </Form.Item>
            <Button type="primary" htmlType="submit" icon={<PlusOutlined />} block>
              Создать партнера
            </Button>
          </Form>
        </div>
        <Table rowKey="id" columns={columns} dataSource={partners} pagination={false} />
      </div>
    </>
  )
}

function PartnerWarehousesPage({
  partners,
  warehouses,
  addWarehouse,
}: {
  partners: PartnerResponse[]
  warehouses: PartnerWarehouseResponse[]
  addWarehouse: (values: PartnerWarehouseCreateRequest) => Promise<boolean>
}) {
  const [form] = Form.useForm<PartnerWarehouseCreateRequest>()

  const columns: TableProps<PartnerWarehouseResponse>['columns'] = [
    { title: 'Название', dataIndex: 'name' },
    {
      title: 'Партнер',
      dataIndex: 'partnerId',
      render: (value: number) => partners.find((item) => item.id === value)?.name ?? value,
    },
    { title: 'Адрес', dataIndex: 'address' },
    {
      title: 'Принимает',
      render: (_, item) => (
        <Space wrap>
          {item.acceptsLand && <Tag>Наземный</Tag>}
          {item.acceptsSea && <Tag>Морской</Tag>}
          {item.acceptsAir && <Tag>Авиа</Tag>}
        </Space>
      ),
      width: 210,
    },
    {
      title: 'Статус',
      dataIndex: 'isActive',
      render: (value: boolean) => (
        <Tag color={value ? 'green' : 'default'}>{value ? 'Активен' : 'Отключен'}</Tag>
      ),
      width: 120,
    },
  ]

  return (
    <>
      <PageHeader
        title="Склады партнеров"
        endpoint="GET/POST /api/partners/{partnerId}/warehouses"
        description="Отдельная сущность partner warehouses с флагами acceptsLand, acceptsSea, acceptsAir."
      />
      <div className="workspace">
        <div className="panel form-panel">
          <Title level={5}>Создать склад партнера</Title>
          <Form
            form={form}
            layout="vertical"
            initialValues={{ acceptsLand: true, acceptsSea: false, acceptsAir: false }}
            onFinish={async (values) => {
              if (await addWarehouse(values)) {
                form.resetFields()
              }
            }}
          >
            <Form.Item name="partnerId" label="Партнер" rules={[{ required: true }]}>
              <Select
                options={partners.map((item) => ({ value: item.id, label: item.name }))}
              />
            </Form.Item>
            <Form.Item name="name" label="Название" rules={[{ required: true }]}>
              <Input placeholder="ПВЗ Казань" />
            </Form.Item>
            <Form.Item name="address" label="Адрес">
              <Input placeholder="Казань, ул. Логистическая, 8" />
            </Form.Item>
            <Row gutter={12}>
              <Col span={12}>
                <Form.Item name="latitude" label="Широта" rules={[{ required: true }]}>
                  <InputNumber className="full-width" min={-90} max={90} step={0.0001} />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item name="longitude" label="Долгота" rules={[{ required: true }]}>
                  <InputNumber className="full-width" min={-180} max={180} step={0.0001} />
                </Form.Item>
              </Col>
            </Row>
            <Space direction="vertical">
              <Form.Item name="acceptsLand" valuePropName="checked">
                <Checkbox>Наземный транспорт</Checkbox>
              </Form.Item>
              <Form.Item name="acceptsSea" valuePropName="checked">
                <Checkbox>Морской транспорт</Checkbox>
              </Form.Item>
              <Form.Item name="acceptsAir" valuePropName="checked">
                <Checkbox>Авиа транспорт</Checkbox>
              </Form.Item>
            </Space>
            <Button type="primary" htmlType="submit" icon={<PlusOutlined />} block>
              Создать склад партнера
            </Button>
          </Form>
        </div>
        <Table
          rowKey="id"
          columns={columns}
          dataSource={warehouses}
          pagination={false}
          scroll={{ x: 900 }}
        />
      </div>
    </>
  )
}

function StocksPage({
  stocks,
  products,
  warehouses,
  addStock,
}: {
  stocks: StockResponse[]
  products: ProductResponse[]
  warehouses: OwnWarehouseResponse[]
  addStock: (values: StockFormValues) => Promise<boolean>
}) {
  const [form] = Form.useForm<StockFormValues>()

  const rows = stocks.map((item) => ({
    ...item,
    productName: products.find((product) => product.id === item.productId)?.name ?? item.productId,
    warehouseName:
      warehouses.find((warehouse) => warehouse.id === item.ownWarehouseId)?.name ??
      item.ownWarehouseId,
  }))

  const columns: TableProps<(typeof rows)[number]>['columns'] = [
    { title: 'Склад', dataIndex: 'warehouseName' },
    { title: 'Товар', dataIndex: 'productName' },
    { title: 'Количество', dataIndex: 'quantity', width: 130 },
    { title: 'Резерв', dataIndex: 'reservedQuantity', width: 100 },
  ]

  return (
    <>
      <PageHeader
        title="Остатки"
        endpoint="GET/POST /api/warehouse/{id}/stock"
        description="Backend добавляет товар на конкретный наш склад через id склада в path."
      />
      <div className="workspace">
        <div className="panel form-panel">
          <Title level={5}>Приемка товара</Title>
          <Form
            form={form}
            layout="vertical"
            onFinish={async (values) => {
              if (await addStock(values)) {
                form.resetFields()
              }
            }}
          >
            <Form.Item name="ownWarehouseId" label="Склад" rules={[{ required: true }]}>
              <Select
                options={warehouses.map((item) => ({ value: item.id, label: item.name }))}
              />
            </Form.Item>
            <Form.Item name="productId" label="Товар" rules={[{ required: true }]}>
              <Select
                options={products.map((item) => ({
                  value: item.id,
                  label: `${item.sku} - ${item.name}`,
                }))}
              />
            </Form.Item>
            <Form.Item name="quantity" label="Количество" rules={[{ required: true }]}>
              <InputNumber className="full-width" min={1} />
            </Form.Item>
            <Form.Item name="reason" label="Причина">
              <Input placeholder="Поставка, инвентаризация, корректировка" />
            </Form.Item>
            <Button type="primary" htmlType="submit" icon={<PlusOutlined />} block>
              Добавить остаток
            </Button>
          </Form>
        </div>
        <Table rowKey="id" columns={columns} dataSource={rows} pagination={false} />
      </div>
    </>
  )
}

function DeliveryPage({
  products,
  transports,
  ownWarehouses,
  partnerWarehouses,
  stocks,
}: {
  products: ProductResponse[]
  transports: TransportResponse[]
  ownWarehouses: OwnWarehouseResponse[]
  partnerWarehouses: PartnerWarehouseResponse[]
  stocks: StockResponse[]
}) {
  const [priority, setPriority] = useState<DeliveryPriority>('CHEAPEST')
  const [options, setOptions] = useState<DeliveryOption[]>([])
  const [form] = Form.useForm<{
    productId: number
    quantity: number
    partnerWarehouseId: number
  }>()
  const calculateMutation = useMutation({
    mutationFn: routingApi.calculate,
  })

  const columns: TableProps<DeliveryOption>['columns'] = [
    { title: 'Наш склад', dataIndex: 'sourceWarehouseName' },
    { title: 'Склад партнера', dataIndex: 'partnerWarehouseName' },
    {
      title: 'Транспорт',
      render: (_, item) => `${transportLabels[item.transportType]} - ${item.transportName}`,
    },
    {
      title: 'Дистанция',
      dataIndex: 'distanceKm',
      render: (value: number) => `${formatNumber(value, 0)} км`,
    },
    { title: 'Рейсы', dataIndex: 'tripsCount', width: 90 },
    {
      title: 'Время',
      dataIndex: 'estimatedTimeHours',
      render: (value: number) => `${formatNumber(value, 1)} ч`,
    },
    {
      title: 'Стоимость',
      dataIndex: 'estimatedCost',
      render: (value: number) => `${formatNumber(value, 0)}`,
    },
  ]

  return (
    <>
      <PageHeader
        title="Расчет доставки"
        endpoint="POST /api/routing/calculate"
        description="Экран готов к backend-расчету DeliveryOptionRequest/DeliveryCalculationResponse; mock-расчет оставлен для демо."
      />
      <Alert
        className="field-block"
        type="info"
        showIcon
        message="Routing API добавлен"
        description="Форма сначала вызывает POST /api/routing/calculate. Если backend недоступен, экран оставляет локальный mock-расчет для демо."
      />
      <div className="workspace">
        <div className="panel form-panel">
          <Title level={5}>Параметры доставки</Title>
          <Form
            form={form}
            layout="vertical"
            initialValues={{
              quantity: 10,
              productId: products[0]?.id,
              partnerWarehouseId: partnerWarehouses[0]?.id,
            }}
            onFinish={async (values) => {
              const partnerWarehouse = partnerWarehouses.find(
                (warehouse) => warehouse.id === values.partnerWarehouseId,
              )

              if (!partnerWarehouse) {
                message.error('Выберите склад партнера')
                return
              }

              try {
                const response = await calculateMutation.mutateAsync({
                  productId: values.productId,
                  quantity: values.quantity,
                  partnerId: partnerWarehouse.partnerId,
                  partnerWarehouseId: values.partnerWarehouseId,
                  preference: priority,
                })
                setOptions(mapDeliveryOptions(response, partnerWarehouses))
                message.success('Варианты доставки рассчитаны через API')
              } catch (error) {
                if (isBackendValidationError(error)) {
                  showApiError(error)
                  return
                }

                const nextOptions = calculateDeliveryOptions({
                  productId: values.productId,
                  quantity: values.quantity,
                  partnerWarehouseId: values.partnerWarehouseId,
                  priority,
                  products,
                  transports,
                  ownWarehouses,
                  partnerWarehouses,
                  stocks,
                })
                setOptions(nextOptions)
                message.warning('Backend недоступен, варианты рассчитаны в mock-режиме')
              }
            }}
          >
            <Form.Item name="productId" label="Товар" rules={[{ required: true }]}>
              <Select
                options={products.map((item) => ({
                  value: item.id,
                  label: `${item.sku} - ${item.name}`,
                }))}
              />
            </Form.Item>
            <Form.Item name="quantity" label="Количество" rules={[{ required: true }]}>
              <InputNumber className="full-width" min={1} />
            </Form.Item>
            <Form.Item
              name="partnerWarehouseId"
              label="Склад партнера"
              rules={[{ required: true }]}
            >
              <Select
                options={partnerWarehouses.map((item) => ({
                  value: item.id,
                  label: item.name,
                }))}
              />
            </Form.Item>
            <div className="field-block">
              <Text className="field-label">Приоритет</Text>
              <Segmented<DeliveryPriority>
                block
                value={priority}
                options={[
                  { label: priorityLabels.CHEAPEST, value: 'CHEAPEST' },
                  { label: priorityLabels.FASTEST, value: 'FASTEST' },
                ]}
                onChange={setPriority}
              />
            </div>
            <Button
              type="primary"
              htmlType="submit"
              icon={<ReloadOutlined />}
              loading={calculateMutation.isPending}
              block
            >
              Рассчитать
            </Button>
          </Form>
        </div>
        <div className="panel result-panel">
          {options.length ? (
            <Table
              rowKey={(item) =>
                `${item.sourceWarehouseId}-${item.partnerWarehouseId}-${item.transportId}`
              }
              columns={columns}
              dataSource={options}
              pagination={false}
              scroll={{ x: 900 }}
            />
          ) : (
            <Empty description="Заполните параметры и запустите расчет" />
          )}
        </div>
      </div>
    </>
  )
}

function calculateDeliveryOptions({
  productId,
  quantity,
  partnerWarehouseId,
  priority,
  products,
  transports,
  ownWarehouses,
  partnerWarehouses,
  stocks,
}: {
  productId: number
  quantity: number
  partnerWarehouseId: number
  priority: DeliveryPriority
  products: ProductResponse[]
  transports: TransportResponse[]
  ownWarehouses: OwnWarehouseResponse[]
  partnerWarehouses: PartnerWarehouseResponse[]
  stocks: StockResponse[]
}) {
  const product = products.find((item) => item.id === productId)
  const partnerWarehouse = partnerWarehouses.find(
    (item) => item.id === partnerWarehouseId,
  )

  if (!product || !partnerWarehouse || quantity <= 0) {
    return []
  }

  const requiredWeightKg = product.weightKg * quantity
  const requiredVolumeM3 = product.volumeM3 * quantity

  const options = ownWarehouses
    .filter((warehouse) => warehouse.isActive)
    .flatMap((warehouse) => {
      const stock = stocks.find(
        (item) => item.ownWarehouseId === warehouse.id && item.productId === product.id,
      )
      const availableQuantity = stock
        ? Math.max(stock.quantity - stock.reservedQuantity, 0)
        : 0

      if (availableQuantity < quantity) {
        return []
      }

      const distance = distanceKm(warehouse, partnerWarehouse)
      const warehouseTransportIds = warehouse.transports.map((item) => item.id)

      return transports
        .filter((transport) => warehouseTransportIds.includes(transport.id))
        .filter((transport) => isTransportAccepted(transport.transportType, partnerWarehouse))
        .map((transport) => {
          const tripsByWeight = requiredWeightKg / transport.maxWeightKg
          const tripsByVolume = requiredVolumeM3 / transport.maxVolumeM3
          const tripsCount = Math.max(1, Math.ceil(Math.max(tripsByWeight, tripsByVolume)))

          return {
            sourceWarehouseId: warehouse.id,
            sourceWarehouseName: warehouse.name,
            partnerWarehouseId: partnerWarehouse.id,
            partnerWarehouseName: partnerWarehouse.name,
            transportId: transport.id,
            transportName: transport.name,
            transportType: transport.transportType,
            distanceKm: distance,
            requiredWeightKg,
            requiredVolumeM3,
            tripsCount,
            estimatedTimeHours: (distance / transport.speedKmH) * tripsCount,
            estimatedCost: distance * transport.costPerKm * tripsCount,
            availableQuantity,
          }
        })
    })

  return [...options].sort((left, right) =>
    priority === 'CHEAPEST'
      ? left.estimatedCost - right.estimatedCost
      : left.estimatedTimeHours - right.estimatedTimeHours,
  )
}

function mapDeliveryOptions(
  response: DeliveryCalculationResponse,
  partnerWarehouses: PartnerWarehouseResponse[],
): DeliveryOption[] {
  const partnerWarehouse = partnerWarehouses.find(
    (warehouse) => warehouse.id === response.partnerWarehouseId,
  )

  return response.options.map((option) => ({
    sourceWarehouseId: option.sourceWarehouseId,
    sourceWarehouseName: option.sourceWarehouseName,
    partnerWarehouseId: response.partnerWarehouseId,
    partnerWarehouseName:
      partnerWarehouse?.name ?? String(response.partnerWarehouseId),
    transportId: option.transportId,
    transportName: option.transportName,
    transportType: option.transportType,
    distanceKm: option.distanceKm,
    requiredWeightKg: option.totalWeightKg,
    requiredVolumeM3: option.totalVolumeM3,
    tripsCount: option.requiredTrips,
    estimatedTimeHours: option.estimatedTimeHours,
    estimatedCost: option.estimatedCost,
    availableQuantity: option.availableQuantity,
  }))
}

function AppContent() {
  const queryClient = useQueryClient()
  const [mockProducts, setProducts] = useState<ProductResponse[]>(initialProducts)
  const [mockTransports, setTransports] =
    useState<TransportResponse[]>(initialTransports)
  const [mockOwnWarehouses, setOwnWarehouses] =
    useState<OwnWarehouseResponse[]>(initialOwnWarehouses)
  const [mockPartners, setPartners] = useState<PartnerResponse[]>(initialPartners)
  const [mockPartnerWarehouses, setPartnerWarehouses] = useState<
    PartnerWarehouseResponse[]
  >(initialPartnerWarehouses)
  const [mockStocks, setStocks] = useState<StockResponse[]>(initialStocks)

  const createProductMutation = useMutation({
    mutationFn: productsApi.create,
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['products'] }),
  })
  const createTransportMutation = useMutation({
    mutationFn: transportsApi.create,
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['transports'] }),
  })
  const createOwnWarehouseMutation = useMutation({
    mutationFn: ownWarehousesApi.create,
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['ownWarehouses'] }),
  })
  const createPartnerMutation = useMutation({
    mutationFn: partnersApi.create,
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['partners'] }),
  })
  const createPartnerWarehouseMutation = useMutation({
    mutationFn: (values: PartnerWarehouseCreateRequest) =>
      partnerWarehousesApi.create(values.partnerId, values),
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: ['partnerWarehouses'] }),
  })
  const addStockMutation = useMutation({
    mutationFn: (values: StockFormValues) =>
      stocksApi.add(values.ownWarehouseId, {
        productId: values.productId,
        quantity: values.quantity,
        reason: values.reason,
      }),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['stocks'] }),
  })

  const productsQuery = useQuery({
    queryKey: ['products', 'list', defaultPageParams],
    queryFn: () => productsApi.list(defaultPageParams),
  })
  const transportsQuery = useQuery({
    queryKey: ['transports', 'list', defaultPageParams],
    queryFn: () => transportsApi.list(defaultPageParams),
  })
  const ownWarehousesQuery = useQuery({
    queryKey: ['ownWarehouses', 'list', defaultPageParams],
    queryFn: () => ownWarehousesApi.list(defaultPageParams),
  })
  const partnersQuery = useQuery({
    queryKey: ['partners', 'list', defaultPageParams],
    queryFn: () => partnersApi.list(defaultPageParams),
  })

  const products = pageContent(productsQuery.data, mockProducts)
  const transports = pageContent(transportsQuery.data, mockTransports)
  const ownWarehouses = pageContent(ownWarehousesQuery.data, mockOwnWarehouses)
  const partners = pageContent(partnersQuery.data, mockPartners)

  const activePartners = useMemo(
    () => partners.filter((partner) => partner.isActive),
    [partners],
  )

  const partnerWarehousesQuery = useQuery({
    queryKey: [
      'partnerWarehouses',
      'byPartner',
      activePartners.map((partner) => partner.id).join(','),
    ],
    queryFn: async () => {
      const pages = await Promise.all(
        activePartners.map((partner) =>
          partnerWarehousesApi.list(partner.id, defaultPageParams),
        ),
      )
      return pages.flatMap((page) => page.content)
    },
    enabled: activePartners.length > 0,
  })
  const partnerWarehouses =
    partnerWarehousesQuery.data ?? mockPartnerWarehouses

  const stocksQuery = useQuery({
    queryKey: [
      'stocks',
      'byWarehouse',
      ownWarehouses.map((warehouse) => warehouse.id).join(','),
    ],
    queryFn: async () => {
      const pages = await Promise.all(
        ownWarehouses.map((warehouse) =>
          stocksApi.list(warehouse.id, defaultPageParams),
        ),
      )
      return pages.flatMap((page) => page.content)
    },
    enabled: ownWarehouses.length > 0,
  })
  const stocks = stocksQuery.data ?? mockStocks

  const liveQueryCount = [
    productsQuery,
    transportsQuery,
    ownWarehousesQuery,
    partnersQuery,
    partnerWarehousesQuery,
    stocksQuery,
  ].filter((query) => query.isSuccess).length

  const addProduct = async (values: ProductCreateRequest) => {
    try {
      await createProductMutation.mutateAsync(values)
      message.success('Товар создан через API')
      return true
    } catch (error) {
      if (isBackendValidationError(error)) {
        showApiError(error)
        return false
      }

      setProducts((items) => [...items, { ...values, id: nextId(items) }])
      message.warning('Backend недоступен, товар добавлен в mock-данные')
      return true
    }
  }

  const addTransport = async (values: TransportCreateRequest) => {
    try {
      await createTransportMutation.mutateAsync(values)
      message.success('Транспорт создан через API')
      return true
    } catch (error) {
      if (isBackendValidationError(error)) {
        showApiError(error)
        return false
      }

      setTransports((items) => [...items, { ...values, id: nextId(items) }])
      message.warning('Backend недоступен, транспорт добавлен в mock-данные')
      return true
    }
  }

  const addOwnWarehouse = async (values: OwnWarehouseCreateRequest) => {
    try {
      await createOwnWarehouseMutation.mutateAsync(values)
      message.success('Склад создан через API')
      return true
    } catch (error) {
      if (isBackendValidationError(error)) {
        showApiError(error)
        return false
      }
    }

    const linkedTransports = transports
      .filter((transport) => values.transportIds.includes(transport.id))
      .map((transport) => ({
        id: transport.id,
        name: transport.name,
        transportType: transport.transportType,
      }))

    setOwnWarehouses((items) => [
      ...items,
      {
        id: nextId(items),
        name: values.name,
        address: values.address,
        latitude: values.latitude,
        longitude: values.longitude,
        isActive: true,
        transports: linkedTransports,
      },
    ])
    message.warning('Backend недоступен, склад добавлен в mock-данные')
    return true
  }

  const addPartner = async (values: PartnerCreateRequest) => {
    try {
      await createPartnerMutation.mutateAsync(values)
      message.success('Партнер создан через API')
      return true
    } catch (error) {
      if (isBackendValidationError(error)) {
        showApiError(error)
        return false
      }

      setPartners((items) => [
        ...items,
        {
          id: nextId(items),
          name: values.name,
          contactEmail: values.contactEmail,
          isActive: values.isActive,
        },
      ])
      message.warning('Backend недоступен, партнер добавлен в mock-данные')
      return true
    }
  }

  const addPartnerWarehouse = async (values: PartnerWarehouseCreateRequest) => {
    if (!values.acceptsLand && !values.acceptsSea && !values.acceptsAir) {
      message.error('Нужен хотя бы один доступный тип доставки')
      return false
    }

    try {
      await createPartnerWarehouseMutation.mutateAsync(values)
      message.success('Склад партнера создан через API')
      return true
    } catch (error) {
      if (isBackendValidationError(error)) {
        showApiError(error)
        return false
      }

      setPartnerWarehouses((items) => [
        ...items,
        {
          ...values,
          id: nextId(items),
          isActive: true,
        },
      ])
      message.warning('Backend недоступен, склад партнера добавлен в mock-данные')
      return true
    }
  }

  const addStock = async (values: StockFormValues) => {
    try {
      await addStockMutation.mutateAsync(values)
      message.success('Остаток добавлен через API')
      return true
    } catch (error) {
      if (isBackendValidationError(error)) {
        showApiError(error)
        return false
      }

      setStocks((items) => [
        ...items,
        {
          id: nextId(items),
          ownWarehouseId: values.ownWarehouseId,
          productId: values.productId,
          quantity: values.quantity,
          reservedQuantity: 0,
        },
      ])
      message.warning('Backend недоступен, остаток добавлен в mock-данные')
      return true
    }
  }

  return (
    <Layout className="app-shell">
      <Sider width={280} theme="light" className="sider">
        <div className="brand">
          <div className="brand-mark">S</div>
          <div>
            <Title level={5}>Shulker SRM</Title>
            <Text type="secondary">warehouse CRM</Text>
          </div>
        </div>
        <AppMenu />
      </Sider>
      <Layout>
        <Header className="topbar">
          <div>
            <Title level={3}>Рабочий интерфейс</Title>
            <Text type="secondary">
              Frontend reads backend API with mock fallback for offline demo
            </Text>
          </div>
          <Space wrap>
            <Tag icon={<ApiOutlined />} color="processing">
              /api proxy to localhost:8080
            </Tag>
            <Tag color="success">backend API v0.0.4</Tag>
            <Tag color={liveQueryCount ? 'success' : 'default'}>
              {liveQueryCount ? `live GET ${liveQueryCount}/6` : 'mock fallback'}
            </Tag>
          </Space>
        </Header>
        <Content className="content">
          <Routes>
            <Route
              path="/"
              element={
                <Dashboard
                  products={products}
                  transports={transports}
                  ownWarehouses={ownWarehouses}
                  partners={partners}
                  partnerWarehouses={partnerWarehouses}
                  stocks={stocks}
                />
              }
            />
            <Route
              path="/products"
              element={<ProductsPage products={products} addProduct={addProduct} />}
            />
            <Route
              path="/own-warehouses"
              element={
                <OwnWarehousesPage
                  warehouses={ownWarehouses}
                  transports={transports}
                  addWarehouse={addOwnWarehouse}
                />
              }
            />
            <Route
              path="/partners"
              element={<PartnersPage partners={partners} addPartner={addPartner} />}
            />
            <Route
              path="/partner-warehouses"
              element={
                <PartnerWarehousesPage
                  partners={activePartners}
                  warehouses={partnerWarehouses}
                  addWarehouse={addPartnerWarehouse}
                />
              }
            />
            <Route
              path="/transports"
              element={
                <TransportsPage transports={transports} addTransport={addTransport} />
              }
            />
            <Route
              path="/stocks"
              element={
                <StocksPage
                  stocks={stocks}
                  products={products}
                  warehouses={ownWarehouses}
                  addStock={addStock}
                />
              }
            />
            <Route
              path="/delivery"
              element={
                <DeliveryPage
                  products={products}
                  transports={transports}
                  ownWarehouses={ownWarehouses}
                  partnerWarehouses={partnerWarehouses}
                  stocks={stocks}
                />
              }
            />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </Content>
      </Layout>
    </Layout>
  )
}

export default function App() {
  return (
    <ConfigProvider
      theme={{
        token: {
          colorPrimary: '#2563eb',
          borderRadius: 6,
          fontFamily:
            'Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, Segoe UI, sans-serif',
        },
      }}
    >
      <BrowserRouter>
        <AppContent />
      </BrowserRouter>
    </ConfigProvider>
  )
}
