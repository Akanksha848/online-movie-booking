# Theater API

## Overview
_APIs for managing theaters under a registered partner. A partner must be in **ACTIVE** status before they can create theaters. Each theater belongs to exactly one partner and contains one or more screens._

| Method | Path                                                    | Exposed |
|--------|---------------------------------------------------------|---------|
| POST   | /api/partners/{partnerId}/theaters                      | ✅      |
| GET    | /api/partners/{partnerId}/theaters                      | ✅      |
| GET    | /api/partners/{partnerId}/theaters/{theaterId}          | ✅      |

---

## POST /api/partners/{partnerId}/theaters

Create a new theater for the specified partner. Partner must be `ACTIVE`.

<details>
  <summary>Request</summary>

```json
{
  "theaterName": "PVR Phoenix Mall",
  "city": "Mumbai",
  "state": "Maharashtra",
  "country": "IN",
  "address": "462, Senapati Bapat Marg, Lower Parel, Mumbai",
  "postalCode": "400013",
  "latitude": 19.0013,
  "longitude": 72.8261,
  "phone": "+912266561000",
  "hasLegacySystem": false
}
```
</details>

<details>
  <summary>Response — 201 Created</summary>

```json
{
  "id": 101,
  "theaterName": "PVR Phoenix Mall",
  "city": "Mumbai",
  "state": "Maharashtra",
  "country": "IN",
  "address": "462, Senapati Bapat Marg, Lower Parel, Mumbai",
  "postalCode": "400013",
  "latitude": 19.0013,
  "longitude": 72.8261,
  "phone": "+912266561000",
  "status": "ACTIVE",
  "hasLegacySystem": false,
  "createdAt": "2026-03-25T12:00:00",
  "updatedAt": "2026-03-25T12:00:00"
}
```
</details>

<details>
  <summary>Response — 400 Bad Request (partner not active or validation failure)</summary>

```json
{}
```
</details>

### Validation Rules
| Field          | Required | Rules                                    |
|----------------|----------|------------------------------------------|
| theaterName    | ✅       | Non-blank                                |
| city           | ✅       | Non-blank                                |
| state          | ✅       | Non-blank                                |
| country        | ✅       | Non-blank, ISO 3166-1 alpha-2 (e.g. `IN`) |
| address        | ✅       | Non-blank                                |
| hasLegacySystem| ✅       | Boolean                                  |
| postalCode     | ❌       | —                                        |
| latitude       | ❌       | —                                        |
| longitude      | ❌       | —                                        |
| phone          | ❌       | —                                        |

### Business Rules
- Partner must exist and have `status = ACTIVE`
- Theater is created with `status = ACTIVE` by default
- `hasLegacySystem` defaults to `false` if not provided

---

## GET /api/partners/{partnerId}/theaters

Retrieve all theaters owned by the specified partner.

<details>
  <summary>Response — 200 OK</summary>

```json
[
  {
    "id": 101,
    "theaterName": "PVR Phoenix Mall",
    "city": "Mumbai",
    "state": "Maharashtra",
    "country": "IN",
    "address": "462, Senapati Bapat Marg, Lower Parel, Mumbai",
    "postalCode": "400013",
    "latitude": 19.0013,
    "longitude": 72.8261,
    "phone": "+912266561000",
    "status": "ACTIVE",
    "hasLegacySystem": false,
    "createdAt": "2026-03-25T12:00:00",
    "updatedAt": "2026-03-25T12:00:00"
  },
  {
    "id": 102,
    "theaterName": "PVR Juhu",
    "city": "Mumbai",
    "state": "Maharashtra",
    "country": "IN",
    "address": "Juhu Tara Rd, Juhu, Mumbai",
    "postalCode": "400049",
    "latitude": 19.1020,
    "longitude": 72.8264,
    "phone": null,
    "status": "ACTIVE",
    "hasLegacySystem": true,
    "createdAt": "2026-03-26T09:30:00",
    "updatedAt": "2026-03-26T09:30:00"
  }
]
```
</details>

---

## GET /api/partners/{partnerId}/theaters/{theaterId}

Retrieve a specific theater by ID.

<details>
  <summary>Response — 200 OK</summary>

```json
{
  "id": 101,
  "theaterName": "PVR Phoenix Mall",
  "city": "Mumbai",
  "state": "Maharashtra",
  "country": "IN",
  "address": "462, Senapati Bapat Marg, Lower Parel, Mumbai",
  "postalCode": "400013",
  "latitude": 19.0013,
  "longitude": 72.8261,
  "phone": "+912266561000",
  "status": "ACTIVE",
  "hasLegacySystem": false,
  "createdAt": "2026-03-25T12:00:00",
  "updatedAt": "2026-03-25T12:00:00"
}
```
</details>

<details>
  <summary>Response — 404 Not Found</summary>

```json
{}
```
</details>

### Theater Status Values
| Status            | Description                        |
|-------------------|------------------------------------|
| ACTIVE            | Theater is operational             |
| INACTIVE          | Theater is not accepting bookings  |
| UNDER_MAINTENANCE | Temporarily closed for maintenance |
