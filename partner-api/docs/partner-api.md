# Partner API

## Overview
_APIs for theater partner onboarding and management. A partner represents a theater chain or independent owner. Partners are registered with **PENDING** status and must be explicitly activated before they can create theaters._

| Method  | Path                                  | Exposed |
|---------|---------------------------------------|---------|
| POST    | /api/partners                         | ✅      |
| GET     | /api/partners                         | ✅      |
| GET     | /api/partners/{partnerId}             | ✅      |
| PATCH   | /api/partners/{partnerId}/activate    | ✅      |

---

## POST /api/partners

Register a new theater partner.

<details>
  <summary>Request</summary>

```json
{
  "partnerName": "PVR Cinemas",
  "email": "onboarding@pvrcinemas.com",
  "phone": "+911234567890",
  "registrationNumber": "CIN-U74999DL2001PLC110364"
}
```
</details>

<details>
  <summary>Response — 201 Created</summary>

```json
{
  "id": 1,
  "partnerName": "PVR Cinemas",
  "email": "onboarding@pvrcinemas.com",
  "phone": "+911234567890",
  "registrationNumber": "CIN-U74999DL2001PLC110364",
  "status": "PENDING",
  "onboardedAt": "2026-03-25T10:00:00",
  "theaterCount": 0
}
```
</details>

<details>
  <summary>Response — 400 Bad Request (email or registration number already exists)</summary>

```json
{}
```
</details>

### Validation Rules
| Field              | Required | Rules                          |
|--------------------|----------|--------------------------------|
| partnerName        | ✅       | Non-blank                      |
| email              | ✅       | Valid email format, unique     |
| phone              | ✅       | 10–15 digits, optional `+` prefix |
| registrationNumber | ❌       | Unique if provided             |

---

## GET /api/partners

Retrieve all registered partners.

<details>
  <summary>Response — 200 OK</summary>

```json
[
  {
    "id": 1,
    "partnerName": "PVR Cinemas",
    "email": "onboarding@pvrcinemas.com",
    "phone": "+911234567890",
    "registrationNumber": "CIN-U74999DL2001PLC110364",
    "status": "ACTIVE",
    "onboardedAt": "2026-03-25T10:00:00",
    "theaterCount": 3
  },
  {
    "id": 2,
    "partnerName": "INOX Leisure",
    "email": "contact@inoxmovies.com",
    "phone": "+912233445566",
    "registrationNumber": null,
    "status": "PENDING",
    "onboardedAt": "2026-03-26T09:00:00",
    "theaterCount": 0
  }
]
```
</details>

---

## GET /api/partners/{partnerId}

Retrieve a specific partner by ID.

<details>
  <summary>Response — 200 OK</summary>

```json
{
  "id": 1,
  "partnerName": "PVR Cinemas",
  "email": "onboarding@pvrcinemas.com",
  "phone": "+911234567890",
  "registrationNumber": "CIN-U74999DL2001PLC110364",
  "status": "ACTIVE",
  "onboardedAt": "2026-03-25T10:00:00",
  "theaterCount": 3
}
```
</details>

<details>
  <summary>Response — 404 Not Found</summary>

```json
{}
```
</details>

---

## PATCH /api/partners/{partnerId}/activate

Change partner status from `PENDING` to `ACTIVE`. Only active partners can create theaters.

<details>
  <summary>Response — 200 OK</summary>

```json
{
  "id": 1,
  "partnerName": "PVR Cinemas",
  "email": "onboarding@pvrcinemas.com",
  "phone": "+911234567890",
  "registrationNumber": "CIN-U74999DL2001PLC110364",
  "status": "ACTIVE",
  "onboardedAt": "2026-03-25T10:00:00",
  "theaterCount": 0
}
```
</details>

<details>
  <summary>Response — 404 Not Found (partner does not exist)</summary>

```json
{}
```
</details>

### Partner Status Flow
```
PENDING → ACTIVE → SUSPENDED → TERMINATED
```
