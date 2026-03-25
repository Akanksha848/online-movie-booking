# Get Booking

## Overview
_This endpoint retrieves booking details for a given booking reference code. Used by customers to view their booking confirmation and ticket information._

|Method  |Path                                   |Exposed|
|---     |---                                    |---    |
| GET    | /api/bookings/{bookingReference}      | ✅    |

## Path Parameters

| Parameter        | Type   | Required | Description                         |
|---               |---     |---       |---                                  |
| bookingReference | String | Yes      | Unique booking reference (e.g. `BKG-A1B2C3D4`) |

## JSON

### Request
<details>
  <summary>Request</summary>

```
GET /api/bookings/BKG-A1B2C3D4
```
</details>

### Response
<details>
  <summary>Response (200 OK)</summary>

```json
{
  "bookingId": 0,
  "bookingReference": "BKG-A1B2C3D4",
  "customerName": "string",
  "showId": 0,
  "seatIdentifiers": ["string"],
  "totalAmount": 0.00,
  "status": "CONFIRMED",
  "paymentStatus": "COMPLETED",
  "bookedAt": "2026-03-25T10:00:00",
  "expiresAt": null
}
```
</details>

### Error Responses

| Status | Reason                        |
|---     |---                            |
| 404    | Booking reference not found   |
