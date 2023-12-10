# Тестовое задание на позицию `Разработчик Java` в компанию [Netris](https://www.netris.ru/).

---

## Содержание
- [Как запустить](#как-запустить)
- [Задача](#задача)
- [Дополнения](#дополнения)
  - [Примеры ответа за запросы](#примеры-ответа-за-запросы)
  - [Расширенное логирование исходящих запросов](#расширенное-логирование-исходящих-запросов)


## Как запустить

- build
```bash
./gradlew build
```
- run
```bash
./gradlew bootRun
```
- tests
```bash
./gradlew test
```

## Задача:

### Оригинал задания в формате .pdf ([task.pdf](docs/task.pdf))

Необходимо написать код для получения и агрегации данных из нескольких сервисов.

Получение списка доступных видеокамер: https://run.mocky.io/v3/bc34ce01-90c6-4266-93f1-07591afad12e

Ответ состоит из массива объектов, содержащих поля:
- `id` - число, идентификатор камеры
- `sourceDataUrl` - строка, ссылка для получения данных источника.
- `tokenDataUrl` - строка, ссылка для получения токенов безопасности по камере.

Формат данных в ответе на запрос на URL из поля `sourceDataUrl`:
- `urlType` - строка, тип ссылки на видеопоток. Возможные значения: `"LIVE"`,
`"ARCHIVE"`
- `videoUrl` - строка, ссылка на видеопоток

Формат данных в ответе на запрос на URL из поля `tokenDataUrl`:
- `value` - строка, токен безопасности
- `ttl` - число, время жизни токена

Необходимо сагрегировать данные по каждой камере. Ожидаемый результат:

```json
[
  {
    "id": 1,
    "urlType": "LIVE",
    "videoUrl": "rtsp://127.0.0.1/1",
    "value": "fa4b588e-249b-11e9-ab14-d663bd873d93",
    "ttl": 120
  },
  {
    "id": 3,
    "urlType": "ARCHIVE",
    "videoUrl": "rtsp://127.0.0.1/3",
    "value": "fa4b5d52-249b-11e9-ab14-d663bd873d93",
    "ttl": 120
  },
  {
    "id": 20,
    "urlType": "LIVE",
    "videoUrl": "rtsp://127.0.0.1/20",
    "value": "fa4b5f64-249b-11e9-ab14-d663bd873d93",
    "ttl": 180
  },
  {
    "id": 2,
    "urlType": "ARCHIVE",
    "videoUrl": "rtsp://127.0.0.1/2",
    "value": "fa4b5b22-249b-11e9-ab14-d663bd873d93",
    "ttl": 60
  }
]
```

Решение должно быть представлено в виде веб-сервиса. Результат нужно вернуть в
ответе на HTTP-запрос к этому сервису.

При написании кода надо учитывать потенциально большие объемы данных, то есть сбор
и агрегация должны выполняться в несколько потоков и как можно меньше блокироваться
(на операциях I/O или ожидании данных другого запроса)

Решение должно быть опубликовано на гитхабе и подкреплено юнит-тестами.

## Дополнения

### Примеры ответа за запросы

- https://run.mocky.io/v3/bc34ce01-90c6-4266-93f1-07591afad12e
```json
[
    {
        "id": 1,
        "sourceDataUrl": "https://run.mocky.io/v3/e417b132-12ff-434d-a2c2-f81e6ddc1c6c",
        "tokenDataUrl": "https://run.mocky.io/v3/fc0b6f75-b911-4b0a-9a1d-dc2379a6ef1c"
    },
    {
        "id": 3,
        "sourceDataUrl": "https://run.mocky.io/v3/64ab5cc8-1f47-4ac5-a621-90d4410f7db7",
        "tokenDataUrl": "https://run.mocky.io/v3/1ea436bd-1d3f-423c-9373-19e7d825dcd7"
    },
    {
        "id": 20,
        "sourceDataUrl": "https://run.mocky.io/v3/e8eee697-3018-4b2b-8b05-d00ab6b545c2",
        "tokenDataUrl": "https://run.mocky.io/v3/e2d29bba-4315-453b-b270-e668b2a67af1"
    },
    {
        "id": 2,
        "sourceDataUrl": "https://run.mocky.io/v3/86da6879-1444-4c92-818c-77b55d9c7864",
        "tokenDataUrl": "https://run.mocky.io/v3/8b3babb6-c354-4874-a878-68d883c8425d"
    }
]
```

- https://run.mocky.io/v3/e417b132-12ff-434d-a2c2-f81e6ddc1c6c
```json
{
    "urlType": "LIVE",
    "videoUrl": "rtsp://127.0.0.1/1"
}
```

- https://run.mocky.io/v3/fc0b6f75-b911-4b0a-9a1d-dc2379a6ef1c
```json
{
    "value": "fa4b588e-249b-11e9-ab14-d663bd873d93",
    "ttl": 120
}
```

### Расширенное логирование исходящих запросов

Для наглядной демонстрации порядка выполнения исходящих запросов раскомментируйте соответствующую строку в [application.properties](src/main/resources/application.properties).